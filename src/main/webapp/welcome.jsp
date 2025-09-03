<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="welcome.css">
    <style>
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        table th {
            background: #f2f2f2;
        }
        form.inline {
            display: flex;
            gap: 5px;
            align-items: center;
        }
        input[type="text"] {
            padding: 4px;
            width: 100%;
            box-sizing: border-box;
        }
        button {
            padding: 4px 8px;
            cursor: pointer;
        }
        button.delete {
            background-color: #ff4d4d;
            color: white;
            border: none;
        }
        button.update {
            background-color: #4CAF50;
            color: white;
            border: none;
        }
        button.add {
            background-color: #008CBA;
            color: white;
            border: none;
        }
        form.add-form {
            display: flex;
            gap: 10px;
            margin-top: 15px;
        }
        .empty-message {
            text-align: center;
            color: #888;
            font-style: italic;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>🎉 Chào mừng bạn!</h2>
    <p>Danh sách Category của bạn:</p>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty categories}">
                <c:forEach var="c" items="${categories}">
                    <tr>
                        <td>${c.id}</td>
                        <td>
                            <form action="categories" method="post" class="inline">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="${c.id}">
                                <input type="text" name="name" value="${c.name}" required>
                        </td>
                        <td>
                                <input type="text" name="description" value="${c.description}">
                        </td>
                        <td>
                                <button type="submit" class="update">Cập nhật</button>
                            </form>
                            <form action="categories" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${c.id}">
                                <button type="submit" class="delete"
                                        onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4" class="empty-message">Chưa có category nào</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <h3>➕ Thêm Category mới</h3>
    <form action="categories" method="post" class="add-form">
        <input type="hidden" name="action" value="add">
        <input type="text" name="name" placeholder="Tên category" required>
        <input type="text" name="description" placeholder="Mô tả">
        <button type="submit" class="add">Thêm</button>
    </form>

    <p style="margin-top:20px;"><a href="logout">Đăng xuất</a></p>
</div>
</body>
</html>
