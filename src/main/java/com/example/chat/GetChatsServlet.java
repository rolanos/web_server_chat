package com.example.chat;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Chat;
import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getChats", value = "/get-chats")
public class GetChatsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdb", "postgres", "1234");
            Statement statement = connection.createStatement();
            String userID = request.getParameter("participant_id");
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM chats where participant_id = " + userID + ";");
                response.setContentType("application/json");
                List<Chat> chatList = new ArrayList<Chat>();
                while (rs.next()){
                    Chat nextchat = new Chat(rs.getInt("id"), rs.getString("name"), rs.getInt("participant_id"));
                    chatList.add(nextchat);
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