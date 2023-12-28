package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.utils.Constants;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class MainApp {
    private static final String TEST_REG_NUMBER = "2020ugec065";
    private static final String TEST_SEMESTER = "VII";
    private WebDriver driver;
    Workbook workbook;
    Sheet sheet;

    public static void main(String[] args) {
        MainApp app = new MainApp();

        app.init();
        app.openWebsite();
        app.extractStudentsResults();
    }

    private void init() {
        //init the Driver
        driver = new ChromeDriver();

        //init Workbook and Sheet
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Results");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Reg. Number");
        headerRow.createCell(1).setCellValue("Student Name");
        headerRow.createCell(2).setCellValue("CGPA");
        headerRow.createCell(3).setCellValue("Result");
    }

    private void openWebsite() {
        //Open the Website
        driver.get(Constants.BASE_URL);
        WebDriver.Options options = driver.manage();
        //options.deleteAllCookies();//delete any cookies website might have stored
        options.window().maximize();//to full screen the window
    }

    private void extractStudentsResults() {
        for (String year : Constants.collegeYears) {
            for (String courseCode : Constants.collegeCourses) {
                for (String branchCode : Constants.collegeBranchCodes) {
                    processCombination(year, courseCode, branchCode);
                }
            }
        }
    }

    private void processCombination(String year, String courseCode, String branchCode) {
        for (int rollNumber = 1; rollNumber <= Constants.MAX_ROLL_NUMBER_EXPECTED; rollNumber++) {
            String formattedRollNumber = String.format("%03d", rollNumber);
            String registrationNumber = generateRegistrationNumber(year, courseCode, branchCode, formattedRollNumber);
            resetPasswordToOurValue(registrationNumber);
            loginUser(registrationNumber);
            showResultsForUser(registrationNumber);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));

            // Locate the elements corresponding to lblStudentName, lblCPI, and lblResult
            WebElement studentNameElement = driver.findElement(By.id(Constants.STUDENT_NAME_LABEL_ID));
            WebElement cgpaElement = driver.findElement(By.id(Constants.STUDENT_CGPA_LABEL_ID));
            WebElement resultElement = driver.findElement(By.id(Constants.STUDENT_RESULT_LABEL_ID));

            // Get the text content of the elements
            String studentName = studentNameElement.getText();
            String cgpa = cgpaElement.getText();
            String result = resultElement.getText();
            extractUserResultToFile(registrationNumber, studentName, cgpa, result);
        }
    }

    private void extractUserResultToFile(String registrationNumber, String name, String cgpa, String result) {
        // Find the next available row index
        int rowIndex = sheet.getLastRowNum() + 1;

        // Create a new row
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(registrationNumber);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(cgpa);
        row.createCell(3).setCellValue(result);
        logoutUser();

        try (FileOutputStream fileOut = new FileOutputStream(Constants.OUTPUT_FILE_PATH)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResultsForUser(String registrationNumber) {
        //Show Result for given Sem
        WebElement semesterInputBox = driver.findElement(By.id(Constants.SELECT_SEMESTER_DROPDOWN_BOX_ID));
        WebElement showResultBtn = driver.findElement(By.id(Constants.SHOW_RESULT_BTN_ID));
        Select selectSemester = new Select(semesterInputBox);
        selectSemester.selectByVisibleText(TEST_SEMESTER);
        showResultBtn.click();
    }

    private void loginUser(String registrationNumber) {
        //Now Login to Website
        WebElement usernameTextInput2 = driver.findElement(By.id(Constants.USERNAME_TEXT_INPUT_ID));
        WebElement passwordTextInput = driver.findElement(By.id(Constants.PASSWORD_TEXT_INPUT_ID));
        WebElement submitBtn2 = driver.findElement(By.id(Constants.BTN_SUBMIT_LOGINPAGE_ID));
        usernameTextInput2.sendKeys(registrationNumber);
        passwordTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);
        submitBtn2.click();
    }

    private String generateRegistrationNumber(String year, String courseCode, String branchCode, String formattedRollNumber) {
        return year + courseCode + branchCode + formattedRollNumber;
    }

    private void resetPasswordToOurValue(String registrationNumber) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));

        //Forgot Password
        WebElement forgotPasswordTextLabel = driver.findElement(By.id(Constants.FORGOT_PSWD_TEXT_LABEL_ID));
        forgotPasswordTextLabel.click();//This will navigate to 2nd page (Forgot Password)

        //Enter Roll_Number, Password, Confirm_Password
        WebElement usernameTextInput = driver.findElement(By.id(Constants.USERNAME_TEXT_INPUT_ID));
        WebElement newPassTextInput = driver.findElement(By.id(Constants.NEW_PASS_TEXT_INPUT_ID));
        WebElement confirmPassTextInput = driver.findElement(By.id(Constants.CONFIRM_PASS_TEXT_INPUT_ID));
        WebElement submitBtn = driver.findElement(By.id(Constants.BTN_SUBMIT_ID));
        usernameTextInput.sendKeys(registrationNumber);
        newPassTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);
        confirmPassTextInput.sendKeys(Constants.UNIVERSAL_NEW_PASS_VALUE);

        //Click Submit
        submitBtn.click();

        //Wait for alert dialog to come
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        Alert alert = driver.switchTo().alert();
        alert.accept();

        //Wait for Page to Change
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
    }

    private void logoutUser() {
        WebElement logoutBtnElement = driver.findElement(By.id(Constants.LOGOUT_BTN_ID));
        logoutBtnElement.click();

        //Wait for Page to Change
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
    }
}
