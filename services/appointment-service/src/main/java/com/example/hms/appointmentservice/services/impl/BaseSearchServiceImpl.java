package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import com.example.hms.appointmentservice.services.BaseSearchService;
import com.example.hms.appointmentservice.utils.FilteringSpecification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseSearchServiceImpl implements BaseSearchService {
    private final EntityManager entityManager;

    public <T> Page<T> search(Class<T> entityClass,
                              JpaSpecificationExecutor<T> repository,
                              SearchRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(
                requestDTO.getPage(),
                requestDTO.getSize(),
                parseSort(requestDTO.getSort())
        );

        var spec = FilteringSpecification.build(
                entityClass, requestDTO.getFilters());

        return repository.findAll(spec, pageable);
    }

    private Sort parseSort(List<String> sortList) {
        if (sortList == null || sortList.isEmpty()) return Sort.unsorted();
        return Sort.by(sortList.stream().map(s -> {
            String[] parts = s.split(",");
            return parts.length == 2 && parts[1].equalsIgnoreCase("desc")
                    ? new Sort.Order(Sort.Direction.DESC, parts[0])
                    : new Sort.Order(Sort.Direction.ASC, parts[0]);
        }).toList());
    }
}
