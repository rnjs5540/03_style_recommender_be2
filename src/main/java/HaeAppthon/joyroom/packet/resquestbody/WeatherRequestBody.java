package HaeAppthon.joyroom.packet.resquestbody;

import lombok.Data;

@Data
public class WeatherRequestBody {
    private String date = "20231124";
    private int time = 6;
    private String x = "35";
    private String y = "128";
}
