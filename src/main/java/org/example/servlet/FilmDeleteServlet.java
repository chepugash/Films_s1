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

@WebServlet("/delete")
public class FilmDeleteServlet extends HttpServlet {
    private FilmDao filmDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filmDao = (FilmDao) getServletContext().getAttribute("filmDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            if (id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id been provided");
            }
            filmDao.delFilm(id);
            resp.sendRedirect("/");
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }
}
