package homework.sb_carrental_rest.service;

import homework.sb_carrental_rest.db.Database;
import homework.sb_carrental_rest.dto.CarDto;
import homework.sb_carrental_rest.dto.CarDtoList;
import homework.sb_carrental_rest.dto.ReservationDto;
import homework.sb_carrental_rest.model.Car;
import homework.sb_carrental_rest.model.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarRentalService {

    private Database db;

    public CarDtoList getAvailableCarsForChosenPeriod(LocalDate startDate, LocalDate endDate) {
        CarDtoList carDtoList = null;
        List<CarDto> availableCarDtoList = new ArrayList<>();

        List<Car> availableCarList = db.getAvailableCarList(startDate, endDate);

        for (Car car : availableCarList) {
            availableCarDtoList.add(convertCarToCarDto(car));
        }

        carDtoList = new CarDtoList(availableCarDtoList, startDate, endDate);
        System.out.println("**********" + carDtoList.toString());

        return carDtoList;
    }

    private CarDto getCarDtoById(int carId) {
        CarDto carDto = null;
        Car car = db.getCarById(carId);

        carDto = convertCarToCarDto(car);

        return carDto;
    }


    public ReservationDto getReservationDto(int carId, LocalDate startDate, LocalDate endDate,
                                            String name, String email, String address, String phone) {
        CarDto carDto = null;

        carDto = getCarDtoById(carId);
        int fullPrice = calculateFullPrice(startDate, endDate, carDto.getPrice());

        ReservationDto reservationDto = null;

        reservationDto = new ReservationDto(0, carDto, startDate, endDate, name, email, address, phone, fullPrice);

        saveReservationDto(reservationDto);

        return reservationDto;
    }

    private void saveReservationDto(ReservationDto reservationDto) {

        Reservation reservation = new Reservation(reservationDto.getId(),
                reservationDto.getCarDto().getId(),
                reservationDto.getName(),
                reservationDto.getEmail(),
                reservationDto.getAddress(),
                reservationDto.getPhone(),
                reservationDto.getStartDate(),
                reservationDto.getEndDate(),
                reservationDto.getFullPrice());
        db.saveNewReservation(reservation);
    }

    private int calculateFullPrice(LocalDate startDate, LocalDate endDate, int price) {
        int fullPrice = 0;
        int daysBetweenStartAndEndDate = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

        fullPrice = daysBetweenStartAndEndDate * price;

        return fullPrice;
    }

    public ReservationDto prepareReservationDto(LocalDate startDate, LocalDate endDate, int carId) {
        CarDto carDto = null;

        carDto = getCarDtoById(carId);

        ReservationDto reservationDto = new ReservationDto(null, carDto, startDate, endDate, null, null,
                null, null, null);

        return reservationDto;
    }

    private CarDto convertCarToCarDto(Car car) {
        CarDto carDto = null;

        carDto = new CarDto(car.getId(), car.getType(), car.isAvailable(), car.getPrice(), car.getImage());

        return carDto;
    }
}
