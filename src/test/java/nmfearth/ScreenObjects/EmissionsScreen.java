package nmfearth.ScreenObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmissionsScreen extends ScreenBase {
    public MobileElement addButton;

    public EmissionsScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        addButton = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"Add\")"));
    }

    public void clickAddButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addButton));
        addButton.click();
    }

    public void clickAddButton(AndroidTouchAction actions) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addButton));
        actions.tap(ElementOption.element(addButton)).perform();
    }
}
