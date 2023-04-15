package socket;

import coders.MessageDecoder;
import coders.MessageEncoder;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import models.Message;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.HeaderParam;

@ServerEndpoint(value = "/chat",  encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class ChatEndpoint {
    private Session session = null;
    private static List<Session> sessionList = new LinkedList<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        //session.getUserProperties().put("user_id", userId);
        sessionList.add(session);
    }
    @OnClose
    public void onClose(Session session){
        sessionList.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message message){
        System.out.println(message.getText());
        System.out.println(sessionList.size());
        try{
            URL url = new URL("http://localhost:8080/ms");
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String urlParameters = "userName=" + message.getUserName() + "&owner_id=" + message.getUserID() + "&chat_id=" + message.getChatID() + "&text=" + message.getText();
            connection.setDoOutput(true);
            connection.getOutputStream().write(urlParameters.getBytes("UTF-8"));
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            connection.disconnect();
            System.out.println("Сообщение записано в БД");
        }catch (Exception e){
            e.printStackTrace();
        }
        //рассылаем всем сообщения
        sessionList.forEach(s->{
            //if(s == this.session) return;
            try {
                s.getBasicRemote().sendObject(message);
                System.out.println("Сообщение клиенту отправлено!");
            }catch (Exception e)
            {
                System.out.println("Сообщение клиенту НЕ отправлено!");
                e.printStackTrace();
            }
        });
    }
}
