package main;

import kyc.PepDataRow;
import kyc.PepDataSearcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PepDataSearchTest {
    /**
     * Helper method to create data
     * @return List of rows with data.
     */
    private List<PepDataRow> createData() {
        List<String> aliases = List.of("RJ","rob");
        PepDataRow pep1 = new PepDataRow("123","Person","Robin Jain",aliases,
                "1997-11-16","Norway","NA","a","none","none",
                " ",List.of("NO","CIA"),"never","year 1");
        PepDataRow pep2 = new PepDataRow("234","Person","Knut Arild",aliases,
                "1997-11-16","Norway","NA","a","none","none",
                " ",List.of("CIA","Stacc"),"never","year 1");
        PepDataRow pep3 = new PepDataRow("345","Person","Are Ketil",aliases,
                "1997-11-16","Norway","NA","a","none","none",
                " ",List.of("NO","AXE"),"never","year 1");
        PepDataRow pep4 = new PepDataRow("1010","Person","robin jain",aliases,
                "1997-11-17","Norway","NA","a","none","none",
                " ",List.of("NO","Stacc"),"never","year 1");
        return List.of(pep1,pep2,pep3,pep4);

    }
    List<PepDataRow> testData = this.createData();
    List<PepDataRow> results;

    /**
     * This is a test to make sure search by name works for a unique name.
     */
    @Test
    public void searchByNameSingleEntryTest() {
        List<PepDataRow> result = PepDataSearcher.searchByName(testData,"Are Ketil");
        Assert.assertEquals(1,result.size());
        String name = result.get(0).name();
        Assert.assertEquals("Are Ketil",name);
    }

    /**
     * This is a test to make sure when searching for names that are not unique, that all entries are found
     */
    @Test
    public void searchByNameMultipleTest() {
        results = PepDataSearcher.searchByName(testData,"Robin Jain");
        Assert.assertEquals(2,results.size());

        String name1 = results.get(0).name();
        String name2 = results.get(1).name();
        Assert.assertTrue(name1.equalsIgnoreCase(name2));
    }

    /**
     * Simple dataset search test
     */
    @Test
    public void searchByDatasetTest() {
        results = PepDataSearcher.searchByDataset(testData,"AXE");
        Assert.assertEquals(1,results.size());

        String id = results.get(0).id();
        Assert.assertEquals("345",id);
    }

    /**
     * Test to make sure all are found for a dataset with multiple people
     */
    @Test
    public void searchByDatasetMultipleTest() {
        results = PepDataSearcher.searchByDataset(testData,"stacc");
        Assert.assertEquals(2,results.size());

        String id1 = results.get(0).id();
        String id2 = results.get(1).id();
        Assert.assertEquals("234",id1);
        Assert.assertEquals("1010",id2);
    }

    /**
     * Test to make sure searching by BirthDate works
     */
    @Test
    public void searchByBirthDateTest() {
        String birthDate = "1997-11-17";
        results = PepDataSearcher.searchByBirthDate(testData,birthDate);
        Assert.assertEquals(1,results.size());

        String name = results.get(0).name();
        Assert.assertEquals("robin jain",name);
    }


}
