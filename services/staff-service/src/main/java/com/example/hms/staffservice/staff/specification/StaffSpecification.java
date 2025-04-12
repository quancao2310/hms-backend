package com.example.hms.staffservice.staff.specification;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.models.internal.staff.Nurse;
import com.example.hms.models.internal.staff.Staff;
import org.springframework.data.jpa.domain.Specification;

public class StaffSpecification {

    public static Specification<Staff> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get("status"), WorkingStatus.valueOf(status));
        };
    }

    public static Specification<Staff> hasRole(String role) {
        return (root, query, cb) -> {
            if (role == null) {
                return null;
            }

            return cb.equal(root.get("role"), UserRole.valueOf(role));
        };
    }

    public static Specification<Staff> filterSex(String sex) {
        return (root, query, cb) -> {
            if (sex == null)  {
                return null;
            }

            return cb.equal(root.get("sex"), Sex.valueOf(sex));
        };
    }

    public static Specification<Staff> hasDepartment(String department) {
        return (root, query, cb) -> {
            if (department == null || department.isBlank()) {
                return null;
            }

            return cb.or(
                    cb.equal(cb.treat(root, Doctor.class).get("department"), department),
                    cb.equal(cb.treat(root, Nurse.class).get("department"), department)
            );
        };
    }

    public static Specification<Staff> matchesSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }

            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("fullName")), like),
                    cb.like(cb.lower(root.get("email")), like)
            );
        };
    }

    public static Specification<Staff> excludeAdminIfNotAdmin(boolean isAdmin) {
        return (root, query, cb) -> {
            if (isAdmin) {
                return null;
            }

            return cb.notEqual(root.get("role"), UserRole.ADMIN);
        };
    }
}
