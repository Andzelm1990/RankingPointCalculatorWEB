package pl.squashleague.rankingPointCalkulator.repozytory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.squashleague.rankingPointCalkulator.Dto.PlayerDto;

import java.util.Optional;

@Repository
public class UserDtoRepozytory {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<PlayerDto> getPlayerById(int id) {
        return jdbcTemplate.query(
                "SELECT id, rankingPoint FROM user WHERE id = ?",
                BeanPropertyRowMapper.newInstance(PlayerDto.class),
                id
        ).stream().findFirst();
    }

    public int saveNewRankingPointToDb (PlayerDto player){

        return jdbcTemplate.update("UPDATE user SET rankingPoint = ? WHERE id = ?",
                player.getRankingPoint(),
                player.getId());
    }
}
