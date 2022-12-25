package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.FilmDao;
import org.example.util.DbException;

import java.io.IOException;

@WebServlet("/create")
public class FilmCreateServlet extends HttpServlet {
    private FilmDao filmDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filmDao = (FilmDao) getServletContext().getAttribute("filmDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/films" +
                "/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String genres = req.getParameter("genres");
        String info = req.getParameter("info");
        int publishedYear = Integer.parseInt(req.getParameter("published_year"));
        if (!title.equals("") && publishedYear >= 1850 && publishedYear < 2025
        && !director.equals("") && !genres.equals("") && !info.equals("")) {
            try {
                filmDao.setFilm(title, publishedYear, director, genres, info);
                resp.sendRedirect("/");
            } catch (DbException e) {
                throw new ServletException(e);
            }
        } else {
            resp.sendRedirect("/create");
        }
    }
}
