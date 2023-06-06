package pl.squashleague.rankingPointCalkulator.exeption;

public class MatchResultsIsWrongException extends RuntimeException{

    public MatchResultsIsWrongException (String tekst){
        super(tekst);
    }
}
