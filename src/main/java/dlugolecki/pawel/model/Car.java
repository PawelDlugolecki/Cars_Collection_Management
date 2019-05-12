package dlugolecki.pawel.model;

import dlugolecki.pawel.model.enums.Colour;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Car {
    private String model;
    private BigDecimal price;
    private int mileage;
    private Colour colour;
    private List<String> equipment;

    @Override
    public String toString() {
        return model + " " +
                price + " " +
                colour + " " +
                mileage + " " +
                equipment;
    }
}
