package com.example.authservice.service.image;

import com.example.authservice.config.FileStorageProperties;
import com.example.authservice.enitity.Auth;
import com.example.authservice.enitity.FileImage;
import com.example.authservice.exception.NotFoundExceptionClass;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.repository.ImageRepository;
import com.example.authservice.response.FileResponse;
import com.example.commonservice.config.ValidationConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{

    private final FileStorageProperties fileStorageProperties;
    private final AuthRepository authRepository;

    private final ImageRepository imageRepository;

    public ImageServiceImpl(FileStorageProperties fileStorageProperties, AuthRepository authRepository, ImageRepository imageRepository) {
        this.fileStorageProperties = fileStorageProperties;
        this.authRepository = authRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public FileResponse saveImage(Long userId, MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = fileStorageProperties.getUploadPath();
        Path directoryPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        java.io.File directory = directoryPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = UUID.randomUUID() + file.getOriginalFilename().replaceAll("\\s+","");
        validateImages(fileName);
        File dest = new File(directoryPath.toFile(), fileName);
        file.transferTo(dest);
        // Update field
        Auth auth = authRepository.findById(userId).orElseThrow(() -> new NotFoundExceptionClass(ValidationConfig.NOTFOUND_USER));
        auth.setImage(fileName);
        authRepository.save(auth);
        return new FileResponse(fileName,file.getContentType(),file.getSize());
    }

    @Override
    public ByteArrayResource getImage(String fileName) throws IOException {
        String filePath = "auth-service/src/main/resources/storage/" + fileName;
        Path path = Paths.get(filePath);
        if(!Files.exists(path)){
            throw new NotFoundExceptionClass(ValidationConfig.FILE_NOTFOUND);
        }
        String uploadPath = fileStorageProperties.getUploadPath();
        Path paths = Paths.get(uploadPath + fileName);
        return new ByteArrayResource(Files.readAllBytes(paths));
    }

    @Override
    public FileResponse uploadImage(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = fileStorageProperties.getUploadPath();
        Path directoryPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        java.io.File directory = directoryPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = UUID.randomUUID() + file.getOriginalFilename().replaceAll("\\s+","");
        validateImages(fileName);
        File dest = new File(directoryPath.toFile(), fileName);
        file.transferTo(dest);
        return imageRepository.save(new FileImage(null,fileName,file.getContentType(), file.getSize())).toDto();
    }

    // Validation String image
    public static void validateImages(String fileName){
        String[] validExtensions = {".jpg", ".jpeg", ".png", ".tiff"};
        boolean isValidExtension = false;
        for (String extension : validExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                isValidExtension = true;
                break;
            }
        }
        if (!isValidExtension) {
            throw new IllegalArgumentException(ValidationConfig.ILLEGAL_FILE);
        }
    }
}
