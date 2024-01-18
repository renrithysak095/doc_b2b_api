package com.example.authservice.service.image;
import com.example.authservice.response.FileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    FileResponse saveImage(Long userId, MultipartFile file, HttpServletRequest request) throws IOException;

    ByteArrayResource getImage(String fileName) throws IOException;

    FileResponse uploadImage(MultipartFile file, HttpServletRequest request) throws IOException;
}
