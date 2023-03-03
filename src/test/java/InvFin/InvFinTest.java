package InvFin;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.util.Map;
import InvFin.InvFin;

class InvFinTest {

    @Test
    void testBuildURL() throws MalformedURLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("slug", "thats-a-nice-slug");
        params.put("id", 25);
        assertEquals(
                new URL("https://inversionesyfinanzas.xyz/api/v1/here/?api_key=apiKey&id=25&slug=thats-a-nice-slug"),
                InvFin.buildURL("here", params, "apiKey"));
    }

    @Test
    void testFormatPath() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("slug", "thats-a-nice-slug");
        params.put("id", 25);
        assertEquals(
                new String("https://inversionesyfinanzas.xyz/api/v1/here/?api_key=apiKey&id=25&slug=thats-a-nice-slug"),
                InvFin.formatPath("here", params, "apiKey"));
    }

    @Test
    void testFormatParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("slug", "thats-a-nice-slug");
        params.put("id", 25);
        assertEquals(
                new String("&id=25&slug=thats-a-nice-slug"),
                InvFin.formatParams(params));
    }

    @Test
    void testFormatParamsEmpty() {
        Map<String, Object> params = new HashMap<String, Object>();
        assertEquals(
                new String(""),
                InvFin.formatParams(params));
    }
}