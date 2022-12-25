package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.FilmDao;
import org.example.entity.Film;
import org.example.util.DbException;

import java.io.IOException;

@WebServlet("/edit")
public class FilmEditServlet extends HttpServlet {
    private FilmDao filmDao;
    private Integer id;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filmDao = (FilmDao) getServletContext().getAttribute("filmDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            id = Integer.parseInt(req.getParameter("id"));
            if (id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id been provided");
            }
            Film film = filmDao.getDetail(id);
            if (film == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/view/errors/notfound.jsp").forward(req, resp);
            }
            req.setAttribute("film", film);
            getServletContext().getRequestDispatcher("/WEB-INF/view/films/edit.jsp").forward(req, resp);
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String genres = req.getParameter("genres");
        String info = req.getParameter("info");
        int publishedYear = Integer.parseInt(req.getParameter("published_year"));
        try {
            if (!title.equals("") && publishedYear >= 1850 && publishedYear < 2025) {
                filmDao.editFilm(id, title, publishedYear, director, genres, info);
                resp.sendRedirect("/");
            } else if (publishedYear < 1850 || publishedYear >= 2025) {
                resp.sendRedirect("/");
            }
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }
}
