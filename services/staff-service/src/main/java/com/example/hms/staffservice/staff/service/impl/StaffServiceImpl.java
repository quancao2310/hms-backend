package com.example.hms.staffservice.staff.service.impl;

import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.auth.exception.DuplicateEmailException;
import com.example.hms.staffservice.auth.exception.DuplicateSsnException;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.staff.dto.*;
import com.example.hms.staffservice.staff.exception.InvalidRequestException;
import com.example.hms.staffservice.staff.exception.StaffNotFoundException;
import com.example.hms.staffservice.staff.factory.StaffFactory;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffMapper;
import com.example.hms.staffservice.staff.service.StaffService;
import com.example.hms.staffservice.staff.specification.StaffSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private static final List<String> ALLOWED_SORT_FIELDS = List.of(
            "fullName", "createdAt", "startWorkingDate", "email", "status"
    );
    private static final List<String> ALLOWED_SORT_DIRECTIONS = List.of("asc", "desc");

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Qualifier("staffFactory")
    private final StaffFactory staffFactory;
    @Qualifier("doctorFactory")
    private final StaffFactory doctorFactory;
    @Qualifier("nurseFactory")
    private final StaffFactory nurseFactory;

    @Override
    @Transactional
    public StaffDTO createStaff(CreateStaffDTO dto) {
        if (staffRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException();
        }
        if (staffRepository.existsBySsn(dto.ssn())) {
            throw new DuplicateSsnException();
        }

        UserRole role = UserRole.valueOf(dto.role());
        Staff createdStaff = getStaffFactory(role)
                .handleCreation(dto);

        return staffMapper.toStaffDTO(createdStaff);
    }

    @Override
    public PageResponse<StaffDTO> getFilteredStaff(StaffFilterDTO filterDTO) {
        Pageable pageable = createPageRequest(
                filterDTO.getSortBy(), filterDTO.getSortDirection(), filterDTO.getPage(), filterDTO.getSize()
        );

        Specification<Staff> specification = Specification
                .where(StaffSpecification.hasStatus(filterDTO.getStatus()))
                .and(StaffSpecification.hasRole(filterDTO.getRole()))
                .and(StaffSpecification.filterSex(filterDTO.getSex()))
                .and(StaffSpecification.hasDepartment(filterDTO.getDepartment()))
                .and(StaffSpecification.matchesSearch(filterDTO.getSearch()))
                .and(StaffSpecification.excludeAdminIfNotAdmin(isCurrentUserAdmin()));

        Page<StaffDTO> dtoPage = staffRepository.findAll(specification, pageable)
                .map(staffMapper::toStaffDTO);
        return PageResponse.from(dtoPage);
    }

    @Override
    public StaffDTO getStaffById(UUID id) {
        return staffRepository.findById(id)
                .filter(staff -> !staff.getRole().equals(UserRole.ADMIN) || isCurrentUserAdmin())
                .map(staffMapper::toStaffDTO)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));
    }

    @Override
    @Transactional
    public StaffDTO updateStaff(UUID id, UpdateStaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));
        
        if (!dto.email().equals(staff.getEmail()) && staffRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException();
        }
        if (!dto.ssn().equals(staff.getSsn()) && staffRepository.existsBySsn(dto.ssn())) {
            throw new DuplicateSsnException();
        }

        Staff updatedStaff = getStaffFactory(staff.getRole())
                .handleUpdate(staff, dto);

        return staffMapper.toStaffDTO(updatedStaff);
    }

    @Override
    @Transactional
    public StaffDTO patchStaff(UUID id, PatchStaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));

        // Handle unique constraints
        if (dto.email().isPresent()) {
            String newEmail = dto.email().get();
            if (!newEmail.equals(staff.getEmail()) && staffRepository.existsByEmail(newEmail)) {
                throw new DuplicateEmailException();
            }
        }
        if (dto.ssn().isPresent()) {
            String newSsn = dto.ssn().get();
            if (!newSsn.equals(staff.getSsn()) && staffRepository.existsBySsn(newSsn)) {
                throw new DuplicateSsnException();
            }
        }

        Staff updatedStaff = getStaffFactory(staff.getRole())
                .handlePartialUpdate(staff, dto);

        return staffMapper.toStaffDTO(updatedStaff);
    }

    @Override
    @Transactional
    public MessageDTO updateStaffStatus(UUID id, UpdateStatusDTO dto) {
        if (!staffRepository.existsById(id)) {
            throw new StaffNotFoundException("Staff not found with id: " + id);
        }

        staffRepository.updateStatus(id, WorkingStatus.valueOf(dto.status()));
        return new MessageDTO("Staff status updated successfully");
    }

    private StaffFactory getStaffFactory(UserRole role) {
        switch (role) {
            case DOCTOR -> {
                return doctorFactory;
            }
            case NURSE -> {
                return nurseFactory;
            }
            default -> {
                return staffFactory;
            }
        }
    }

    private boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities()
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

    private void validateSortParams(String sortBy, String sortDirection) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidRequestException(String.format("Invalid sortBy field: %s. Allowed fields are: %s",
                    sortBy, ALLOWED_SORT_FIELDS));
        }

        if (!ALLOWED_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            throw new InvalidRequestException(String.format("Invalid sortDirection: %s. Allowed values are: %s",
                    sortDirection, ALLOWED_SORT_DIRECTIONS));
        }
    }

    private Pageable createPageRequest(String sortBy, String sortDirection, int page, int size) {
        validateSortParams(sortBy, sortDirection);

        if (page < 1) {
            throw new InvalidRequestException("Page number must be greater than 0");
        }

        if (size < 1 || size > 100) {
            throw new InvalidRequestException("Page size must be between 1 and 100");
        }

        return PageRequest.of(
                page - 1,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection.toUpperCase()), sortBy)
        );
    }
}
