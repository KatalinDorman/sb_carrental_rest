package homework.sb_carrental_rest.service;

import homework.sb_carrental_rest.db.Database;
import homework.sb_carrental_rest.dto.AdminDto;
import homework.sb_carrental_rest.dto.CarDto;
import homework.sb_carrental_rest.dto.ReservationDto;
import homework.sb_carrental_rest.model.Car;
import homework.sb_carrental_rest.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private Database db;

    public AdminDto getAdminDto() {
        AdminDto adminDto = null;
        List<CarDto> carDtoList = new ArrayList<>();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        List<Car> carList;
        List<Reservation> reservationList;


        carList = db.getAllCars();
        reservationList = db.getAllReservations();

        if (carList.size() > 0) {
            for (Car car : carList) {
                CarDto carDto = convertCarToCarDto(car);
                carDtoList.add(carDto);
            }
            if (reservationList.size() > 0) {

                for (Reservation reservation : reservationList) {
                    CarDto carDto = getCarDtoById(reservation.getCarId());

                    ReservationDto reservationDto = new ReservationDto(reservation.getId(),
                            carDto,
                            reservation.getStartDate(),
                            reservation.getEndDate(),
                            reservation.getName(),
                            reservation.getEmail(),
                            reservation.getAddress(),
                            reservation.getPhone(),
                            reservation.getFullPrice());
                    reservationDtoList.add(reservationDto);
                }
                adminDto = new AdminDto(carDtoList, reservationDtoList);
            } else {
                adminDto = new AdminDto(carDtoList, null);
            }
        }

        return adminDto;
    }

    public CarDto getCarDtoById(int carId) {
        CarDto carDto = null;
        Car car = db.getCarById(carId);

        carDto = convertCarToCarDto(car);

        return carDto;
    }

    private CarDto getCarDto(Integer id, String type, boolean available, int price, byte[] image, boolean isNewCarSaved) {
        CarDto carDto = null;

        carDto = new CarDto(id, type, available, price, image, isNewCarSaved);

        return carDto;
    }


    @SneakyThrows
    public CarDto prepareCarDto(String type, String isAvailable, int price, MultipartFile file) {
        boolean available = false;
        boolean isNewCarSaved = false;
        byte[] image = file.getBytes();

        if (!isAvailable.equals("-")) {
            isNewCarSaved = true;

            available = isAvailable.equals("yes");
        }

        CarDto carDto = getCarDto(0, type, available, price, image, isNewCarSaved);

        if (isNewCarSaved) {
            saveCarDto(carDto);
        }

        return carDto;
    }

    @SneakyThrows
    public CarDto updateCarDto(int carId, String type, String isAvailable, int price, MultipartFile file) {
        boolean available = false;
        boolean isNewCarSaved = false;
        byte[] image = file.getBytes();

        if (!isAvailable.equals("-")) {
            isNewCarSaved = true;

            available = isAvailable.equals("yes");
        }

        CarDto carDto = getCarDtoById(carId);
        carDto.setType(type);
        carDto.setAvailable(available);
        carDto.setPrice(price);
        carDto.setImage(image);
        carDto.setNewCarSaved(isNewCarSaved);

        if (isNewCarSaved) {
            saveCarDto(carDto);
        }

        return carDto;
    }

    private void saveCarDto(CarDto carDto) {
        Car car = new Car(carDto.getId(), carDto.getType(), carDto.isAvailable(), carDto.getPrice(), carDto.getImageByteArray());

        db.saveCar(car);
    }

    private CarDto convertCarToCarDto(Car car) {
        CarDto carDto = null;

        carDto = new CarDto(car.getId(), car.getType(), car.isAvailable(), car.getPrice(), car.getImage());

        return carDto;
    }
}
