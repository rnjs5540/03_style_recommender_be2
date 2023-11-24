package HaeAppthon.joyroom.controller;

import HaeAppthon.joyroom.packet.responsebody.CodyResponseBody;
import HaeAppthon.joyroom.packet.resquestbody.CodyRequestBody;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
public class CodyController {
    @GetMapping("/")
    public String hello() {
        return "hellllo";
    }


    @PostMapping ("/codies")
    public List<CodyResponseBody> crawling_codies(@RequestBody CodyRequestBody requestBody) {

        final int CODY_COUNT = 12;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        // 무신사 코디맵 페이지 접속
        driver.get("https://www.musinsa.com/app/codimap/lists?tag_no=" + requestBody.getTag());

        List<CodyResponseBody> responseBodies = new ArrayList<>();

        CodyResponseBody cr = new CodyResponseBody();
        cr.setCody_url("safs");
        cr.setImage_url("qwewqe");
        responseBodies.add(cr);



        // 셀레니움으로 responseBodies에 코디들 넣기

        return responseBodies;
    }

}
