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
