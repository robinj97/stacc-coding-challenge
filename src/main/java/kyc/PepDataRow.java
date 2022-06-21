package kyc;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

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

    public static List<PepDataRow> loadFromResource(String fileName) {
        List<PepDataRow> pepData;
        try (var data = PepDataRow.class.getClassLoader().getResourceAsStream(fileName)) {
            var reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(data)));
            pepData = reader.readAll().stream()
                    .skip(1)
                    .map(PepDataRow::fromCSVRow)
                    .toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return pepData;
    }

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
