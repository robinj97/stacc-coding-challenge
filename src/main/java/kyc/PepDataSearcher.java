package kyc;
import java.util.List;

/**
 * This class contains the different search criterias.
 * If new search criterias need to be developed please add them in this class.
 */
public final class PepDataSearcher {
    private PepDataSearcher() {}

    /**
     * This method allows you to search by full name
     * @param rows data to search over in the form of a list of PepDataRows
     * @param name name you want to search for
     * @return List of PepDataRows with matches for given name
     */
    public static List<PepDataRow> searchByName(List<PepDataRow> rows, String name) {
        return rows.stream()
                .filter(row -> row.name().equalsIgnoreCase(name))
                .toList();
    }

    /**
     * This method allows you to search the data through the dataset parameter
     * @param rows data you want to search over
     * @param datasetName name of the dataset for the person you are looking for
     * @return List of matches.
     */
    public static List<PepDataRow> searchByDataset(List<PepDataRow> rows, String datasetName) {
        return rows.stream()
                .filter(row -> row.dataset().stream()
                        .anyMatch(ds -> ds.equalsIgnoreCase(datasetName))
                )
                .toList();
    }

    /**
     * Searches the data by birthDate
     * @param rows list of rows to search over
     * @param birthDate the birthdate to search in the format YYYY-MM-DD
     * @return list of results matching this birthDate
     */
    public static List<PepDataRow> searchByBirthDate(List<PepDataRow> rows, String birthDate) {
        return rows.stream().filter(row -> row.birthDate().equals(birthDate)).toList();
    }
}
