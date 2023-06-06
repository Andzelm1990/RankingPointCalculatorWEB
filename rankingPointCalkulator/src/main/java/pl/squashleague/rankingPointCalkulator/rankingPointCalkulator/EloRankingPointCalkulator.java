package pl.squashleague.rankingPointCalkulator.rankingPointCalkulator;

import org.springframework.stereotype.Service;
import pl.squashleague.rankingPointCalkulator.Dto.PlayerDto;
import pl.squashleague.rankingPointCalkulator.exeption.MatchResultsIsWrongException;

import java.util.ArrayList;

@Service
public class EloRankingPointCalkulator implements RankingPointCalculator {

    /**
     * Calculates the new rating for a player in the Elo ranking.
     *
     * @param player1        player 1 data before update
     * @param player2        player 2 data before update
     * @param matchResults   match result (1- first player win, 2 - two player win)
     * @return updetsPlayers ArrayList<PlayerDto> an array containing updated player data with the new rating
     */
    @Override
    public ArrayList<PlayerDto> recalculateRankingPoint(PlayerDto player1, PlayerDto player2, int matchResults) {

        matchResultsChek(matchResults);

        ArrayList<PlayerDto> updetsPlayers = new ArrayList<>();

        int player1newRankingPoint = (int) Math.round(player1.getRankingPoint()
                + getKFactor(player1.getRankingPoint())
                * (((matchResults == 1)? 1 : 0)
                - calculateExpectedScore(player1, player2)));
        PlayerDto player1update = new PlayerDto(player1.getId(), player1newRankingPoint);

        int player2newRankingPoint = (int) Math.round(player2.getRankingPoint()
                + getKFactor(player2.getRankingPoint())
                * (((matchResults == 2)? 1 : 0)
                - calculateExpectedScore(player2, player1)));
        PlayerDto player2update = new PlayerDto(player2.getId(), player2newRankingPoint);

        updetsPlayers.add(player1update);
        updetsPlayers.add(player2update);

        return updetsPlayers;
    }

    private void matchResultsChek(int matchResults) {
        if (matchResults == 1 || matchResults == 2){
        }else {
            throw new MatchResultsIsWrongException("podany błędny wynik meczu. " +
                    "\n[1]-oznacza, że wygrał gracz nr 1." +
                    "\n[2]-oznacza, że wygrał gracz nr 2.");
        }
    }

    /**
     * Returns the player's k-factor based on their score.
     *
     * @param rankingPoint  player score
     * @return              k-factor for the player
     */
    private double getKFactor(int rankingPoint) {
        if (rankingPoint < 10000) {
            return 32;
        } else if (rankingPoint < 12000) {
            return 24;
        } else {
            return 16;
        }
    }

    /**
     * Calculates a player's expected score based on the score difference.
     *
     * @param player1    player 1 data before update
     * @param player2    player 2 data before update
     * @return           player's expected score
     */
    private double calculateExpectedScore(PlayerDto player1, PlayerDto player2) {
        return 1 / (1 + Math.pow(10, ((double) player2.getRankingPoint()
                - (double) player1.getRankingPoint()) / 4000));
    }

}