package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CommentDao;
import org.example.dao.FilmDao;
import org.example.entity.Film;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.DbException;

import java.io.IOException;
import java.util.concurrent.CompletionException;

@WebServlet("/detail")
public class FilmDetailServlet extends HttpServlet {
    private FilmDao filmDao;
    private CommentDao commentDao;
    private UserService userService;
    private String filmId;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filmDao = (FilmDao) getServletContext().getAttribute("filmDao");
        commentDao = (CommentDao) getServletContext().getAttribute("commentDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("add") != null) {
                if (req.getParameter("add").equals("true")) {
                    req.getRequestDispatcher("/WEB-INF/view/films/upload.jsp").forward(req, resp);
                }
            }
            ServletContext servletContext = req.getServletContext();

            filmId = req.getParameter("id");
            if (filmId == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id been provided");
            }

            Film film = filmDao.getDetail(Integer.parseInt(filmId));
            if (film == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/view/errors/notfound.jsp").forward(req, resp);
            }
            req.getSession().setAttribute("currentFilmId", filmId);
            req.setAttribute("film", film);
            try {
                req.setAttribute("comments", commentDao.getCommentByFilmId(Integer.parseInt(filmId)));
            } catch (DbException e) {
                throw new ServletException(e);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/view/films/detail.jsp").forward(req, resp);
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("comment");
        User user = userService.getUser(req, resp);
        if (user != null) {
            try {
                if (!text.equals("")) {
                    commentDao.setComment(Integer.parseInt(filmId), user.getUsername(), text);
                }
                ServletContext servletContext = req.getServletContext();
                servletContext.setAttribute("filmId", filmId);
                resp.sendRedirect("/detail?id="+filmId);
            } catch (DbException e) {
                throw new ServletException(e);
            }
        }
    }
}
