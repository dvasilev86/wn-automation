package base;

import common.Config;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.MainPage;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author by dvasilev on 13-Sep-17.
 */
public class BaseWebTest {
    protected MainPage mainPage;
    private RemoteWebDriver driver;

    @Before
    public void Init() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.chrome();

        driver = new RemoteWebDriver(new URL(Config.HUB_URL), capability);
        mainPage = new MainPage(driver);
    }

    @After
    public void Close() {
        driver.quit();
    }
}
