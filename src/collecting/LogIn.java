package collecting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogIn
{
    private String email, password;
    private WebDriver driver;
    public LogIn(WebDriver driver, String email, String password)
    {
        this.email = email;
        this.password = password;
        this.driver = driver;
    }

    public void openAccess()
    {
        driver.get("https://accounts.spotify.com/en/login?continue=https%3A%2F%2Fcharts.spotify.com/login");


        WebElement emailFrame = driver.findElement(By.id("login-username"));
        WebElement passwordFrame = driver.findElement(By.id("login-password"));

        emailFrame.sendKeys(email);
        passwordFrame.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

}
