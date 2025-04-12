package com.example.hms.staffservice.staff.factory;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.common.service.EmailService;
import com.example.hms.staffservice.staff.dto.CreateStaffDTO;
import com.example.hms.staffservice.staff.dto.PatchStaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStaffDTO;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("staffFactory")
public class StaffFactory {

    private static final int PASSWORD_LENGTH = 12;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private final Random random = new Random();

    protected final PasswordEncoder passwordEncoder;
    protected final StaffRepository staffRepository;
    protected final EmailService emailService;

    public StaffFactory(PasswordEncoder passwordEncoder, StaffRepository staffRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.staffRepository = staffRepository;
        this.emailService = emailService;
    }

    /**
     * Template method to handle the creation of a staff member.
     *
     * @param dto The staff creation DTO containing the necessary information.
     * @return The created staff member.
     */
    public Staff handleCreation(CreateStaffDTO dto) {
        String generatedPassword = generateRandomPassword();
        Staff staff = createStaff(dto, generatedPassword);
        Staff createdStaff = saveStaff(staff);
        sendPasswordToEmail(createdStaff.getEmail(), createdStaff.getFullName(), generatedPassword);
        return createdStaff;
    }

    /**
     * Template method to handle the update of a staff member.
     *
     * @param staff The existing staff member to be updated.
     * @param dto   The staff update DTO containing the new information.
     * @return The updated staff member.
     */
    public Staff handleUpdate(Staff staff, UpdateStaffDTO dto) {
        return saveStaff(updateStaff(staff, dto));
    }

    /**
     * Template method to handle the partial update of a staff member.
     *
     * @param staff The existing staff member to be partially updated.
     * @param dto   The staff patch DTO containing the new information.
     * @return The partially updated staff member.
     */
    public Staff handlePartialUpdate(Staff staff, PatchStaffDTO dto) {
        if (dto.isEmpty()) {
            return staff;
        }
        return saveStaff(partialUpdateStaff(staff, dto));
    }

    protected String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    protected Staff createStaff(CreateStaffDTO dto, String generatedPassword) {
        return Staff.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(passwordEncoder.encode(generatedPassword))
                .ssn(dto.ssn())
                .dateOfBirth(dto.dateOfBirth())
                .sex(Sex.valueOf(dto.sex()))
                .phoneNumber(dto.phoneNumber())
                .nationality(dto.nationality())
                .address(dto.address())
                .biography(dto.biography())
                .role(UserRole.valueOf(dto.role()))
                .startWorkingDate(dto.startWorkingDate())
                .status(WorkingStatus.valueOf(dto.status()))
                .build();
    }

    protected Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    protected void sendPasswordToEmail(String email, String fullName, String password) {
        emailService.sendPasswordEmail(email, fullName, password);
    }

    protected Staff updateStaff(Staff staff, UpdateStaffDTO dto) {
        staff.setFullName(dto.fullName());
        staff.setEmail(dto.email());
        staff.setSsn(dto.ssn());
        staff.setDateOfBirth(dto.dateOfBirth());
        staff.setSex(Sex.valueOf(dto.sex()));
        staff.setPhoneNumber(dto.phoneNumber());
        staff.setNationality(dto.nationality());
        staff.setAddress(dto.address());
        staff.setBiography(dto.biography());
        staff.setStartWorkingDate(dto.startWorkingDate());
        staff.setStatus(WorkingStatus.valueOf(dto.status()));
        return staff;
    }

    protected Staff partialUpdateStaff(Staff staff, PatchStaffDTO dto) {
        dto.fullName().ifPresent(staff::setFullName);
        dto.email().ifPresent(staff::setEmail);
        dto.ssn().ifPresent(staff::setSsn);
        dto.dateOfBirth().ifPresent(staff::setDateOfBirth);
        dto.sex().ifPresent(sex -> staff.setSex(Sex.valueOf(sex)));
        dto.phoneNumber().ifPresent(staff::setPhoneNumber);
        dto.nationality().ifPresent(staff::setNationality);
        dto.address().ifPresent(staff::setAddress);
        dto.biography().ifPresent(staff::setBiography);
        dto.startWorkingDate().ifPresent(staff::setStartWorkingDate);
        dto.status().ifPresent(status -> staff.setStatus(WorkingStatus.valueOf(status)));
        return staff;
    }
}
