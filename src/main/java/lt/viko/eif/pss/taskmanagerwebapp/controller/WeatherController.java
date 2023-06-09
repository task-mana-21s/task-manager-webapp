package lt.viko.eif.pss.taskmanagerwebapp.controller;

import lt.viko.eif.pss.taskmanagerwebapp.weatherService.WeatherApiIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/weather")

public class WeatherController {
    private final WeatherApiIntegration weatherApiIntegration;

    public WeatherController(WeatherApiIntegration weatherApiIntegration) {
        this.weatherApiIntegration = weatherApiIntegration;
    }

    @GetMapping("/vilnius")
    public String getTemperatureVilnius() throws IOException {
        return weatherApiIntegration.getTemperatureForVilnius();
    }
}
