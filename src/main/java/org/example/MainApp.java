package org.example;

import org.example.utils.Constants;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class MainApp {
   private static final String TEST_REG_NUMBER = "2020ugec40";
   private static final String TEST_SEMESTER = "VII";

    public static void main(String[] args) {
        //init the Driver
        WebDriver driver = new ChromeDriver();

        //Open the Website
        driver.get(Constants.BASE_URL);
        WebDriver.Options options = driver.manage();
        options.deleteAllCookies();//delete any cookies website might have stored
        options.window().maximize();//to full screen the window

        //Forgot Password
        WebElement forgotPasswordTextLabel = driver.findElement(By.id(Constants.FORGOT_PSWD_TEXT_LABEL_ID));
        forgotPasswordTextLabel.click();//This will navigate to 2nd page (Forgot Password)

        //Enter Roll_Number, Password, Confirm_Password
        WebElement usernameTextInput = driver.findElement(By.id(Constants.USERNAME_TEXT_INPUT_ID));
        WebElement newPassTextInput = driver.findElement(By.id(Constants.NEW_PASS_TEXT_INPUT_ID));
        WebElement confirmPassTextInput = driver.findElement(By.id(Constants.CONFIRM_PASS_TEXT_INPUT_ID));
        WebElement submitBtn = driver.findElement(By.id(Constants.BTN_SUBMIT_ID));
        usernameTextInput.sendKeys(TEST_REG_NUMBER);
        newPassTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);
        confirmPassTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);

        //Click Submit
        submitBtn.click();

        //Wait for alert dialog to come
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3L));
        Alert alert = driver.switchTo().alert();
        alert.accept();

        //Wait for Page to Change
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3L));

        //Now Login to Website
        WebElement usernameTextInput2 = driver.findElement(By.id(Constants.USERNAME_TEXT_INPUT_ID));
        WebElement passwordTextInput = driver.findElement(By.id(Constants.PASSWORD_TEXT_INPUT_ID));
        WebElement submitBtn2 = driver.findElement(By.id(Constants.BTN_SUBMIT_LOGINPAGE_ID));
        usernameTextInput2.sendKeys(TEST_REG_NUMBER);
        passwordTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);
        submitBtn2.click();

        //Show Result for given Sem
        WebElement semesterInputBox = driver.findElement(By.id(Constants.SELECT_SEMESTER_DROPDOWN_BOX_ID));
        WebElement showResultBtn = driver.findElement(By.id(Constants.SHOW_RESULT_BTN_ID));
        Select selectSemester = new Select(semesterInputBox);
        selectSemester.selectByVisibleText(TEST_SEMESTER);
        showResultBtn.click();

    }
}
