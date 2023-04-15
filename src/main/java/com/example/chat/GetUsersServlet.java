package com.example.chat;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getUsers", value = "/get-users")
public class GetUsersServlet extends HttpServlet {

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
                ResultSet rs = statement.executeQuery("SELECT * FROM users;");
                response.setContentType("application/json");
                List<User> userList = new ArrayList<User>();
                while (rs.next()){
                    User nextuser = new User(rs.getInt("id"), rs.getString("name"));
                    userList.add(nextuser);
                }
                Gson gson = new Gson();
                String result = gson.toJson(userList);
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