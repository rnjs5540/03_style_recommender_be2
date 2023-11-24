package HaeAppthon.joyroom.controller;

import HaeAppthon.joyroom.packet.responsebody.WeatherResponseBody;
import HaeAppthon.joyroom.packet.resquestbody.WeatherRequestBody;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Controller
public class WeatherController {

    String getBeforeYesterday() {

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

        calendar.add(Calendar.DATE, -2);
        String dayBeforeYesterday = SDF.format(calendar.getTime());

        return dayBeforeYesterday;
    }

    int getTime() {

        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        return hour;
    }

    @GetMapping("/weather")
    public WeatherResponseBody getWeather(@RequestBody WeatherRequestBody requestBody) throws IOException, ParseException {

        WeatherResponseBody responseBody = new WeatherResponseBody();

        int baseTime = getTime();
        String baseDate = getBeforeYesterday();
        String nx = requestBody.getX();
        String ny = requestBody.getY();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=3Yfk%2BzV48B7ULnXptsof0yOnGYvzlZvqdJHuAQsIWYxyhVrJJK%2FwVo4fmz12EDKAVq7KEyGCN0cWG1IyrMxc6w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("580", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("2300", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String sb = "";
        String line;
        while ((line = rd.readLine()) != null) {
            sb += line;
        }
        rd.close();
        conn.disconnect();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        int itemIdx = 0;
        for(int fcstTime = 0; fcstTime < 24; fcstTime++) {

            JSONObject data = (JSONObject) item.get(itemIdx);

            if(data.get("category").toString().equals("TMN")){
                itemIdx++;
            } else if (data.get("category").toString().equals("TMX")) {
                itemIdx++;
            }

            if(fcstTime == baseTime) {
                data = (JSONObject) item.get(itemIdx); //TMP
                responseBody.setYesterdayTemperature(data.get("fcstValue").toString());
                itemIdx = 290;
                break;
            }
            else itemIdx += 12;
        }

        for(int fcstTime = 0; fcstTime < 24; fcstTime++) {

            JSONObject data = (JSONObject) item.get(itemIdx);

            if(data.get("category").toString().equals("TMN")){
                responseBody.setMinTemperature(data.get("fcstValue").toString());
                itemIdx++;
            } else if (data.get("category").toString().equals("TMX")) {
                responseBody.setMaxTemperature(data.get("fcstValue").toString());
                itemIdx++;
            }

            if(fcstTime == baseTime) {
                data = (JSONObject) item.get(itemIdx); //TMP
                responseBody.setTemperature(data.get("fcstValue").toString());
                data = (JSONObject) item.get(itemIdx+4); //WSD
                responseBody.setWindSpeed(data.get("fcstValue").toString());
                data = (JSONObject) item.get(itemIdx+5); //SKY
                responseBody.setSkyState(data.get("fcstValue").toString());
                data = (JSONObject) item.get(itemIdx+6); //PTY
                responseBody.setRainState(data.get("fcstValue").toString());
                data = (JSONObject) item.get(itemIdx+9); //PCP
                responseBody.setPrecipitation(data.get("fcstValue").toString());
                data = (JSONObject) item.get(itemIdx+10); //REH
                responseBody.setHumidity(data.get("fcstValue").toString());
            }
            itemIdx += 12;
        }

        return responseBody;
    }
}
