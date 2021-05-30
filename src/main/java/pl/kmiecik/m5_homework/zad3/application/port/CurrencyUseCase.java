package pl.kmiecik.m5_homework.zad3.application.port;

import pl.kmiecik.m5_homework.zad3.domain.Rate;

import java.util.List;

public interface CurrencyUseCase {
    Rate getRandomRate();
    List<Rate> getAllRates();
}
