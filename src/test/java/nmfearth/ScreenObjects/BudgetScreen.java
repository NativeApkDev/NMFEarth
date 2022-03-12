package nmfearth.ScreenObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BudgetScreen extends ScreenBase {

    public MobileElement emissionScreenButton;
    public MobileElement setMonthlyBudgetButton;


    public BudgetScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        emissionScreenButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("Emissions, tab, 2 of 4"));
        setMonthlyBudgetButton = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"Set monthly budget\")"));
    }

    public void clickEmissionScreenButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(emissionScreenButton));
        emissionScreenButton.click();
    }
}
