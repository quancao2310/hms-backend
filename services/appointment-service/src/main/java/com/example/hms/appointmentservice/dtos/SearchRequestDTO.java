package com.example.hms.appointmentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private List<FilteringDTO> filters;
    private List<String> sort;
    private Integer page;
    private Integer size;
}
