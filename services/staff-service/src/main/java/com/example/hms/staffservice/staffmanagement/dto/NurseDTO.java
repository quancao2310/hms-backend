package com.example.hms.staffservice.staffmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NurseDTO extends StaffAccountDTO {
    private String licenseNumber;
    private String qualification;
}
