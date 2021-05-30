package pl.kmiecik.m5_homework.zad3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kmiecik.m5_homework.zad3.application.port.CurrencyUseCase;
import pl.kmiecik.m5_homework.zad3.domain.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/currency")
public class CurrencyController {

    private Rate rate;
    private String message="";
    private int counter;
    private BigDecimal hostValue;

    private final CurrencyUseCase currencyService;

    @Autowired
    public CurrencyController(CurrencyUseCase currencyService) {
        this.currencyService = currencyService;
        rate = currencyService.getRandomRate();
        hostValue=BigDecimal.valueOf(this.rate.getAsk()).setScale(2,RoundingMode.HALF_UP);
    }


    @GetMapping
    public String getCurrency(Model model){
        model.addAttribute("rate",rate);
        model.addAttribute("hint", this.hostValue);
        model.addAttribute("newRate", new Rate());
        model.addAttribute("message",message);
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
