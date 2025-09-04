package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import until.DBConnection;
import until.HashUtils;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, token_expiry FROM users WHERE reset_token=?"
            );
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp expiry = rs.getTimestamp("token_expiry");
                if (expiry != null && expiry.after(new Timestamp(System.currentTimeMillis()))) {
                    // Update password
                    PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE users SET password=?, reset_token=NULL, token_expiry=NULL WHERE reset_token=?"
                    );
                    updateStmt.setString(1, HashUtils.hashPassword(newPassword)); // nhớ hash mật khẩu
                    updateStmt.setString(2, token);
                    updateStmt.executeUpdate();

                    response.getWriter().println("Mật khẩu đã được đặt lại thành công!");
                } else {
                    response.getWriter().println("Token đã hết hạn hoặc không hợp lệ.");
                }
            } else {
                response.getWriter().println("Token không tồn tại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

