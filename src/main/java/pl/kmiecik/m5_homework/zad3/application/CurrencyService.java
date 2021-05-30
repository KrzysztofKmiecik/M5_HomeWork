package pl.kmiecik.m5_homework.zad3.application;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kmiecik.m5_homework.zad3.application.port.CurrencyUseCase;
import pl.kmiecik.m5_homework.zad3.domain.Currency;
import pl.kmiecik.m5_homework.zad3.domain.Rate;

import java.util.List;
import java.util.Random;

@Service
public class CurrencyService implements CurrencyUseCase {

    private RestTemplate restTemplate;
    private Currency[] myCurrency;

    public CurrencyService() {
        this.restTemplate = new RestTemplate();
        myCurrency = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/tables/C", Currency[].class);
    }

    @Override
    public Rate getRandomRate() {
        List<Rate> rates = getAllRates();
        int int_random = getInt_random(rates);
        return rates.get(int_random);
    }

    private int getInt_random(List<Rate> rates) {
        int size = rates.size();
        Random random = new Random();
        int int_random = random.nextInt(size);
        return int_random;
    }

    @Override
    public List<Rate> getAllRates() {
        return myCurrency[0].getRates();
    }

}
