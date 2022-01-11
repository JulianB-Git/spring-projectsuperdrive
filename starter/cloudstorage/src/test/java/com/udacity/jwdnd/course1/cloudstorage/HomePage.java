package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    public WebDriverWait wait;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    //Note elements
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "new-note")
    private WebElement newNoteButton;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTextArea;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "save-note")
    private WebElement saveNoteButton;

    @FindBy(id = "note-title-row")
    private WebElement noteTitleHeader;

    @FindBy(id = "success-link")
    private WebElement successLink;

    @FindBy(id = "note-description-row")
    private WebElement noteDescriptionData;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    //Credential Elements
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "new-credentials-button")
    private WebElement newCredentialsButton;

    @FindBy(id = "credential-url")
    private WebElement credentialsURLInput;

    @FindBy(id = "credential-username")
    private WebElement credentialsUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialsPasswordInput;

    @FindBy(id = "save-credentials-button")
    private WebElement saveCredentialsButton;

    @FindBy(id = "credentials-url-row")
    private WebElement credentialsURLRow;

    @FindBy(id = "credentials-username-row")
    private WebElement credentialsUsernameRow;

    @FindBy(id = "credentials-password-row")
    private WebElement credentialsPasswordRow;

    @FindBy(id = "edit-credentials-button")
    private WebElement editCredentialsButton;

    @FindBy(id = "delete-credentials-button")
    private WebElement deleteCredentialsButton;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void logout() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.logoutButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Note Methods
    public void addAndVerifyNote(String title, String description) {
        switchToTab("nav-notes-tab");

        newNoteButton.click();
        noteTitleInput.sendKeys(title);
        noteDescriptionTextArea.sendKeys(description);
        saveNoteButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-notes-tab");

        noteTitleHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-row")));
        noteDescriptionData = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-row")));

        Assertions.assertEquals(title, this.noteTitleHeader.getText());
        Assertions.assertEquals(description, this.noteDescriptionData.getText());
    }

    public void editNote(String title, String description) {
        this.editNoteButton.click();

        this.noteTitleInput.clear();
        this.noteTitleInput.sendKeys(title);
        this.noteDescriptionTextArea.clear();
        this.noteDescriptionTextArea.sendKeys(description);
        this.saveNoteButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-notes-tab");

        noteTitleHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-row")));
        noteDescriptionData = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-row")));

        Assertions.assertEquals(title, noteTitleHeader.getText());
        Assertions.assertEquals(description, noteDescriptionData.getText());
    }

    public void deleteNote() {
        this.deleteNoteButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-notes-tab");

        Assertions.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("note-title-row"))));
    }

    //Credential methods
    public void addCredentials(String url, String username, String password) {
        switchToTab("nav-credentials-tab");

        newCredentialsButton.click();
        credentialsURLInput.sendKeys(url);
        credentialsUsernameInput.sendKeys(username);
        credentialsPasswordInput.sendKeys(password);
        saveCredentialsButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-credentials-tab");

        credentialsURLRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-url-row")));
        credentialsUsernameRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-username-row")));
        credentialsPasswordRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-password-row")));

        Assertions.assertEquals(url, credentialsURLRow.getText());
        Assertions.assertEquals(username, credentialsUsernameRow.getText());
        Assertions.assertNotEquals(password, credentialsPasswordRow.getText());
    }

    public void editCredentials(String url, String username, String oldPassword, String newPassword) {
        this.editCredentialsButton.click();

        credentialsPasswordInput = this.waitForElementVisible(By.id("credential-password"));
        Assertions.assertEquals(oldPassword, credentialsPasswordInput.getAttribute("value"));

        this.credentialsURLInput.clear();
        this.credentialsURLInput.sendKeys(url);
        this.credentialsUsernameInput.clear();
        this.credentialsUsernameInput.sendKeys(username);
        this.credentialsPasswordInput.clear();
        this.credentialsPasswordInput.sendKeys(newPassword);
        this.saveCredentialsButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-credentials-tab");

        credentialsURLRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-url-row")));
        credentialsUsernameRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-username-row")));
        credentialsPasswordRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-password-row")));

        Assertions.assertEquals(url, credentialsURLRow.getText());
        Assertions.assertEquals(username, credentialsUsernameRow.getText());
        Assertions.assertNotEquals(newPassword, credentialsPasswordRow.getText());
    }

    public void deleteCredentials() {
        this.deleteCredentialsButton.click();

        verifyAndClickSuccessLink();

        switchToTab("nav-credentials-tab");

        Assertions.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("credentials-url-row"))));
    }

    private void switchToTab(String tabName) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement tabElement = this.waitForElementVisible(By.id(tabName));
        tabElement.click();
    }

    private void verifyAndClickSuccessLink(){
        successLink = wait.until(ExpectedConditions.elementToBeClickable(successLink));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        successLink.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement waitForElementVisible(By elementBySelector) {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(elementBySelector));
    }
}
