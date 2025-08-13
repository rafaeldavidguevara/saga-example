package common.dtos.driver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import common.dtos.cab.dto.CabDto;

@Data
@NoArgsConstructor
public class DriverDto {
    private String driverName;
    private String driverEmail;
    private String driverLocation;
    private CabDto cabDto;
}
