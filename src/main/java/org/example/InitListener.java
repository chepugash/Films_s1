package org.example;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.dao.CommentDao;
import org.example.dao.FilmDao;
import org.example.dao.UserDao;
import org.example.service.UserService;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
            sce.getServletContext().setAttribute("filmDao", new FilmDao(connectionProvider));
            sce.getServletContext().setAttribute("userDao", new UserDao(connectionProvider));
            sce.getServletContext().setAttribute("userService", new UserService());
            sce.getServletContext().setAttribute("commentDao", new CommentDao(connectionProvider));

            FilmDao filmDao = (FilmDao) sce.getServletContext().getAttribute("filmDao");
            filmDao.createTable();

            UserDao userDao = (UserDao) sce.getServletContext().getAttribute("userDao");
            userDao.createTable();

            CommentDao commentDao = (CommentDao) sce.getServletContext().getAttribute("commentDao");
            commentDao.createTable();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }
}
