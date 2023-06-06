package pl.squashleague.rankingPointCalkulator.Dto;


import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    @Id
    private int id;
    private int rankingPoint;

}
