package testcases;

import base.BaseWebTest;
import org.junit.Test;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class WebTest extends BaseWebTest {

    @Test
    public void Test() {
        mainPage.GetMainPage();
    }
}
