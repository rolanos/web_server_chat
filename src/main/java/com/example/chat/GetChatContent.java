package com.example.chat;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Chat;
import models.Message;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getChatContent", value = "/get-chats-content")
public class GetChatContent extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdb", "postgres", "1234");
            Statement statement = connection.createStatement();
            String chatId = request.getParameter("chat_id");
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM messages where chat_id = " + chatId + ";");
                response.setContentType("application/json");
                List<Message> chatList = new ArrayList<Message>();
                while (rs.next()){
                    Message nextMessage = new Message(rs.getString("user_name"), rs.getInt("owner_id"), rs.getInt("chat_id"), rs.getString("text"),  rs.getDate("dispatch"));
                    chatList.add(nextMessage);
                }
                Gson gson = new Gson();
                String result = gson.toJson(chatList);
                response.getWriter().write(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}
