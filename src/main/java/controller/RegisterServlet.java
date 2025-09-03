package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();

     // Kiểm tra username đã tồn tại chưa
        if (dao.findByUsername(username) != null) {
            resp.sendRedirect("register.html?error=Username+da+ton+tai");
            return;
        }

        User u = new User(username, email, password);
        boolean ok = dao.insert(u);

        if (ok) {
            resp.sendRedirect("login.html?user=" + username);
        } else {
        	resp.sendRedirect("register.html?error=Dang+ky+that+bai");
        }
    }
}
