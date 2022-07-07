package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Pattern;

public class Message {
    private Date date = new Date();
    private String from;
    private String to;
    private String text;
    private int currentCount;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
        this.to = getLogin(text);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(this);
    }

    public static Message fromJSON(String s) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(s, Message.class);
    }



    @Override
    public String toString() {
        return new StringBuilder().append("[").append(date)
                .append(", From: ").append(from).append(", To: ")
                .append(to == null?"All!: ":to)
                .append("] ").append(text)
                .toString();
    }



    public int send(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode(); // 200?
        }
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    private String getLogin(String text) {
        // @clientLogin text....
        String[] arrayText = text.split(" +");
        String toLogin = null;
        if (arrayText.length >= 2) {
            if (arrayText[0].startsWith("@")) {
                toLogin = arrayText[0].substring(1);
            }
        }
        return toLogin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
