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
        DesiredCapabilities capability;

            if (System.getProperty("browser")!= null && (System.getProperty("browser").contentEquals("chrome"))) {
            capability = DesiredCapabilities.chrome();

        }

        else if (System.getProperty("browser")!= null && (System.getProperty("browser").contentEquals("firefox"))) {
            capability = DesiredCapabilities.firefox();
        }
        else {
                System.out.print("WARNING! SESSION IS RUNNING ON CHROME BECAUSE NO SYSTEM PROPERTY IS SET" +
                        "THIS IS TO MAKE SURE YOU CAN RUN TESTS FROM JAVA IDE WITHOUT CALLING GRADLE");
                capability = DesiredCapabilities.chrome();
            }

        driver = new RemoteWebDriver(new URL(Config.HUB_URL), capability);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @After
    public void Close() {
        driver.quit();
    }
}
