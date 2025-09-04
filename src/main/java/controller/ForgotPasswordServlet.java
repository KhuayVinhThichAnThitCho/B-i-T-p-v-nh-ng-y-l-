package controller;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import until.DBConnection;
import until.EmailUtils;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Connection conn = DBConnection.getConnection();
        	     PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email=?")) {

        	    stmt.setString(1, email);
        	    try (ResultSet rs = stmt.executeQuery()) {
        	        if (rs.next()) {
        	            // Tạo token
        	            String token = UUID.randomUUID().toString();
        	            Timestamp expiry = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));

        	            // Lưu vào DB
        	            try (PreparedStatement updateStmt = conn.prepareStatement(
        	                    "UPDATE users SET reset_token=?, token_expiry=? WHERE email=?")) {
        	                updateStmt.setString(1, token);
        	                updateStmt.setTimestamp(2, expiry);
        	                updateStmt.setString(3, email);
        	                updateStmt.executeUpdate();
        	            }

        	            // Gửi email
        	            String resetLink = "http://localhost:8080/hello/reset-password?token=" + token;
        	            EmailUtils.sendMail(email, "Reset Password",
        	                    "Click vào link để đổi mật khẩu: " + resetLink);
        	        }
        	    }
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}

        	response.getWriter().println("Nếu email tồn tại, link reset đã được gửi.");


        response.getWriter().println("Nếu email tồn tại, link reset đã được gửi.");
    }
}
