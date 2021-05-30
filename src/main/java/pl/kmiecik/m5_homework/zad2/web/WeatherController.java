package pl.kmiecik.m5_homework.zad2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kmiecik.m5_homework.zad2.application.port.WeatherUseCase;
import pl.kmiecik.m5_homework.zad2.domain.City;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherUseCase weatherService;
    private City city;

    @Autowired
    public WeatherController(WeatherUseCase weatherService) {
        this.weatherService = weatherService;
        city = new City("Warsaw");
    }

    @GetMapping()
    public String getWeather(Model model) {
        String src = weatherService.getWeather(this.city);
        model.addAttribute("city", this.city);
        model.addAttribute("src", src);
        return "weatherView";
    }

    @PostMapping
    public String postCity(@ModelAttribute @Valid CityCommand command) {
        this.city.setName(command.toCity().getName());
        return "redirect:/weather";
    }


    public void setCity(final String city) {
        this.city.setName(city);
    }

    private static class CityCommand {
        @NotBlank
        private String name;


        public CityCommand(String name) {
            this.name = name;
        }

        public CityCommand() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public City toCity() {
            return new City(name);

        }
    }
}
