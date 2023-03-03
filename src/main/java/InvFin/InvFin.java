package InvFin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import com.dslplatform.json.*;

public class InvFin {
    static String APIVersion = "v1";

    public static String get(String path, Map<String, Object> params, String APIKey) throws IOException {
        URL url = buildURL(path, params, APIKey);
        HttpURLConnection connection = createURLConnection(url);
        return handleResponse(connection);
    }

    public static URL buildURL(String path, Map<String, Object> params, String APIKey) throws MalformedURLException {
        String finalUrl = formatPath(path, params, APIKey);
        return new URL(finalUrl);
    }

    public static String formatPath(String path, Map<String, Object> params, String APIKey) {
        String formatedParams = formatParams(params);
        return String.format("https://inversionesyfinanzas.xyz/api/%s/%s/?api_key=%s%s", APIVersion, path,
                APIKey, formatedParams);
    }

    public static String formatParams(Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuilder.append(String.format("&%s=%s", entry.getKey(), entry.getValue()));
        }

        return stringBuilder.toString();
    }

    public static HttpURLConnection createURLConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        setConnectionAttributes(connection);
        return connection;
    }

    public static void setConnectionAttributes(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
    }

    public static String handleResponse(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return successResponse(connection);
        } else {
            // Error: handle the error
            return "some error";
        }
    }

    public static String successResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return readResponse(reader);
    }

    public static String readResponse(BufferedReader bufReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static byte[] readResponseInBytes(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] jsonBytes = byteArrayOutputStream.toByteArray();
        inputStream.close();
        connection.disconnect();
        return jsonBytes;
    }

    // public static void serializeResult(byte[] jsonBytes) {
    // DslJson<Object> dslJson = new DslJson<>();
    // ReadObject<Object> deserializer = dslJson.newReadObject(Object.class);
    // JsonReader<Object> jsonReader = dslJson.newReader(jsonBytes);
    // Object myObject = deserializer.read(jsonReader);
    // }
}
