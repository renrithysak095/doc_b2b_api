package com.example.docmenuservice.service.serverImpl;

import com.example.docmenuservice.config.FileUploadProperties;
import com.example.docmenuservice.exception.InternalServerErrorException;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.exception.NullExceptionClass;
import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.dto.SubTitleDto;
import com.example.docmenuservice.model.entity.FileUpload;
import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.entity.SubTitle;
import com.example.docmenuservice.model.request.FileUploadRequest;
import com.example.docmenuservice.model.request.MainTitleRequest;
import com.example.docmenuservice.model.request.SubTitleRequest;
import com.example.docmenuservice.repository.FileUploadRepository;
import com.example.docmenuservice.repository.MainTitleRepository;
import com.example.docmenuservice.repository.SubTitleRepository;
import com.example.docmenuservice.service.interfaces.MainTitleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainTitleServiceImpl implements MainTitleService {

    private final MainTitleRepository mainTitleRepository;
    private final SubTitleRepository subTitleRepository;

    private final FileUploadRepository fileUploadRepository;
    private final FileUploadProperties fileUploadProperities;

    public MainTitleServiceImpl(MainTitleRepository mainTitleRepository, SubTitleRepository subTitleRepository, FileUploadRepository fileUploadRepository, FileUploadProperties fileUploadProperities) {
        this.mainTitleRepository = mainTitleRepository;
        this.subTitleRepository = subTitleRepository;
        this.fileUploadRepository = fileUploadRepository;
        this.fileUploadProperities = fileUploadProperities;
    }

    @Override
    public MainTitleDto addNewMainTitle(MainTitleRequest mainTitleRequest) {
        if (mainTitleRequest==null || mainTitleRequest.getMainTitle()==null || mainTitleRequest.getMainTitle().isEmpty() || mainTitleRequest.getMainTitle().isBlank()){
            throw new InternalServerErrorException("MainTitle can not be empty or blank");
        }
        var ownerEntity= mainTitleRequest.toEntity();
        return mainTitleRepository.save(ownerEntity).toDto();
    }

    @Override
    public List<MainTitleDto> geAllMainTitle() {
        List<MainTitle> mainTitles= mainTitleRepository.findAll();
        return mainTitles.stream()
                .map(MainTitle::toDto).toList()
                .stream().collect(Collectors.toList());
    }

    @Override
    public Void deleteMainTitleById(Long id) {
        MainTitle mainTitle= mainTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id Not found"));
        mainTitleRepository.deleteById(mainTitle.getId());
        return null;
    }

    @Override
    public MainTitleDto updateMainTitleById(MainTitleRequest mainTitleRequest, Long id) {
        MainTitle mainTitle1=mainTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass(id + " not found"));
        if (mainTitleRequest==null|| mainTitleRequest.getMainTitle()==null || mainTitleRequest.getMainTitle().isBlank() || mainTitleRequest.getMainTitle().isEmpty()){
            throw new NullExceptionClass("mainTitle can not be null or blank");
        }
        else {
            Optional<MainTitle> mainTitleOptional=mainTitleRepository.findById(id);
            if (mainTitleOptional.isPresent()){
                MainTitle mainTitle=mainTitleOptional.get();
                mainTitle.setMainTitle(mainTitleRequest.getMainTitle());
                return mainTitleRepository.save(mainTitle).toDto();
            }
            else {
                return null;
            }
        }
    }

    @Override
    public MainTitleDto getMainTitleById(Long id) {
        MainTitle mainTitle= mainTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id not found"));
        return mainTitleRepository.findById(id).get().toDto();
    }

    @Override
    public SubTitleDto addNewSubTitleTOMainTitle(SubTitleRequest subTitleRequest, Long id) {
        if (subTitleRequest==null || subTitleRequest.getSubTitle()==null || subTitleRequest.getSubTitle().isBlank()|| subTitleRequest.getSubTitle().isEmpty()){
            throw new InternalServerErrorException("SubTitle and Content can not be null");
        }
        else {
            MainTitle mainTitle=mainTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id not found"));
            var subTitleEntity=subTitleRequest.toEntity(mainTitle);
            return subTitleRepository.save(subTitleEntity).toDto();
        }
    }

    @Override
    public SubTitleDto updateSubTitleInMainTitleId(SubTitleRequest subTitleRequest, Long id) {
        SubTitle subTitle=subTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Account id not found"));
        if (subTitleRequest==null || subTitleRequest.getSubTitle()==null || subTitleRequest.getSubTitle().isBlank()|| subTitleRequest.getSubTitle().isEmpty()
        ){
            throw new InternalServerErrorException("SubTitle and Content can not be null");
        }
        else {
            Optional<SubTitle> subTitles = subTitleRepository.findById(id);
            if (subTitles.isPresent()) {
                SubTitle subTitle1 = subTitles.get();
                subTitle1.setSubTitle(subTitleRequest.getSubTitle());
                return subTitleRepository.save(subTitle1).toDto();
            } else {
                return null;
            }
        }

    }

    @Override
    public Void deleteSubTitleFromMainTitleById(Long sub_Id) {
        SubTitle subTitle= subTitleRepository.findById(sub_Id).orElseThrow(()->new NotFoundExceptionClass("account_Id not found"));
        subTitleRepository.deleteById(subTitle.getId());
        return null;
    }

    @Override
    public List<SubTitleDto> getAllSubTitleByMainTitleId(Long id) {
        MainTitle mainTitle=mainTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id not found"));
        List<SubTitle> subTitles= subTitleRepository.findByMainTitleId(id);
        return subTitles.stream()
                .map(SubTitle::toDto).toList()
                .stream().collect(Collectors.toList());
    }

    @Override
    public FileUploadRequest saveFile(MultipartFile file, HttpServletRequest request, Long id) throws IOException {

        System.out.println("ID: " + id);

        MainTitle mainTitle = mainTitleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Owner_Id Not found"));


        FileUpload fileUpload = new FileUpload();

        fileUpload.setFileName(file.getOriginalFilename());
        fileUpload.setFileType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUpload.setFileUrl(String.valueOf(request.getRequestURL()).substring(0, 22) + "images/" + fileUpload.getFileName());
        fileUpload.setMainTitle(mainTitle);

        String uploadPath = fileUploadProperities.getUploadPath();
        Path directoryPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        File directory = directoryPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(directory, fileName);
        file.transferTo(dest);

        fileUploadRepository.save(fileUpload);

        return new FileUploadRequest(fileUpload.getFileName(), fileUpload.getFileUrl(), fileUpload.getFileType(), fileUpload.getSize());
    }


    @Override
    public List<FileUploadRequest> saveMultipleFile(List<MultipartFile> files, HttpServletRequest request, Long id) throws IOException {
        MainTitle mainTitle = mainTitleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Owner_Id Not found"));
        List<FileUploadRequest> fileUploadRequests= new ArrayList<>();
        for (MultipartFile file:files){
            String uploadPath= fileUploadProperities.getUploadPath();
            Path directPath=Paths.get(uploadPath).toAbsolutePath().normalize();
            java.io.File directory= directPath.toFile();
            if (!directory.exists()){
                directory.mkdirs();
            }
            String fileName= file.getOriginalFilename();
            File dest= new File(directPath.toFile(), fileName);

            file.transferTo(dest);

            FileUpload fileUpload= new FileUpload();
            fileUpload.setFileName(file.getOriginalFilename());
            fileUpload.setFileUrl(String.valueOf(request.getRequestURL()).substring(0,22)+"images/"+fileUpload.getFileName());
            fileUpload.setFileType(file.getContentType());
            fileUpload.setSize(file.getSize());
            fileUpload.setMainTitle(mainTitle);
            fileUploadRepository.save(fileUpload);
            fileUploadRequests.add(new FileUploadRequest(fileUpload.getFileName(),fileUpload.getFileType(),fileUpload.getFileUrl(), fileUpload.getSize()));
        }
        return fileUploadRequests;
    }



    @Override
    public Void deleteFileUploadsByMainTitleId( Long file_id) {
        FileUpload fileUpload= fileUploadRepository.findById(file_id).orElseThrow(()->new NotFoundExceptionClass("File Not Found"));
        fileUploadRepository.deleteById(fileUpload.getId());
        return null;
    }

    @Override
    public byte[] getFileContent(String fileName) throws IOException {
        String uploadPath = fileUploadProperities.getUploadPath();
        Path path = Paths.get(uploadPath + fileName);
        Resource file = new ByteArrayResource(Files.readAllBytes(path));
        return file.getContentAsByteArray();
    }

    @Override
    public SubTitleDto getSubTitleById(Long sub_Id) {
        SubTitle subTitles= subTitleRepository.findById(sub_Id).orElseThrow(()->new NotFoundExceptionClass("Id not found"));
        return subTitleRepository.findById(sub_Id).get().toDto();
    }


}
