package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.DbException;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private UserDao userDao;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/security/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && password != null) {
            try {
                User user = userDao.getUserByUsernameAndPassword(username, password);
                if (user == null && !userDao.isEmailExists(username)) {
                    if (password.length() >= 8 && password.length() <= 32) {
                        userDao.setUser(username, password);
                        resp.sendRedirect("/");
                    }
                } else if (userDao.isEmailExists(username)) {
                    resp.sendRedirect("/signin");
                } else {
                    resp.sendRedirect("/registration");
                }
            } catch (DbException e) {
                throw new ServletException(e);
            }
        }
    }
}
