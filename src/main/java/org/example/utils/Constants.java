package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String BASE_URL = "http://117.252.249.5/StudentPortal/default.aspx/";

    //todo: Try to add more meaningful names to constants

    //Login Page
    public static final String FORGOT_PSWD_TEXT_LABEL_ID = "lblforgetpass";
    public static final String PASSWORD_TEXT_INPUT_ID = "txt_password";
    public static final String BTN_SUBMIT_LOGINPAGE_ID = "btnSubmit";

    //Forgot Password Page
    public static final String USERNAME_TEXT_INPUT_ID = "txt_username";
    public static final String NEW_PASS_TEXT_INPUT_ID = "txtnewpass";
    public static final String CONFIRM_PASS_TEXT_INPUT_ID = "txtConfirmpass";
    public static final String BTN_SUBMIT_ID = "btnSubmit";
    public static final String UNIVERSAL_NEW_PASS_VALUE = "123";

    //Results Page
    public static final String SELECT_SEMESTER_DROPDOWN_BOX_ID = "ddlSemester";
    public static final String SHOW_RESULT_BTN_ID = "btnimgShowResult";
    public static final String STUDENT_NAME_LABEL_ID = "lblStudentName";
    public static final String STUDENT_CGPA_LABEL_ID = "lblCPI";
    public static final String STUDENT_RESULT_LABEL_ID = "lblResult";
    public static final String LOGOUT_BTN_ID = "lnklogout";

    //College Related Constants
    public static final List<String> collegeBranchCodes = Arrays.asList("cs", "ec", "ee", "mm", "me", "ce", "pi");
    public static final List<String> collegeYears = Arrays.asList("2020");
    public static final List<String> collegeCourses = Arrays.asList("ug");
    public static final int MAX_ROLL_NUMBER_EXPECTED = 100;//just a random guess that no class would have as many students

    public static final String OUTPUT_FILE_PATH = "output.xslx";

}
