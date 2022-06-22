package kyc;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a container for the data from the CSV file
 */
public record PepDataRow(
        String id,
        String schema,
        String name,
        String aliases,
        String birthDate,
        String countries,
        String addresses,
        String identifiers,
        String sanctions,
        String phones,
        String emails,
        List<String> dataset,
        String lastSeen,
        String firstSeen
) {
    /**
     * Creates a single row from a csv line
     * @param row an array of values
     * @return a PepDataRow class with values mapped to fields
     */
    public static PepDataRow fromCSVRow(String[] row) {
        return new PepDataRow(
                row[0],
                row[1],
                row[2],
                row[3],
                row[4],
                row[5],
                row[6],
                row[7],
                row[8],
                row[9],
                row[10],
                List.of(row[11].split(";")),
                row[12],
                row[13]
        );
    }

    /**
     * Takes a file and creates an organized list with values mapped to fields
     * @param fileName name of the csv file
     * @return List of organized rows and values
     */
    public static List<PepDataRow> loadFromResource(String fileName) {
        List<PepDataRow> pepData;
        // Load up file, this will handle just the file but also since its bundled it will handle if built from jar
        try (var data = PepDataRow.class.getClassLoader().getResourceAsStream(fileName)) {
            var reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(data)));
            pepData = reader.readAll().stream()
                    .skip(1)
                    .map(row -> PepDataRow.fromCSVRow(row))
                    .toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return pepData;
    }

    /**
     * This method turns record into a JSON object
     * @return JSON object of a PepDataRow
     */
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("schema", this.schema)
                .add("name", this.name)
                .add("aliases", this.aliases)
                .add("birthDate", this.birthDate)
                .add("countries", this.countries)
                .add("addresses", this.addresses)
                .add("identifiers", this.identifiers)
                .add("sanctions", this.sanctions)
                .add("phones", this.phones)
                .add("emails", this.emails)
                .add("dataset", Json.createArrayBuilder(this.dataset).build())
                .add("lastSeen", this.lastSeen)
                .add("firstSeen", this.firstSeen)
                .build();
    }
}
