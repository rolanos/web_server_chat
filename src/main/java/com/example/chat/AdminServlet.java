package com.example.chat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Chat;
import models.Message;
import models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "admin", value = "/admin")
public class AdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdb", "postgres", "1234");
            Statement statement = connection.createStatement();
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM chats;");
                List<Chat> chatList = new ArrayList<Chat>();
                while (rs.next()){
                    Chat nextChat = new Chat(rs.getInt("id"), rs.getString("name"), rs.getInt("participant_id"));
                    chatList.add(nextChat);
                }
                Collections.sort(chatList, new Comparator<Chat>() {
                    @Override
                    public int compare(Chat chat1, Chat chat2) {
                        return Long.compare(chat1.getParticipant(), chat2.getParticipant());
                    }
                });
                rs = statement.executeQuery("SELECT * FROM users");
                List<User> userList = new ArrayList<User>();
                while (rs.next()){
                    User nextUser = new User(rs.getInt("id"), rs.getString("name"));
                    userList.add(nextUser);
                }
                for (Chat i: chatList) {
                    int id = i.getParticipant();
                    for (User j: userList) {
                        if(id == j.getId()){
                            i.setParticipantName(j.getName());
                            break;
                        }
                    }
                }
                request.setAttribute("chatList", chatList);
                getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
