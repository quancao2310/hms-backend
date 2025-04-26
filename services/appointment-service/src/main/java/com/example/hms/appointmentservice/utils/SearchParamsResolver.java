package com.example.hms.appointmentservice.utils;

import com.example.hms.appointmentservice.dtos.FilteringDTO;
import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

@Component
public class SearchParamsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SearchParams.class) &&
                parameter.getParameterType().equals(SearchRequestDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) return null;

        List<FilteringDTO> filters = new ArrayList<>();
        String[] filterParams = request.getParameterValues("filter");
        if (filterParams != null) {
            for (String f : filterParams) {
                String[] parts = f.split(":", 3);
                if (parts.length == 3) {
                    filters.add(new FilteringDTO(parts[0], parts[1], parts[2]));
                }
            }
        }

        List<String> sortParams = Optional.ofNullable(request.getParameterValues("sort"))
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

        int page = Optional.ofNullable(request.getParameter("page"))
                .map(Integer::parseInt).orElse(0);

        int size = Optional.ofNullable(request.getParameter("size"))
                .map(Integer::parseInt).orElse(10);

        return new SearchRequestDTO(filters, sortParams, page, size);
    }
}
