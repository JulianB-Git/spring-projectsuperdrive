package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	public static WebDriverWait wait;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 2);
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testUserAccess() {
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get(baseURL + "/note");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testSignupLoginAndLogout()  {

		String username = "Julian";
		String password = "S3cr3t!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testSignUpLoginAndAddAndVerifyNote() {
		String username = "Ben";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addAndVerifyNote("Shopping List", "Bread, Milk");
	}

	@Test
	public void editNote() {
		String username = "Peter";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addAndVerifyNote("Shopping List", "Bread, Milk");
		homePage.editNote("Todo", "Make a shopping list");
	}

	@Test
	public void deleteNote() {
		String username = "Edward";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addAndVerifyNote("Shopping List", "Bread, Milk");
		homePage.deleteNote();
	}

	@Test
	public void addNewCredentials() {
		String username = "Snow";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addCredentials("http://localhost:8080", "Julian", "secretpassword");
	}

	@Test
	public void editCredentials() {
		String username = "Jim";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addCredentials("http://localhost:8080", "Julian", "secretpassword");
		homePage.editCredentials("http://localhost:8080/login", "JBenade", "secretpassword", "newSecretPassword");
	}

	@Test
	public void deleteCredentials() {
		String username = "Tim";
		String password = "P@$$word!";

		signup(username, password);
		login(username, password);
		HomePage homePage = new HomePage(driver, wait);

		homePage.addCredentials("http://localhost:8080", "Julian", "secretpassword");
		homePage.deleteCredentials();
	}

	public void signup(String username, String password) {
		SignupPage signupPage = new SignupPage(driver);

		driver.get(baseURL + "/signup");
		signupPage.signup("Julian", "Benade", username, password);
	}

	public void login(String username, String password) {
		LoginPage loginPage = new LoginPage(driver);
		//driver.get(baseURL + "/login");
		loginPage.login(username, password);
	}
}
