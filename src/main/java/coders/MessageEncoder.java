package coders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import models.Message;

public class MessageEncoder implements Encoder.Text<Message>
{
    private static Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd").create();
    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig config) {
        Encoder.Text.super.init(config);
    }

    @Override
    public void destroy() {
        Encoder.Text.super.destroy();
    }
}
