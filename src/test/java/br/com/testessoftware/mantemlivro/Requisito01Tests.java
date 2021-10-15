package br.com.testessoftware.mantemlivro;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@SpringBootTest
public class Requisito01Tests {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    public void cadastralivro() {
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.id("isbn")).click();
        driver.findElement(By.id("isbn")).sendKeys("2222");
        driver.findElement(By.id("autor")).sendKeys("Jose Andrade");
        driver.findElement(By.id("titulo")).sendKeys("A Felicidade");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        Assertions.assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
    }

    @Test
    @Order(2)
    public void alteralivro() {
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.linkText("Lista de Livros")).click();
        // Descobrir como localizar o botão Editar pelo ISBN informado
        // vars.put("ISBN", driver.findElement(By.name("2222")));
        // driver.findElement(By.id("isbn")).findElement(By.name("2222")).click();
        driver.findElement(By.linkText("Editar")).click();
        driver.findElement(By.cssSelector(".card-body")).click();
        driver.findElement(By.id("autor")).click();
        driver.findElement(By.id("autor")).clear();
        driver.findElement(By.id("autor")).sendKeys("Jose De Andrade");
        driver.findElement(By.id("titulo")).click();
        driver.findElement(By.id("titulo")).clear();
        driver.findElement(By.id("titulo")).sendKeys("A Busca Pela Felicidade");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector("html")).click();
        Assertions.assertEquals("A Busca Pela Felicidade", driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(3)")).getText());
        driver.findElement(By.cssSelector("html")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(4)")).click();
        Assertions.assertEquals("Jose De Andrade", driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(4)")).getText());
        driver.findElement(By.cssSelector("html")).click();
    }

    @Test
    @Order(3)
    public void excluirlivro() {
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.linkText("Lista de Livros")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")).click();
        vars.put("isbn", driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")).getText());
        driver.findElement(By.cssSelector("html")).click();
        driver.findElement(By.linkText("Excluir")).click();

        String line1 = driver.findElement(By.cssSelector("tbody")).getText();
        if (!Objects.equals(line1, "")) {
            Assertions.assertNotEquals(vars.get("isbn"), driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")));
        }
    }
}
