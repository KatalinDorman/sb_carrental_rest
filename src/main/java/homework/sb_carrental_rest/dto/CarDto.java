package homework.sb_carrental_rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Base64;


@Getter
@Setter
public class CarDto {

    private Integer id;
    private String type;
    private boolean available;
    private int price;
    private String image;
    private boolean newCarSaved;

    public CarDto(Integer id, String type, boolean available, int price, byte[] image) {
        this.id = id;
        this.type = type;
        this.available = available;
        this.price = price;
        this.image = Base64.getEncoder().encodeToString(image);
    }

    public CarDto(Integer id, String type, boolean available, int price, byte[] image, boolean newCarSaved) {
        this.id = id;
        this.type = type;
        this.available = available;
        this.price = price;
        this.image = Base64.getEncoder().encodeToString(image);
        this.newCarSaved = newCarSaved;
    }

    public void setImage(byte[] image) {
        this.image = Base64.getEncoder().encodeToString(image);
    }

    public byte[] getImageByteArray() {
        return Base64.getDecoder().decode(this.image);
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", available=" + available +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", newCarSaved=" + newCarSaved +
                '}';
    }
}
