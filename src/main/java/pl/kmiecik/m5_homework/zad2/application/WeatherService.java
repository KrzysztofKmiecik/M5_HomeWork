package pl.kmiecik.m5_homework.zad2.application;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kmiecik.m5_homework.zad2.NoCityInfoException;
import pl.kmiecik.m5_homework.zad2.domain.City;

import java.util.Optional;
import java.util.stream.Stream;

@Service
class WeatherService implements pl.kmiecik.m5_homework.zad2.application.port.WeatherUseCase {
    private RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();

    }

    @Override
    public String getWeather(final City city) {
        String latt_long = getCityCoordinates(city);
        String[] coordinates = latt_long.split(",");
        String lat = coordinates[0];
        String lon = coordinates[1];
        String src = "http://www.7timer.info/bin/astro.php?lon=" + lon + "&lat=" + lat + "&ac=0&lang=en&unit=metric&output=internal&tzshift=0";
        return src;
    }

    private String getCityCoordinates(City city) {
        Optional<JsonNode> cityInfo = Stream.of(restTemplate.getForObject("https://www.metaweather.com/api/location/search/?query=" + city.getName(), JsonNode[].class)).findFirst();
        if (cityInfo.isPresent()) {
            String latt_long = cityInfo.get().get("latt_long").asText();
            return latt_long;
        }else{
            throw new NoCityInfoException("There is no such a city");
        }
    }
}
