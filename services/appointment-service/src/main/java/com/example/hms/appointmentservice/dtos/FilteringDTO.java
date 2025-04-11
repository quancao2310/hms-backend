package com.example.hms.appointmentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteringDTO {
    private String property;
    private String rule;
    private String value;
}
