package pl.kmiecik.m5_homework.zad2.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.kmiecik.m5_homework.zad2.domain.City;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    RestTemplate restTemplate;
    City city;


    public WeatherController() {
        this.restTemplate = new RestTemplate();
        this.city = new City("london");
    }

    @GetMapping()
    public String getWeather(Model model) {
        JsonNode[] latt_longJsonNode = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?query=" + this.city.getName(), JsonNode[].class);
        String latt_long = latt_longJsonNode[0].get("latt_long").asText();
        System.out.println(latt_long);
        String[] coordinates = latt_long.split(",");
        String lat = coordinates[0];
        String lon = coordinates[1];
        String src = "http://www.7timer.info/bin/astro.php?lon=" + lon + "&lat=" + lat + "&ac=0&lang=en&unit=metric&output=internal&tzshift=0";
        model.addAttribute("city", this.city);
        model.addAttribute("src", src);
        return "weatherView";
    }

    @PostMapping
    public String postCity(@ModelAttribute City city) {
        this.city.setName(city.getName());
        return "redirect:/weather";
    }


}
