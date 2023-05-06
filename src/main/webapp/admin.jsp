<%@ page import="models.Chat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% List<Chat> chatList = (ArrayList) request.getAttribute("chatList"); %>
<h2>Создать пользователя</h2>
<form action="/get-users" method="post">
    <label>Имя пользователя:</label>
    <input type="text" name="name" required>
    <br><br>
    <input type="submit" value="Создать пользователя">
</form>
<h2>Добавить пользователя в канал(если данного канала не существует, создаем новый канал)</h2>
<form action="/get-chats" method="post">
    <label>Имя пользователя:</label>
    <input type="text" name="userName" required>
    <label>Название канала:</label>
    <input type="text" name="chatName" required>
    <br><br>
    <input type="submit" value="Создать пользователя">
</form>
<table>
    <thead>
        <tr>
            <td>ID пользователя</td>
            <td>Имя пользователя</td>
            <td>ID чата</td>
            <td>Название чата</td>
        </tr>
    </thead>
    <%for (Chat chat : chatList) {%>
        <tr>
            <td><%= chat.getParticipant() %></td>
            <td><%= chat.getParticipantName() %></td>
            <td><%= chat.getID() %></td>
            <td><%= chat.getName() %></td>
        </tr>
    <%}%>
</table>
</body>
</html>
