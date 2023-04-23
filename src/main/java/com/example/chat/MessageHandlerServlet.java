package com.example.chat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "ms", value = "/ms")
public class MessageHandlerServlet extends HttpServlet {
    //Отправить сообщение на хранение в DB
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdb", "postgres", "1234");
            Statement statement = connection.createStatement();
            String userName = req.getParameter("userName");
            String ownerID = req.getParameter("owner_id");
            String chatID = req.getParameter("chat_id");
            String text = req.getParameter("text");
            String dispatch = req.getParameter("dispatch");
            try {
                statement.executeQuery("INSERT INTO messages (owner_id, chat_id, text, user_name, dispatch) VALUES (" + ownerID + ", " + chatID + ", " + "'" + text + "'" + ", " + "'" + userName + "'"+ "," + "'" + dispatch + "'" + ");");
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrintWriter pw = resp.getWriter();
            pw.println("Сообщение отправлено");
            pw.println("От: " + ownerID);
            pw.println("Чат" + chatID);
            pw.println("Сообщение" + text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
