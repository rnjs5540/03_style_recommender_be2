package HaeAppthon.joyroom;

import HaeAppthon.joyroom.packet.responsebody.WeatherResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootTest
class JoyroomApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testWeather() throws IOException, ParseException {
	}

}
