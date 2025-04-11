package com.example.hms.appointmentservice.utils;

import com.cloudinary.api.exceptions.BadRequest;
import com.example.hms.appointmentservice.dtos.FilteringDTO;
import com.example.hms.appointmentservice.enums.FilterRule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.ws.rs.BadRequestException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FilteringSpecification {
    public static <T> Specification<T> build(Class<T> entityClass,
                                             List<FilteringDTO> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            for (FilteringDTO filter: filters) {
                String prop = filter.getProperty();
                String val = filter.getValue();
                Path<?> path = root.get(prop);

                switch (FilterRule.fromValue(filter.getRule())) {
                    case EQUALS -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.equal(
                                    path, convert(path, val)
                            ));
                    case NOT_EQUALS -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.notEqual(
                                    path, convert(path, val)
                            ));
                    case GREATER_THAN -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.greaterThan(
                                    path.as(Comparable.class), (Comparable<Object>) convert(path, val)
                            ));
                    case GREATER_THAN_OR_EQUALS -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                                    path.as(Comparable.class), (Comparable<Object>) convert(path, val)
                            ));
                    case LESS_THAN -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.lessThan(
                                    path.as(Comparable.class), (Comparable<Object>) convert(path, val)
                            ));
                    case LESS_THAN_OR_EQUALS -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.lessThanOrEqualTo(
                                    path.as(Comparable.class), (Comparable<Object>) convert(path, val)
                            ));
                    case LIKE -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.like(path.as(String.class), "%" + val + "%"));
                    case IS_NULL -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.isNull(path));
                    case IS_NOT_NULL -> predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder.isNotNull(path));
                    case IN -> {
                        CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(path);
                        for (String v: val.split(",")) {
                            inClause.value(convert(path, v));
                        }
                        predicate = criteriaBuilder.and(predicate, inClause);
                    }
                    case NOT_IN -> {
                        CriteriaBuilder.In<Object> notInClause = criteriaBuilder.in(path);
                        for (String v: val.split(",")) {
                            notInClause.value(convert(path, v));
                        }
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(notInClause));
                    }
                    default -> {
                        throw new BadRequestException("Invalid request");
                    }
                }
            }

            return predicate;
        };
    }

    private static Object convert(Path<?> path, String val) {
        Class<?> type = path.getJavaType();

        if (type.equals(LocalDate.class)) {
            return LocalDate.parse(val);
        }
        if (type.equals(Long.class)) {
            return Long.parseLong(val);
        }
        if (type.equals(Integer.class)) {
            return Integer.parseInt(val);
        }
        if (UUID.class.isAssignableFrom(type)) {
            return UUID.fromString(val);
        }
        if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type, val);
        }
        return val;
    }
}
