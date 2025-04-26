package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseSearchService {
    <T> Page<T> search(Class<T> entityClass,
                       JpaSpecificationExecutor<T> repository,
                       SearchRequestDTO requestDTO);
}
