package homework.sb_carrental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@AllArgsConstructor
@Getter
@Setter
public class CarDto {

    private Integer id;
    private String type;
    private boolean available;
    private int price;
    private byte[] image;
    private boolean newCarSaved;

    public CarDto(Integer id, String type, boolean available, int price, byte[] image) {
        this.id = id;
        this.type = type;
        this.available = available;
        this.price = price;
        this.image = image;
    }

    public String getImageBase64() {

        String base64String = Base64.getEncoder().encodeToString(image);

        return base64String;
    }
}
