package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.findByUsername(username);

        if (user != null && password.equals(user.getPasswordHash())) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("welcome.jsp");
        } else {
            req.setAttribute("error", "Sai username hoặc password");
            req.getRequestDispatcher("login.html").forward(req, resp);
        }
    }
}
