package HaeAppthon.joyroom.controller;

import HaeAppthon.joyroom.packet.responsebody.CodyResponseBody;import HaeAppthon.joyroom.packet.resquestbody.CodyRequestBody;
import HaeAppthon.joyroom.packet.resquestbody.DetailRequestBody;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@ResponseBody
@CrossOrigin(allowedHeaders = "*")
@Component
public class CodyController {
    //@GetMapping("/")
    public String hello() {
        return "hellllo";
    }

    @Autowired
    private WebDriver driver;

    List<WebElement> elements;

    @PostMapping ("/codies")
    public List<CodyResponseBody> crawling_codies(@RequestBody CodyRequestBody requestBody) {

        // 무신사 코디맵 페이지 접속
        driver.get("https://www.musinsa.com/app/codimap/lists?tag_no=" + requestBody.getTag());

        if (requestBody.getGender() == 0) {  // 남자
            driver.findElement(By.xpath("//*[@id=\"footerCommonPc\"]/div[1]/button[2]")).click();
        }
        else {  // 여자
            driver.findElement(By.xpath("//*[@id=\"footerCommonPc\"]/div[1]/button[3]")).click();
        }


        List<CodyResponseBody> responseBodies = new ArrayList<>();

        elements = driver.findElements(By.className("style-list-thumbnail__img"));
        for (int i =0 ; i < 9; ++i) {
            System.out.println(elements.get(i).getAttribute("src"));
            CodyResponseBody cr = new CodyResponseBody();
//            cr.setCody_url(elements.get(i).getAttribute("src"));
            cr.setImage_url(elements.get(i).getAttribute("src"));
            responseBodies.add(cr);
        }

        return responseBodies;
    }

    @PostMapping ("/detail")
    public List<String> detail_cody(@RequestBody DetailRequestBody requestBody) {
        List<String> responseBody = new ArrayList<>();

        elements.get(requestBody.getNumber()).click();
        WebElement element = driver.findElement(By.className("photo"));
        responseBody.add(element.getAttribute("src"));

        List<WebElement> detailElements = new ArrayList<>();
        detailElements = driver.findElements(By.className("styling_img"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"style_info\"]/div[3]/div/div/div/div[1]/div[1]/div[1]/a/img")));
        for (WebElement detailElement : detailElements) {
            WebElement imgTag = detailElement.findElement(By.tagName("img"));
            responseBody.add(imgTag.getAttribute("src"));
        }

        return responseBody;
    }
}
