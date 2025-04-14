package com.example.hms.appointmentservice.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hms.appointmentservice.dtos.UrlDTO;
import com.example.hms.appointmentservice.dtos.UrlsDTO;
import com.example.hms.appointmentservice.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public UrlDTO uploadFile(MultipartFile file) throws Exception {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return new UrlDTO((String) uploadResult.get("url"));
    }

    @Override
    public UrlsDTO uploadFiles(List<MultipartFile> files) throws Exception {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file: files) {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String fileUrl = (String) uploadResult.get("url");
            fileUrls.add(fileUrl);
        }

        return new UrlsDTO(fileUrls);
    }
}
