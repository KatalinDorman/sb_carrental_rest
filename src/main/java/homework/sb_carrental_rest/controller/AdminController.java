package homework.sb_carrental_rest.controller;

import homework.sb_carrental_rest.dto.AdminDto;
import homework.sb_carrental_rest.dto.CarDto;
import homework.sb_carrental_rest.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminController {

    private AdminService adminService;

    @GetMapping("/admin")
    public AdminDto loadAdminPage() {

        return adminService.getAdminDto();
    }

    @PostMapping("/admin/newcar")
    public CarDto saveNewCar(@RequestParam(name = "type") String type,
                             @RequestParam(name = "isAvailable") String isAvailable,
                             @RequestParam(name = "price") int price,
                             @RequestParam("file") MultipartFile file) {

        return adminService.prepareCarDto(type, isAvailable, price, file);
    }

    @GetMapping("/admin/editcar")
    public CarDto loadEditPage(@RequestParam(name = "carId") int id) {

        return adminService.getCarDtoById(id);
    }

    @PostMapping("/admin/editcar/commit")
    public CarDto saveEditedCar(@RequestParam(name = "carId") int id,
                                @RequestParam(name = "type") String type,
                                @RequestParam(name = "isAvailable") String isAvailable,
                                @RequestParam(name = "price") int price,
                                @RequestParam("file") MultipartFile file) {

        return adminService.updateCarDto(id, type, isAvailable, price, file);
    }
}
