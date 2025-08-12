package common.dtos.driver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverDto {
    private String driverName;
    private String driverEmail;
    private String driverLocation;
}
