import ch.qos.logback.classic.BasicConfigurator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger
            = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        List<List<String>> data = new ArrayList<List<String>>();
        List<String> subdata = new ArrayList<String>();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        logger.info("Example log from {}", Main.class.getSimpleName());
        logger.debug("Debug");

        try {

            HttpGet request = new HttpGet("https://swapi.dev/api/people/");

            // add request headers
            request.addHeader("custom-key", "mkyong");
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);

                    ObjectMapper objectMapper = new ObjectMapper();
                    final JsonNode arrNode = objectMapper.readTree(result).get("results");

                    String[] column = {"Nombre", "Altura (m)", "Año de Nacimiento"};;

                    if (arrNode.isArray()) {
                        for (final JsonNode objNode : arrNode) {
                            subdata.add(objNode.get("name").asText());
                            subdata.add(objNode.get("height").asText());
                            subdata.add(objNode.get("birth_year").asText());
                            data.add(subdata);
                            System.out.println(subdata);
                        }
                    }
                    new Table(data);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

    }
}
