package kyc;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.microhttp.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class App {
    public static void main(String[] args) throws IOException {
        // load file
        List<PepDataRow> pepData = PepDataRow.loadFromResource("pep.csv");

        int port = 8081;
        try {
            port = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException ignored) {
            System.out.println("No port found, running on port 8081");
        }

        var serverOptions = new Options().withPort(port);

        var loop = new EventLoop(serverOptions, (request, consumer) -> {
            try {
                var response = handleRequest(pepData, request);
                consumer.accept(response);
            } catch (Exception e) {
                e.printStackTrace();
                consumer.accept(new Response(500, "ERR", List.of(), "Unexpected Error".getBytes(StandardCharsets.UTF_8)));
            }
        });
        loop.start();

    }

    /**
     * This method gets the query params and adds them to a map
     * @param uri part of the url after the host
     * @return map of Param:value
     */
    private static Map<String, String> parseQueryParams(String uri) {
        var splitUri = uri.split("\\?");
        if (splitUri.length < 2) {
            return Map.of();
        }
        String queryParams = splitUri[1];
        return Arrays.stream(queryParams.split("&"))
                .map(paramPair -> paramPair.split("="))
                .collect(Collectors.toMap(
                        pair -> URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                        pair -> URLDecoder.decode(pair[1], StandardCharsets.UTF_8))
                );
    }

    /**
     * Handles requests sent to the api wether it is directly from the API or from the front-end GUI
     * @param pepData data which the request pertains to
     * @param request request recieved
     * @return response appropriate to who it was that requested a response
     */
    private static Response handleRequest(List<PepDataRow> pepData, Request request) {
        Header applicationJson = new Header("Content-Type", "application/json");
        byte[] jsonString = Json.createValue("Not Found").toString().getBytes(StandardCharsets.UTF_8);
        Response fourOhFour = new Response(404, "OK", List.of(applicationJson),jsonString);
        var queryParams = parseQueryParams(request.uri());
        JsonObject finalJson;

        if (request.uri().equals("/") || request.uri().equals("/index.html")) {
            return frontendResponse();
        }
        else if (request.uri().startsWith("/api/pep")) {
            if (queryParams.containsKey("name")) {
                pepData = PepDataSearcher.searchByName(pepData, queryParams.get("name"));
            }
            if (queryParams.containsKey("dataset")) {
                pepData = PepDataSearcher.searchByDataset(pepData, queryParams.get("dataset"));
            }
            finalJson = getJsonObject(pepData);
            return new Response(
                    200,
                    "OK",
                    List.of(applicationJson),
                    finalJson.toString().getBytes(StandardCharsets.UTF_8));
        }
        else {
           return fourOhFour;
        }
    }

    /**
     * Extracted method to handle a request made from the front-end UI
     * @return a response that the front-end UI can handle in HTML
     */
    private static Response frontendResponse() {
        try (var stream = App.class.getClassLoader().getResourceAsStream("index.html")){
            Header textHeader = new Header("Content-Type", "text/html");
            return new Response(
                    200,
                    "OK",
                    List.of(textHeader),
                    Objects.requireNonNull(stream).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method turns data into a JSON object
     * @param pepData data to be turned into JSON
     * @return JSON object of data
     */
    private static JsonObject getJsonObject(List<PepDataRow> pepData) {
        var jsonData = Json.createArrayBuilder(pepData.stream().map(row -> row.toJson()).toList())
                .build();

        var finalJson = Json.createObjectBuilder()
                .add("numberOfHits", pepData.size())
                .add("data", jsonData)
                .build();
        return finalJson;
    }
}
