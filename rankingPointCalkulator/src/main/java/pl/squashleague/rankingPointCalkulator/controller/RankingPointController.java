package pl.squashleague.rankingPointCalkulator.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.squashleague.rankingPointCalkulator.Dto.PlayerDto;
import pl.squashleague.rankingPointCalkulator.exeption.PlayerNotFoundException;
import pl.squashleague.rankingPointCalkulator.rankingPointCalkulator.RankingPointCalculator;
import pl.squashleague.rankingPointCalkulator.repozytory.UserDtoRepozytory;

import java.util.List;

/**
 * Controller for handling ranking points.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/rankingPoint")
public class RankingPointController {

    @Autowired
    RankingPointCalculator rankingPointCalculator;

    @Autowired
    UserDtoRepozytory userDtoRepozytory;

    /**
     * GET method that returns information about the ranking points of a player with the specified ID.
     *
     * @param id the player ID
     * @return a PlayerDto object containing information about the player's ranking points
     * @throws PlayerNotFoundException if the player with the specified ID is not found
     */
    @GetMapping("/{id}")
    public PlayerDto getRankingPointInfoById(@PathVariable("id") int id) {
        return userDtoRepozytory.getPlayerById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    /**
     * PUT method for updating the ranking points of players.
     *
     * @param idFirstPlayer  the ID of the first player
     * @param idSecondPlayer the ID of the second player
     * @param whoWin         the ID of the player who won (1 - first player, 2 - second player)
     * @return information about the updated ranking points of the players
     * @throws PlayerNotFoundException if either of the players with the specified IDs is not found
     */
    @PutMapping("/{idFirstPlayer}/{idSecondPlayer}/{whoWin}")
    public String recalculateOfRankingPoint (@PathVariable("idFirstPlayer") int idFirstPlayer,
                                             @PathVariable("idSecondPlayer") int idSecondPlayer,
                                             @PathVariable("whoWin") int whoWin) {

        PlayerDto firstPlayer = userDtoRepozytory.getPlayerById(idFirstPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idFirstPlayer));
        PlayerDto secondPlayer = userDtoRepozytory.getPlayerById(idSecondPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idSecondPlayer));

        List<PlayerDto> updatesPlayers = rankingPointCalculator
                .recalculateRankingPoint(firstPlayer, secondPlayer, whoWin);

        updatesPlayers.stream()
                .forEach(player -> userDtoRepozytory.saveNewRankingPointToDb(player));

        return "ranking zaktualizowany \nFirst Player Start: "
                + firstPlayer.getRankingPoint()
                + " End: "
                + updatesPlayers.get(0).getRankingPoint()
                + " \nSecond Player Start: "
                + secondPlayer.getRankingPoint()
                + " End: "
                + updatesPlayers.get(1).getRankingPoint();
    }
    }










