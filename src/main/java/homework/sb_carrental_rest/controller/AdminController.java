package homework.sb_carrental_rest.controller;

import homework.sb_carrental_rest.dto.AdminDto;
import homework.sb_carrental_rest.dto.CarDto;
import homework.sb_carrental_rest.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @GetMapping("/admin")
    public String loadAdminPage(Model model) {

        AdminDto adminDto = adminService.getAdminDto();

        model.addAttribute("adminDto", adminDto);

        return "admin.html";
    }

    @PostMapping("/admin/newcar")
    public String saveNewCar(Model model,
                             @RequestParam(name = "type") String type,
                             @RequestParam(name = "isAvailable") String isAvailable,
                             @RequestParam(name = "price") int price,
                             @RequestParam("file") MultipartFile file) {
        String returnPage = "";

        CarDto carDto = adminService.prepareCarDto(type, isAvailable, price, file);

        if (!carDto.isNewCarSaved()) {
            loadAdminPage(model);
            returnPage = "admin.html";
        } else {
            returnPage = "newcar.html";
        }

        model.addAttribute("carDto", carDto);

        return returnPage;
    }

    @GetMapping("/admin/editcar")
    public String loadEditPage(Model model,
                               @RequestParam(name = "carId") int id) {

        CarDto carDto = adminService.getCarDtoById(id);

        model.addAttribute("carDto", carDto);

        return "editcar.html";
    }

    @PostMapping("/admin/editcar/commit")
    public String saveEditedCar(Model model,
                                @RequestParam(name = "carId") int id,
                                @RequestParam(name = "type") String type,
                                @RequestParam(name = "isAvailable") String isAvailable,
                                @RequestParam(name = "price") int price,
                                @RequestParam("file") MultipartFile file) {
        String returnPage = "";

        CarDto carDto = adminService.updateCarDto(id, type, isAvailable, price, file);

        if (!carDto.isNewCarSaved()) {
            returnPage = "editcar.html";
        } else {
            returnPage = "newcar.html";
        }

        model.addAttribute("carDto", carDto);

        return returnPage;
    }
}
