package nmfearth.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import nmfearth.ScreenObjects.AddEmissionScreen;
import nmfearth.ScreenObjects.AddFirstEmissionScreen;
import nmfearth.ScreenObjects.BudgetScreen;
import nmfearth.ScreenObjects.EmissionsScreen;
import nmfearth.ScreenObjects.MainScreen;
import nmfearth.components.Emission;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class AndroidTests {

    public AppiumDriver driver;
    public AndroidTouchAction actions;

    private void scrollDown() {
        Dimension dimension = driver.manage().window().getSize();
        int scrollStart = (int) (dimension.getHeight() * 0.8);
        int scrollEnd = (int) (dimension.getHeight() * 0.1);

        actions = new AndroidTouchAction(driver)
                .press(PointOption.point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(PointOption.point(0, scrollEnd))
                .release()
                .perform();
    }

    public void scrollDownMultipleTimes(int n) {
        for (int i = 0; i < n; i++) {
            scrollDown();
        }
    }

    private void scrollUp() {
        Dimension dimension = driver.manage().window().getSize();
        int scrollStart = (int) (dimension.getHeight() * 0.1);
        int scrollEnd = (int) (dimension.getHeight() * 0.8);

        actions = new AndroidTouchAction(driver)
                .press(PointOption.point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(PointOption.point(0, scrollEnd))
                .release()
                .perform();
    }

    public void scrollUpMultipleTimes(int n) {
        for (int i = 0; i < n; i++) {
            scrollUp();
        }
    }

    public double getTotalKgCO2Value(ArrayList<Emission> emissions) {
        double total = 0; // initial value
        for (Emission emission : emissions) {
            total += Double.parseDouble(emission.getKgCO2Value().split(" ")[0]);
        }

        return total;
    }

    public Emission getLastEmission(ArrayList<Emission> emissions) {
        return emissions.get(emissions.size() - 1);
    }

    public EmissionsScreen addMultipleEmissions(String emissionName, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            AddEmissionScreen addEmissionScreen = new AddEmissionScreen(driver);
            addEmissionScreen.clickNameThisEmissionButton();
            addEmissionScreen.nameThisEmissionTextInput = (MobileElement) driver.findElement
                    (MobileBy.AccessibilityId("Name this emission _TEXTINPUT"));
            addEmissionScreen.sendEmissionTextInput(emissionName);
            scrollDown();
            addEmissionScreen.addThisEmissionButton = (MobileElement) driver.findElement
                    (MobileBy.AndroidUIAutomator("descriptionContains(\"addEmission\")"));
            addEmissionScreen.clickAddThisEmissionButton(actions);
            EmissionsScreen emissionsScreen = new EmissionsScreen(driver);
            if (i < numberOfTimes - 1) {
                scrollDown();
                emissionsScreen.clickAddButton();
            }
            else {
                return emissionsScreen;
            }
        }
        return new EmissionsScreen(driver);
    }

    public List<MobileElement> getTextLabels(AppiumDriver driver) {
        return driver.findElements(MobileBy.AndroidUIAutomator
                ("descriptionContains(\"TEXT_LABEL\")"));
    }

    public List<MobileElement> getKgCO2Texts(AppiumDriver driver) {
        return driver.findElements(MobileBy.AndroidUIAutomator
                ("textContains(\"kgCO2\")"));
    }

    public ArrayList<Emission> getEmissions(AppiumDriver driver) {
        ArrayList<Emission> emissions = new ArrayList<>();
        ArrayList<String> emissionNames = new ArrayList<>();
        assert getTextLabels(driver).size() >= getKgCO2Texts(driver).size();
        for (MobileElement textLabel : getTextLabels(driver)) {
            if (textLabel != null) {
                emissionNames.add(textLabel.getText());
            }
        }

        emissionNames.remove(0);
        ArrayList<String> kgCO2Values = new ArrayList<>();
        for (MobileElement kgCO2Text : getKgCO2Texts(driver)) {
            if (kgCO2Text != null) {
                kgCO2Values.add(kgCO2Text.getText());
            }
        }

        int minimumSize = Math.min(emissionNames.size(), kgCO2Values.size());

        for (int i = 0; i < minimumSize; i++) {
            emissions.add(new Emission(emissionNames.get(i), kgCO2Values.get(i)));
        }
        return emissions;
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformVersion", "9.0");
        capabilities.setCapability("deviceName", "Pixel 4 XL API 28");
        capabilities.setCapability("app", System.getProperty("user.dir") +
                "\\apps\\not-my-fault-earth-87f2063490a446feb1af0201d6ded75b-signed.apk");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.clickAgreeButton();
        AddFirstEmissionScreen addFirstEmissionScreen = new AddFirstEmissionScreen(driver);
        addFirstEmissionScreen.clickAddFirstEmissionButton();
        AddEmissionScreen addEmissionScreen = new AddEmissionScreen(driver);
        addEmissionScreen.clickNameThisEmissionButton();
        addEmissionScreen.initialiseNameThisEmissionTextInput();
        addEmissionScreen.sendEmissionTextInput("groceries");
        scrollDown();
        addEmissionScreen.initialiseAddThisEmissionButton();
        addEmissionScreen.clickAddThisEmissionButton();
        BudgetScreen budgetScreen = new BudgetScreen(driver);
        budgetScreen.clickEmissionScreenButton();
    }

    @Test
    public void testCase01() {
        ArrayList<Emission> emissions = getEmissions(driver);
        assertEquals("The first added emission should have been added!", emissions.size(), 1);
        assertEquals("The first added emission with name 'groceries' should have been added",
                emissions.get(0).getName(), "groceries");
    }

    @Test
    public void testCase02() {
        EmissionsScreen emissionsScreen = new EmissionsScreen(driver);
        emissionsScreen.clickAddButton();
        emissionsScreen = addMultipleEmissions("Car", 20);
        scrollDownMultipleTimes(3);
        ArrayList<Emission> emissions = getEmissions(driver);
        assertEquals("The first added emission with name 'groceries' should have been " +
                        "visible at the bottom of the page!",
                getLastEmission(emissions).getName(), "groceries");
    }

    @Test
    public void testCase03() {
        EmissionsScreen emissionsScreen = new EmissionsScreen(driver);
        ArrayList<Emission> emissions = getEmissions(driver);
        double initialKgCO2Total = getTotalKgCO2Value(emissions);
        emissionsScreen.clickAddButton(actions);
        AddEmissionScreen addEmissionScreen = new AddEmissionScreen(driver);
        addEmissionScreen.clickElectricityButton();
        addEmissionScreen.initialiseElectricitySlider();
        addEmissionScreen.initialiseKgCO2EqTextView();
        scrollUp();
        double oldKgCO2EqValue = addEmissionScreen.getElectricityKgCO2EqValue();
        assertEquals(oldKgCO2EqValue, 51.9, 0.01);
        addEmissionScreen.electricitySlider = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator
                ("className(\"android.widget.SeekBar\")"));
        addEmissionScreen.setElectricitySliderValue(0.315);
        double newKgCO2EqValue = addEmissionScreen.getElectricityKgCO2EqValue();
        scrollDown();
        assertEquals(newKgCO2EqValue, 130.14, 0.01);
        addEmissionScreen.initialiseAddThisEmissionButton();
        addEmissionScreen.clickAddThisEmissionButton(actions);
        scrollUp();
        emissions = getEmissions(driver);
        assertEquals(getTotalKgCO2Value(emissions), initialKgCO2Total + 130.14, 0.01);
    }

    @AfterEach
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
