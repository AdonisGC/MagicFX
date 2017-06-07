package card;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String get(String dataUrl) throws IOException {

        URL url = new URL(dataUrl);
        String response = null;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // The particular webserver that we are trying to access is checking the User-Agent
        // HTTP header and denying access to anything that doesn't look like a normal
        // browser, to prevent bots (which is probably what we are writing).

        // Normally a "real" User-Agent contains lots of extra info, but that webserver seems
        // to look only for the basic browser type.
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
        } finally {
            urlConnection.disconnect();
        }
        return response;
    }

    private static String readStream(InputStream in) throws IOException {

        // Read stdin
        BufferedReader rd = new BufferedReader( new InputStreamReader(in));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}
