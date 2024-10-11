package homework.sb_carrental_rest.controller;

import homework.sb_carrental_rest.dto.CarDtoList;
import homework.sb_carrental_rest.dto.ReservationDto;
import homework.sb_carrental_rest.service.CarRentalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CarRentalController {

    private CarRentalService carRentalService;

    @GetMapping("/cars")
    public CarDtoList loadAvailableCars(@RequestParam(name = "startDate") LocalDate startDate,
                                        @RequestParam(name = "endDate") LocalDate endDate) {

        return carRentalService.getAvailableCarsForChosenPeriod(startDate, endDate);
    }

    @GetMapping("/cars/startres")
    public ReservationDto startReservation(@RequestParam(name = "startDate") LocalDate startDate,
                                           @RequestParam(name = "endDate") LocalDate endDate,
                                           @RequestParam(name = "carId") int carId) {

        return carRentalService.prepareReservationDto(startDate, endDate, carId);
    }

    @PostMapping("/cars/endres")
    public ReservationDto endReservation(@RequestParam(name = "carId") int carId,
                                         @RequestParam(name = "startDate") LocalDate startDate,
                                         @RequestParam(name = "endDate") LocalDate endDate,
                                         @RequestParam(name = "name") String name,
                                         @RequestParam(name = "email") String email,
                                         @RequestParam(name = "address") String address,
                                         @RequestParam(name = "phone") String phone) {

        return carRentalService.getReservationDto(carId, startDate, endDate, name, email, address, phone);
    }
}
