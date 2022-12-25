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

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
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
        getServletContext().getRequestDispatcher("/WEB-INF/view/security/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && password != null) {
            try {
                User user = userDao.getUserByUsernameAndPassword(username, password);
                if (user == null) {
                    resp.sendRedirect("/signin");
                } else {
                    userService.auth(user, req, resp);
                    resp.sendRedirect("/");
                }
            } catch (DbException e) {
                throw new ServletException(e);
            }
        }
    }
}
