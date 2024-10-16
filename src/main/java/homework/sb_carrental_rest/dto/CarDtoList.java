package homework.sb_carrental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class CarDtoList {

    private List<CarDto> carDtoList;
    private LocalDate startDate;
    private LocalDate endDate;
}
