package com.example.hms.models.internal.staff;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "admin_id")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Admin extends Staff {
}
