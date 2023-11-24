package HaeAppthon.joyroom.packet.responsebody;

import lombok.Data;

@Data
public class WeatherResponseBody {
    private int temperature;
    private int maxTemperature;
    private int minTemperature;
    private int yesterdayTemperature; // 어제 동시간기온
    private int humidity;
    private int windSpeed;
    private int precipitation;  // 강수량
    private int skyState;  // 코드 추후 확인
    private int rainState;  // 0:x, 1:비, 2:비/눈, 3:눈, 4:소나기
}
