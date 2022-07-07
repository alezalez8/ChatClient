package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetUsers {
    private final Gson gson;


    public GetUsers() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public List<Message> getPresentUsers() throws IOException {
        URL url = new URL(Utils.getURL() + "/user");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        try {
            byte[] buf = responseBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);

            JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
            if (list != null) {
                System.out.println("Users are present now^");
                for (Message m : list.getList()) {

                    System.out.println(m.getFrom());
                }
                System.out.println("____________________");
            }
        } finally {
            is.close();
        }

        return null;
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
