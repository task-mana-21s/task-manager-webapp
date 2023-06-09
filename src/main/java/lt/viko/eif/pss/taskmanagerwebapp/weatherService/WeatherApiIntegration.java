package lt.viko.eif.pss.taskmanagerwebapp.weatherService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeatherApiIntegration {
    private static final String API_KEY = "43c292ab3a8f8cf0b5aaa92ae9d1d4dc";

    public String getTemperatureForVilnius() throws IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=54.6872&lon=25.2797&appid=" + API_KEY;

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        String responseBody = EntityUtils.toString(response.getEntity());

        return responseBody;

    }
}
