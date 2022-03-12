package nmfearth.ScreenObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AddEmissionScreen extends ScreenBase {

    public MobileElement electricityButton;
    public MobileElement nameThisEmissionButton;
    public MobileElement nameThisEmissionTextInput;
    public MobileElement addThisEmissionButton;
    public MobileElement electricitySlider;
    public MobileElement kgCO2EqTextView;


    public AddEmissionScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        electricityButton = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"Electricity\")"));
        nameThisEmissionButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("Name this emission _BUTTON"));
    }

    public void initialiseElectricitySlider() {
        electricitySlider = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("className(\"android.widget.SeekBar\")"));
    }

    public void initialiseKgCO2EqTextView() {
        kgCO2EqTextView = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"kgCO2eq\")"));
    }

    public void initialiseNameThisEmissionTextInput() {
        nameThisEmissionTextInput = (MobileElement)
                driver.findElement(MobileBy.AccessibilityId("Name this emission _TEXTINPUT"));
    }

    public void initialiseAddThisEmissionButton() {
        addThisEmissionButton = (MobileElement)
                driver.findElement(MobileBy.AndroidUIAutomator("descriptionContains(\"addEmission\")"));
    }

    public void clickElectricityButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(electricityButton));
        electricityButton.click();
    }

    public void clickNameThisEmissionButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(nameThisEmissionButton));
        nameThisEmissionButton.click();
    }

    public void clickAddThisEmissionButton() {
        if (addThisEmissionButton != null) {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addThisEmissionButton));
            addThisEmissionButton.click();
        }
    }

    public void clickAddThisEmissionButton(AndroidTouchAction actions) {
        if (addThisEmissionButton != null) {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addThisEmissionButton));
            actions.tap(ElementOption.element(addThisEmissionButton)).perform();
        }
    }

    public void sendEmissionTextInput(String textInput) {
        if (nameThisEmissionTextInput != null) {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(nameThisEmissionTextInput));
            nameThisEmissionTextInput.clear();
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(nameThisEmissionTextInput));
            nameThisEmissionTextInput.sendKeys(textInput);
        }
    }

    public void setElectricitySliderValue(double newValue) {
        int start = electricitySlider.getLocation().getX();
        int end = electricitySlider.getSize().getWidth();
        int y = electricitySlider.getLocation().getY();
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(start, y)).moveTo(PointOption.point(end, y)).release().perform();
        int moveTo = (int) (end * newValue);
        action.press(PointOption.point(start, y)).moveTo(PointOption.point(moveTo, y)).release().perform();
    }

    public double getElectricityKgCO2EqValue() {
        return Double.parseDouble(kgCO2EqTextView.getText().split(" ")[0]);
    }
}
