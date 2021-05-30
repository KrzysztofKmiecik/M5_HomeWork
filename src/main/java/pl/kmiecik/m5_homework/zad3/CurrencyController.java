package pl.kmiecik.m5_homework.zad3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.kmiecik.m5_homework.zad3.domain.Currency;
import pl.kmiecik.m5_homework.zad3.domain.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/currency")
public class CurrencyController {
    RestTemplate restTemplate;
    Rate rate;
    String message="";
    private int counter;
    BigDecimal hostValue;

    public CurrencyController() {
        this.restTemplate = new RestTemplate();

        Currency[] myCurrency = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/tables/C", Currency[].class);

        System.out.println("RATES");
        List<Rate> rates =  myCurrency[0].getRates();
        rates.forEach(System.out::println);
        int size = rates.size();
        System.out.println(size);
        Random random=new Random();
        int int_random=random.nextInt(size);
        rate = rates.get(int_random);
        hostValue=BigDecimal.valueOf(this.rate.getAsk()).setScale(2,RoundingMode.HALF_UP);
    }

    @GetMapping
    public String getCurrency(Model model){
        model.addAttribute("rate",rate);
        model.addAttribute("hint", this.hostValue);
        model.addAttribute("newRate", new Rate());

        model.addAttribute("message",message);
        System.out.println("hello");
        return "currencyView";
    }

    @PostMapping
    public String postCurrencyValue(@ModelAttribute Rate rate){
        BigDecimal inputValue=BigDecimal.valueOf(rate.getAsk()).setScale(2, RoundingMode.HALF_UP);

        if(inputValue.compareTo(hostValue)>0){
            message="Za dużo";
        }else if(inputValue.compareTo(hostValue)<0){
            message="Za mało";
        }else{
            message="Gratki! Udało się za "+this.counter+ " razem";
        }

        this.counter++;
        return "redirect:/currency";
    }

}
