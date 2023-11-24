package HaeAppthon.joyroom.controller;

import HaeAppthon.joyroom.packet.responsebody.WeatherResponseBody;
import HaeAppthon.joyroom.packet.resquestbody.WeatherRequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WeatherController {
    @PostMapping("/weather")
    public WeatherResponseBody crawling_codies(@RequestBody WeatherRequestBody requestBody){

        WeatherResponseBody responseBody = new WeatherResponseBody();


        return responseBody;
    }
}
