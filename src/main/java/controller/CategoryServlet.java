package controller;

import dao.CategoryDAO;
import model.Category;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session, kiểm tra user đã login chưa
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.html"); // chưa login → về login
            return;
        }

        // Lấy danh sách category của user
        List<Category> categories = categoryDAO.findByUserId(user.getId());
        request.setAttribute("categories", categories);

        // Forward sang JSP
        request.getRequestDispatcher("/welcome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Thêm category mới
            Category c = new Category();
            c.setName(request.getParameter("name"));
            c.setDescription(request.getParameter("description"));
            c.setUserId(user.getId());
            categoryDAO.insert(c);

        } else if ("update".equals(action)) {
            // Cập nhật category
            Category c = new Category();
            c.setId(Integer.parseInt(request.getParameter("id")));
            c.setName(request.getParameter("name"));
            c.setDescription(request.getParameter("description"));
            c.setUserId(user.getId());
            categoryDAO.update(c);

        } else if ("delete".equals(action)) {
            // Xóa category
            int id = Integer.parseInt(request.getParameter("id"));
            categoryDAO.delete(id, user.getId());
        }

        // Sau khi thao tác, reload danh sách
        response.sendRedirect("categories");
    }
}
