package pl.squashleague.rankingPointCalkulator.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MatchResultsIsWrongAdvice {

    @ExceptionHandler(MatchResultsIsWrongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String playerNotFoundHandler (MatchResultsIsWrongException exception){
        return exception.getMessage();
    }
}
