package homework.sb_carrental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AdminDto {

    private List<CarDto> carDtoList;
    private List<ReservationDto> reservationDtoList;

}
