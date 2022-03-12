package nmfearth.ScreenObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainScreen extends ScreenBase {

    public MobileElement agreeButton;

    public MainScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        agreeButton = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("descriptionContains(\"agreeButton\")"));
    }

    public void clickAgreeButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(agreeButton));
        agreeButton.click();
    }
}
