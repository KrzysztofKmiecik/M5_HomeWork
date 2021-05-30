package pl.kmiecik.m5_homework.zad2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kmiecik.m5_homework.zad2.web.WeatherController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
class CustomBlobalExceptionHandler {

    private final WeatherController weatherController;

    @Autowired
    public CustomBlobalExceptionHandler(final WeatherController weatherController) {
        this.weatherController = weatherController;
        setDefaults();
    }

    @ExceptionHandler(NoCityInfoException.class)
    public ResponseEntity<Object> RestHandleException(final NoCityInfoException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("info", "No such a Capitol City - refrash to set Warsaw");
        setDefaults();
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> RestHandleException(final BindException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " - " + x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        setDefaults();
        return new ResponseEntity<>(body, status);
    }

    private void setDefaults() {
        weatherController.setCity("Warsaw");
    }

}
