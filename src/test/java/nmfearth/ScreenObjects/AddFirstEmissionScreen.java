package nmfearth.ScreenObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddFirstEmissionScreen extends ScreenBase {

    public MobileElement addFirstEmissionButton;

    public AddFirstEmissionScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        addFirstEmissionButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("addMyFirstEmission"));
    }

    public void clickAddFirstEmissionButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addFirstEmissionButton));
        addFirstEmissionButton.click();
    }
}
