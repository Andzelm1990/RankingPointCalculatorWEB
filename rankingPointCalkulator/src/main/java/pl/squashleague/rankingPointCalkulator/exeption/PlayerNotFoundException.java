package pl.squashleague.rankingPointCalkulator.exeption;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException (int id){
        super("Not found user id: " + id);
    }
}
