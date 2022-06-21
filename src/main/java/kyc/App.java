package kyc;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.json.Json;
import org.microhttp.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class App {
    public static void main(String[] args) throws IOException {
        // load file
        var pepData = PepDataRow.loadFromResource("pep.csv");

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
            } catch (Exception __) {
                consumer.accept(new Response(500, "ERR", List.of(), "Unexpected Error".getBytes(StandardCharsets.UTF_8)));
            }
        });
        loop.start();

    }

    // Gets the query params after the /api/pep?
    private static Map<String, String> parseQueryParams(String uri) {
        var splitUri = uri.split("\\?");
        if (splitUri.length < 2) {
            return Map.of();
        }

        var queryParams = splitUri[1];
        return Arrays.stream(queryParams.split("&"))
                .map(paramPair -> paramPair.split("="))
                .collect(Collectors.toMap(
                        pair -> URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                        pair -> URLDecoder.decode(pair[1], StandardCharsets.UTF_8))
                );
    }

    private static Response handleRequest(List<PepDataRow> pepData, Request request) {
        if (request.uri().startsWith("/api/pep")) {
            var queryParams = parseQueryParams(request.uri());

            if (queryParams.containsKey("name")) {
                pepData = PepDataSearcher.searchByName(pepData, queryParams.get("name"));
            }

            if (queryParams.containsKey("dataset")) {
                pepData = PepDataSearcher.searchByDataset(pepData, queryParams.get("dataset"));
            }

            var jsonData = Json.createArrayBuilder(pepData.stream().map(row -> row.toJson()).toList())
                    .build();

            var finalJson = Json.createObjectBuilder()
                    .add("numberOfHits", pepData.size())
                    .add("data", jsonData)
                    .build();

            return new Response(200, "OK", List.of(
                    new Header("Content-Type", "application/json")
            ), finalJson.toString().getBytes(StandardCharsets.UTF_8));
        }

        else {
           return new Response(
                   404,
                   "OK",
                   List.of(new Header("Content-Type", "application/json")),
                   Json.createValue("Not Found")
                           .toString()
                           .getBytes(StandardCharsets.UTF_8));
        }

    }
}
