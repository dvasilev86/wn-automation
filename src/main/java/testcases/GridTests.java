package testcases;

import base.BaseWebTest;
import org.junit.Test;

/**
 * @author by dvasilev on 14-Sep-17.
 */
public class GridTests extends BaseWebTest {

    @Test
    public void searchAccByNameSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void searchAccByDobSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void searchAccNoResults() {
        mainPage.getMainPage();
    }

    @Test
    public void gridShow10Entries() {
        mainPage.getMainPage();
    }

    @Test
    public void gridShow25Entries() {
        mainPage.getMainPage();
    }

    @Test
    public void gridShow50Entries() {
        mainPage.getMainPage();
    }

    @Test
    public void gridShow100Entries() {
        mainPage.getMainPage();
    }

    @Test
    public void gridNextPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void gridNextPageFailure() {
        mainPage.getMainPage();
    }

    @Test
    public void gridPreviousPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void gridPreviousPageFailure() {
        mainPage.getMainPage();
    }

    @Test
    public void gridShowSpecificPageSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void gridSortByName() {
        mainPage.getMainPage();
    }

    @Test
    public void gridSortByDob() {
        mainPage.getMainPage();
    }
}
