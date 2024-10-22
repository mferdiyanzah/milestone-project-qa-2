package com.milestone;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Main {

    public static void main(String[] args) {
        // Creating webdriver instance
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Open web application
        driver.get("https://google.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Enter data into textboxes
        driver.findElement(By.name("q")).sendKeys("bona tampan dan berani");
        // driver.findElement(By.id("change_className")).sendKeys("test_class");
        // driver.findElement(By.id("Submit")).click();
        // driver.findElement(By.id("change_id")).sendKeys("updated_test_id");
        // driver.findElement(By.id("change_className")).sendKeys("updated_test_class");
    }

}