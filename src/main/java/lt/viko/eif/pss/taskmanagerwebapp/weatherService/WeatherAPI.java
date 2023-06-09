package lt.viko.eif.pss.taskmanagerwebapp.weatherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller class for retrieving weather information.
 */
@RestController
@RequestMapping("/weather")

public class WeatherAPI {
    private final WeatherApiIntegration weatherApiIntegration;

    /**
     * Constructs a new WeatherController with the given WeatherApiIntegration.
     *
     * @param weatherApiIntegration the weather API integration service
     */
    public WeatherAPI(WeatherApiIntegration weatherApiIntegration) {
        this.weatherApiIntegration = weatherApiIntegration;
    }

    /**
     * Retrieves the temperature for Vilnius.
     *
     * @return the temperature in Vilnius
     * @throws IOException if an I/O error occurs during the temperature retrieval
     */
    @GetMapping("/vilnius")
    public String getTemperatureVilnius() throws IOException {
        return weatherApiIntegration.getTemperatureForVilnius();
    }
}
