package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.staffservice.common.validator.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffFilterDTO {
    @Parameter(description = "Filter by working status")
    @ValidEnum(enumClass = WorkingStatus.class)
    private String status;

    @Parameter(description = "Filter by role")
    @ValidEnum(enumClass = UserRole.class)
    private String role;

    @Parameter(description = "Filter by sex")
    @ValidEnum(enumClass = Sex.class)
    private String sex;

    @Parameter(description = "Filter by department")
    private String department;

    @Parameter(description = "Search term")
    private String search;

    @Parameter(description = "Field to sort by")
    @Builder.Default
    private String sortBy = "fullName";

    @Parameter(description = "Sort direction (asc/desc)")
    @Builder.Default
    private String sortDirection = "asc";

    @Parameter(description = "Page number starting from 1")
    @Builder.Default
    private Integer page = 1;

    @Parameter(description = "Number of items per page")
    @Builder.Default
    private Integer size = 10;
}
