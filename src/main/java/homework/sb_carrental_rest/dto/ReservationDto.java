package homework.sb_carrental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ReservationDto {

    private Integer id;
    private CarDto carDto;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Integer fullPrice;
}
