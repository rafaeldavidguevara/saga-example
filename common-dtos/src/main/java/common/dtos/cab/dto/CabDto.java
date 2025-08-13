package common.dtos.cab.dto;

import common.dtos.cab.enums.CabTypes;
import lombok.Data;

@Data
public class CabDto {
    private CabTypes cabType;
    private String registrationNumber;
}
