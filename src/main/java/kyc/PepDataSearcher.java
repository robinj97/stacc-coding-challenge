package kyc;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;

public final class PepDataSearcher {
    private PepDataSearcher() {}

    //case insensitive name search
    public static List<PepDataRow> searchByName(List<PepDataRow> rows, String name) {
        return rows.stream()
                .filter(row -> row.name().equalsIgnoreCase(name))
                .toList();
    }

    public static List<PepDataRow> searchByDataset(List<PepDataRow> rows, String datasetName) {
        return rows.stream()
                .filter(row -> row.dataset().stream()
                        .anyMatch(ds -> ds.equalsIgnoreCase(datasetName))
                )
                .toList();
    }

    // Apache commons text has some stuff that can be helpful
    public static List<PepDataRow>  searchByNameFuzzy(List<PepDataRow> rows, String name) {
        var ld = LevenshteinDistance.getDefaultInstance();
        return rows.stream()
                .filter(row -> ld.apply(row.name().toLowerCase(), name.toLowerCase()) < 5)
                .toList();
    }
}
