package com.example.chat;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
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
import java.util.Set;

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
                    Chat nextchat = new Chat(rs.getInt("id"), rs.getString("name"));
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdb", "postgres", "1234");
            Statement statement = connection.createStatement();
            String userName = req.getParameter("userName");
            String chatName = req.getParameter("chatName");
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM chats where name = " + "'" +chatName+ "';" );
                boolean isChatExist = false;
                int id = -1;
                while(rs.next()){
                    isChatExist = true;
                    id = rs.getInt("id");
                }
                int participant = -1;
                boolean isNameExist = false;
                rs = statement.executeQuery("SELECT * FROM users where name = " + "'" +userName + "';" );
                while(rs.next()){
                    isNameExist = true;
                    participant = rs.getInt("id");
                }
                if(isChatExist && isNameExist){
                    statement.executeQuery("INSERT INTO chats (id, name, participant_id) values (" + id + ','+"'" + chatName + "'" + ',' + participant + ");" );
                }
                if(!isChatExist && isNameExist){
                    rs = statement.executeQuery("SELECT * FROM chats;");
                    int maxId = 0;
                    while(rs.next()){
                        if (rs.getInt("id") > maxId){
                            maxId = rs.getInt("id");
                        }
                    }
                    maxId++;
                    statement.executeQuery("INSERT INTO chats (id, name, participant_id) values (" + maxId + ','+"'" + chatName + "'" + ',' + participant + ");" );
                }
                else{
                    resp.getWriter().write("Ошибка! Пользователя с именем " + userName + " не существует!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}