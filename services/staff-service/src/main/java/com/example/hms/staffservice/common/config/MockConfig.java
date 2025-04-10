package com.example.hms.staffservice.common.config;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.models.internal.staff.Nurse;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.staff.repository.DoctorRepository;
import com.example.hms.staffservice.staff.repository.NurseRepository;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class MockConfig {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail = "";

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword = "";

    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            // Create admin if not exists
            if (!adminEmail.isEmpty() && !adminPassword.isEmpty() && !staffRepository.existsByEmail(adminEmail)) {
                createAdmin();
            }

            // Create mock data only if the database is empty (no doctors and nurses)
            if (doctorRepository.count() == 0) {
                createMockDoctors();
            }

            if (nurseRepository.count() == 0) {
                createMockNurses();
            }
        };
    }

    private void createAdmin() {
        Staff admin = Admin.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(UserRole.ADMIN)
                .fullName("System Administrator")
                .ssn("000-00-0000")
                .status(WorkingStatus.ACTIVE)
                .build();
        staffRepository.save(admin);
    }

    private void createMockDoctors() {
        String password = "P@ssword1";
        // Specializations
        Set<String> cardiologySpecs = new HashSet<>();
        cardiologySpecs.add("Cardiology");
        cardiologySpecs.add("Heart Surgery");

        Set<String> neurologySpecs = new HashSet<>();
        neurologySpecs.add("Neurology");
        neurologySpecs.add("Brain Surgery");

        Set<String> orthopedicSpecs = new HashSet<>();
        orthopedicSpecs.add("Orthopedics");
        orthopedicSpecs.add("Sports Medicine");

        Set<String> dermatologySpecs = new HashSet<>();
        dermatologySpecs.add("Dermatology");
        dermatologySpecs.add("Cosmetic Dermatology");

        Set<String> pediatricsSpecs = new HashSet<>();
        pediatricsSpecs.add("Pediatrics");
        pediatricsSpecs.add("Neonatal Care");

        // Services
        Set<String> cardiologyServices = new HashSet<>();
        cardiologyServices.add("EKG");
        cardiologyServices.add("Heart Monitoring");

        Set<String> neurologyServices = new HashSet<>();
        neurologyServices.add("Brain Scan");
        neurologyServices.add("Neurological Assessment");

        Set<String> orthopedicServices = new HashSet<>();
        orthopedicServices.add("Physical Therapy");
        orthopedicServices.add("Joint Replacement");

        Set<String> dermatologyServices = new HashSet<>();
        dermatologyServices.add("Skin Biopsy");
        dermatologyServices.add("Acne Treatment");

        Set<String> pediatricsServices = new HashSet<>();
        pediatricsServices.add("Child Health Checkup");
        pediatricsServices.add("Vaccination");

        // Create 10 doctors
        Doctor[] doctors = {
                Doctor.builder()
                        .fullName("Dr. John Smith")
                        .email("john.smith@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("123-45-6789")
                        .dateOfBirth(LocalDate.of(1975, 3, 15))
                        .sex(Sex.MALE)
                        .phoneNumber("555-123-4567")
                        .nationality("American")
                        .address("123 Main St, Boston, MA 02108")
                        .biography("Dr. Smith is a renowned cardiologist with over 20 years of experience.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2005, 5, 10))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD123456")
                        .qualification("MD, Harvard Medical School")
                        .department("Cardiology")
                        .specializations(cardiologySpecs)
                        .services(cardiologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Sarah Johnson")
                        .email("sarah.johnson@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("234-56-7890")
                        .dateOfBirth(LocalDate.of(1980, 7, 22))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-234-5678")
                        .nationality("American")
                        .address("456 Park Ave, Boston, MA 02115")
                        .biography("Dr. Johnson specializes in neurological disorders and brain surgery.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2010, 3, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD234567")
                        .qualification("MD, Johns Hopkins University")
                        .department("Neurology")
                        .specializations(neurologySpecs)
                        .services(neurologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Michael Chen")
                        .email("michael.chen@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("345-67-8901")
                        .dateOfBirth(LocalDate.of(1978, 9, 10))
                        .sex(Sex.MALE)
                        .phoneNumber("555-345-6789")
                        .nationality("American")
                        .address("789 Oak Dr, Cambridge, MA 02139")
                        .biography("Dr. Chen is an orthopedic surgeon specializing in sports medicine.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2008, 6, 20))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD345678")
                        .qualification("MD, Stanford University")
                        .department("Orthopedics")
                        .specializations(orthopedicSpecs)
                        .services(orthopedicServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Emily Davis")
                        .email("emily.davis@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("456-78-9012")
                        .dateOfBirth(LocalDate.of(1982, 4, 5))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-456-7890")
                        .nationality("American")
                        .address("101 Pine St, Boston, MA 02110")
                        .biography("Dr. Davis is a dermatologist with expertise in cosmetic procedures.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2012, 8, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD456789")
                        .qualification("MD, Yale University")
                        .department("Dermatology")
                        .specializations(dermatologySpecs)
                        .services(dermatologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. David Wilson")
                        .email("david.wilson@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("567-89-0123")
                        .dateOfBirth(LocalDate.of(1970, 11, 20))
                        .sex(Sex.MALE)
                        .phoneNumber("555-567-8901")
                        .nationality("American")
                        .address("222 Maple Ave, Brookline, MA 02445")
                        .biography("Dr. Wilson is a pediatrician with a special focus on neonatal care.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2000, 7, 10))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD567890")
                        .qualification("MD, Boston University")
                        .department("Pediatrics")
                        .specializations(pediatricsSpecs)
                        .services(pediatricsServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Jennifer Miller")
                        .email("jennifer.miller@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("678-90-1234")
                        .dateOfBirth(LocalDate.of(1985, 2, 14))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-678-9012")
                        .nationality("American")
                        .address("333 Elm St, Somerville, MA 02143")
                        .biography("Dr. Miller specializes in cardio-thoracic surgery and heart transplants.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2015, 4, 5))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD678901")
                        .qualification("MD, University of Pennsylvania")
                        .department("Cardiology")
                        .specializations(cardiologySpecs)
                        .services(cardiologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Robert Thompson")
                        .email("robert.thompson@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("789-01-2345")
                        .dateOfBirth(LocalDate.of(1976, 8, 30))
                        .sex(Sex.MALE)
                        .phoneNumber("555-789-0123")
                        .nationality("American")
                        .address("444 Cedar Ln, Newton, MA 02458")
                        .biography("Dr. Thompson is a neurosurgeon specializing in brain tumors.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2007, 9, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD789012")
                        .qualification("MD, Columbia University")
                        .department("Neurology")
                        .specializations(neurologySpecs)
                        .services(neurologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Linda Martinez")
                        .email("linda.martinez@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("890-12-3456")
                        .dateOfBirth(LocalDate.of(1983, 5, 12))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-890-1234")
                        .nationality("American")
                        .address("555 Birch Dr, Cambridge, MA 02138")
                        .biography("Dr. Martinez is an orthopedic surgeon focusing on joint replacements.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2013, 10, 20))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD890123")
                        .qualification("MD, Duke University")
                        .department("Orthopedics")
                        .specializations(orthopedicSpecs)
                        .services(orthopedicServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. James Anderson")
                        .email("james.anderson@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("901-23-4567")
                        .dateOfBirth(LocalDate.of(1974, 1, 8))
                        .sex(Sex.MALE)
                        .phoneNumber("555-901-2345")
                        .nationality("American")
                        .address("666 Walnut St, Boston, MA 02116")
                        .biography("Dr. Anderson is a dermatologist specializing in skin cancer treatment.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2004, 11, 10))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD901234")
                        .qualification("MD, University of Chicago")
                        .department("Dermatology")
                        .specializations(dermatologySpecs)
                        .services(dermatologyServices)
                        .createdAt(Instant.now())
                        .build(),

                Doctor.builder()
                        .fullName("Dr. Patricia Brown")
                        .email("patricia.brown@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("012-34-5678")
                        .dateOfBirth(LocalDate.of(1979, 6, 25))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-012-3456")
                        .nationality("American")
                        .address("777 Cherry Ln, Brookline, MA 02446")
                        .biography("Dr. Brown is a pediatrician with a focus on childhood development.")
                        .role(UserRole.DOCTOR)
                        .startWorkingDate(LocalDate.of(2009, 2, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("MD012345")
                        .qualification("MD, Northwestern University")
                        .department("Pediatrics")
                        .specializations(pediatricsSpecs)
                        .services(pediatricsServices)
                        .createdAt(Instant.now())
                        .build()
        };

        doctorRepository.saveAll(Arrays.asList(doctors));
    }

    private void createMockNurses() {
        String password = "P@ssword1";
        // Create 5 nurses
        Nurse[] nurses = {
                Nurse.builder()
                        .fullName("Nurse Emma Roberts")
                        .email("emma.roberts@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("123-45-6780")
                        .dateOfBirth(LocalDate.of(1985, 4, 12))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-111-2222")
                        .nationality("American")
                        .address("111 Nurse Ave, Boston, MA 02108")
                        .biography("Emma is a registered nurse with experience in emergency care.")
                        .role(UserRole.NURSE)
                        .startWorkingDate(LocalDate.of(2010, 6, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("RN123456")
                        .qualification("BSN, Boston College")
                        .department("Emergency")
                        .createdAt(Instant.now())
                        .build(),

                Nurse.builder()
                        .fullName("Nurse William Turner")
                        .email("william.turner@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("234-56-7891")
                        .dateOfBirth(LocalDate.of(1988, 8, 23))
                        .sex(Sex.MALE)
                        .phoneNumber("555-222-3333")
                        .nationality("American")
                        .address("222 Nurse Blvd, Boston, MA 02115")
                        .biography("William is a registered nurse specializing in surgical assistance.")
                        .role(UserRole.NURSE)
                        .startWorkingDate(LocalDate.of(2012, 9, 10))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("RN234567")
                        .qualification("BSN, University of Massachusetts")
                        .department("Surgery")
                        .createdAt(Instant.now())
                        .build(),

                Nurse.builder()
                        .fullName("Nurse Sophia Clark")
                        .email("sophia.clark@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("345-67-8902")
                        .dateOfBirth(LocalDate.of(1990, 3, 5))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-333-4444")
                        .nationality("American")
                        .address("333 Nurse St, Cambridge, MA 02139")
                        .biography("Sophia is a registered nurse with expertise in pediatric care.")
                        .role(UserRole.NURSE)
                        .startWorkingDate(LocalDate.of(2014, 5, 20))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("RN345678")
                        .qualification("BSN, Simmons University")
                        .department("Pediatrics")
                        .createdAt(Instant.now())
                        .build(),

                Nurse.builder()
                        .fullName("Nurse Daniel Harris")
                        .email("daniel.harris@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("456-78-9013")
                        .dateOfBirth(LocalDate.of(1986, 7, 18))
                        .sex(Sex.MALE)
                        .phoneNumber("555-444-5555")
                        .nationality("American")
                        .address("444 Nurse Dr, Boston, MA 02110")
                        .biography("Daniel is a nurse practitioner with a focus on cardiac care.")
                        .role(UserRole.NURSE)
                        .startWorkingDate(LocalDate.of(2011, 8, 15))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("NP456789")
                        .qualification("MSN, Boston University")
                        .department("Cardiology")
                        .createdAt(Instant.now())
                        .build(),

                Nurse.builder()
                        .fullName("Nurse Olivia Wilson")
                        .email("olivia.wilson@hospital.com")
                        .password(passwordEncoder.encode(password))
                        .ssn("567-89-0124")
                        .dateOfBirth(LocalDate.of(1992, 5, 10))
                        .sex(Sex.FEMALE)
                        .phoneNumber("555-555-6666")
                        .nationality("American")
                        .address("555 Nurse Ln, Brookline, MA 02445")
                        .biography("Olivia is a registered nurse specializing in rehabilitation care.")
                        .role(UserRole.NURSE)
                        .startWorkingDate(LocalDate.of(2016, 4, 12))
                        .status(WorkingStatus.ACTIVE)
                        .licenseNumber("RN567890")
                        .qualification("BSN, Northeastern University")
                        .department("Rehabilitation")
                        .createdAt(Instant.now())
                        .build()
        };

        nurseRepository.saveAll(Arrays.asList(nurses));
    }
}
