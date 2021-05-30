package pl.kmiecik.m5_homework.zad2.application.port;

import pl.kmiecik.m5_homework.zad2.domain.City;

public interface WeatherUseCase {
    String getWeather(City city);
}
