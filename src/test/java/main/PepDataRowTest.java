package main;

import kyc.PepDataRow;
import org.junit.Assert;
import org.junit.Test;

public class PepDataRowTest {

    /**
     * Test to make sure the CSV mapper works when properly populated
     */
    @Test
    public void fromCSVRowTest() {
        String[] dataset = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n"};
        PepDataRow singleRow = PepDataRow.fromCSVRow(dataset);
        Assert.assertEquals("a",singleRow.id());
        Assert.assertEquals("c",singleRow.name());
        Assert.assertEquals("e",singleRow.birthDate());
        Assert.assertEquals(1,singleRow.dataset().size());
    }

    /**
     * Test to make sure an index out of bounds exception is thrown in case data is not correctly indexed
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void fromCSVExceptionTest() {
        String[] dataset = {"a","b","c","d","e","f","g","h","i","j","k","l","m"};
        PepDataRow singleRow = PepDataRow.fromCSVRow(dataset);
    }

}
