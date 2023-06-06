package pl.squashleague.rankingPointCalkulator.rankingPointCalkulator;

import pl.squashleague.rankingPointCalkulator.Dto.PlayerDto;

import java.util.ArrayList;

public interface RankingPointCalculator {

    ArrayList<PlayerDto> recalculateRankingPoint (PlayerDto player1, PlayerDto player2, int matchResults);
}
