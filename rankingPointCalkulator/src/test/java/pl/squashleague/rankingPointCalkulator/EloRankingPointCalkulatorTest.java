package pl.squashleague.rankingPointCalkulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.squashleague.rankingPointCalkulator.Dto.PlayerDto;
import pl.squashleague.rankingPointCalkulator.controller.RankingPointController;
import pl.squashleague.rankingPointCalkulator.exeption.PlayerNotFoundException;
import pl.squashleague.rankingPointCalkulator.rankingPointCalkulator.EloRankingPointCalkulator;
import pl.squashleague.rankingPointCalkulator.rankingPointCalkulator.RankingPointCalculator;
import pl.squashleague.rankingPointCalkulator.repozytory.UserDtoRepozytory;

import java.util.ArrayList;
import java.util.Optional;



public class EloRankingPointCalkulatorTest {

    @Test
    public void testRecalculateOfRankingPoint_PlayerNotFound() {
        int idFirstPlayer = 123;
        int idSecondPlayer = 456;
        int whoWin = 1;

        UserDtoRepozytory userRepository = Mockito.mock(UserDtoRepozytory.class);
        RankingPointCalculator rankingPointCalculator = Mockito.mock(RankingPointCalculator.class);
        RankingPointController yourObject = new RankingPointController(rankingPointCalculator, userRepository);


        Mockito.when(userRepository.getPlayerById(idFirstPlayer)).thenReturn(Optional.empty());


        Assertions.assertThrows(PlayerNotFoundException.class, () -> {
            yourObject.recalculateOfRankingPoint(idFirstPlayer, idSecondPlayer, whoWin);
        });
    }

    @Test
    public void testRecalculateRankingPointReturnSize() {

        PlayerDto player1 = new PlayerDto(1, 1000); // Gracz 1 zaczyna z 1000 punktami
        PlayerDto player2 = new PlayerDto(2, 900); // Gracz 2 zaczyna z 900 punktami
        EloRankingPointCalkulator elo = new EloRankingPointCalkulator();


        ArrayList<PlayerDto> updatedPlayers = elo.recalculateRankingPoint(player1, player2, 1);


        Assertions.assertEquals(2, updatedPlayers.size());
    }

    @Test
    public void testRecalculateRankingPointPlayer1Win() {

        int firstPlayerPoint = 1000;
        int secoundPlayerPoint = 900;
        PlayerDto player1 = new PlayerDto(1, firstPlayerPoint);
        PlayerDto player2 = new PlayerDto(2, secoundPlayerPoint);
        EloRankingPointCalkulator elo = new EloRankingPointCalkulator();


        ArrayList<PlayerDto> updatedPlayers = elo.recalculateRankingPoint(player1, player2, 1);


        PlayerDto updatedPlayer1 = updatedPlayers.get(0);
        Assertions.assertTrue(updatedPlayer1.getRankingPoint() >= firstPlayerPoint);
    }

    @Test
    public void testRecalculateRankingPointPlayer2Win() {

        int firstPlayerPoint = 1000;
        int secoundPlayerPoint = 900;
        PlayerDto player1 = new PlayerDto(1, firstPlayerPoint);
        PlayerDto player2 = new PlayerDto(2, secoundPlayerPoint);
        EloRankingPointCalkulator elo = new EloRankingPointCalkulator();


        ArrayList<PlayerDto> updatedPlayers = elo.recalculateRankingPoint(player1, player2, 2);


        PlayerDto updatedPlayer2 = updatedPlayers.get(1);
        Assertions.assertTrue(updatedPlayer2.getRankingPoint() >= secoundPlayerPoint);
    }



}
