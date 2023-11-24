package HaeAppthon.joyroom.packet.responsebody;

import lombok.Data;

@Data
public class WeatherResponseBody {
    private String temperature;
    private String maxTemperature;
    private String minTemperature;
    private String yesterdayTemperature; // 어제 동시간기온
    private String humidity;
    private String windSpeed;
    private String precipitation;  // 강수량
    private String skyState;  // 코드 추후 확인
    private String rainState;  // 0:x, 1:비, 2:비/눈, 3:눈, 4:소나기
}
