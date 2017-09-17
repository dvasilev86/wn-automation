package testcases;

import base.BaseWebTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author by dvasilev on 14-Sep-17.
 */
public class GridWebTests extends BaseWebTest {

    @Test
    public void gridShow10Entries() {
        String desiredCount = "10";
        mainPage.getMainPage()
                .changeEntriesPerPage(desiredCount);
        String actualCount = String.valueOf(mainPage.getRowsCount());
        Assert.assertEquals("counts mismatch, is enough data present?", desiredCount, actualCount);
    }

    @Test
    public void gridShow25Entries() {
        String desiredCount = "25";
        mainPage.getMainPage()
                .changeEntriesPerPage(desiredCount);
        String actualCount = String.valueOf(mainPage.getRowsCount());
        Assert.assertEquals("counts mismatch, is enough data present?", desiredCount, actualCount);
    }

    @Test
    public void gridShow50Entries() {
        String desiredCount = "50";
        mainPage.getMainPage()
                .changeEntriesPerPage(desiredCount);
        String actualCount = String.valueOf(mainPage.getRowsCount());
        Assert.assertEquals("counts mismatch, is enough data present?", desiredCount, actualCount);
    }

    @Test
    public void gridShow100Entries() {
        String desiredCount = "100";
        mainPage.getMainPage()
                .changeEntriesPerPage(desiredCount);
        String actualCount = String.valueOf(mainPage.getRowsCount());
        Assert.assertEquals("counts mismatch, is enough data present?", desiredCount, actualCount);
    }

    @Test
    @Ignore
    public void gridNextPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridNextPageFailure() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridPreviousPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridPreviousPageFailure() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridShowSpecificPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridSortByName() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void gridSortByDob() {
        mainPage.getMainPage();
    }
}
