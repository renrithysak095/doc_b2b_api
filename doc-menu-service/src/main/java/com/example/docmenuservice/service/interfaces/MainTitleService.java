package com.example.docmenuservice.service.interfaces;

import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.dto.SubTitleDto;
import com.example.docmenuservice.model.request.FileUploadRequest;
import com.example.docmenuservice.model.request.MainTitleRequest;
import com.example.docmenuservice.model.request.SubTitleRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MainTitleService {
    MainTitleDto addNewMainTitle(MainTitleRequest mainTitleRequest);
    List<MainTitleDto> geAllMainTitle();
    Void deleteMainTitleById(Long id);

    MainTitleDto updateMainTitleById(MainTitleRequest mainTitleRequest, Long id);

    MainTitleDto getMainTitleById(Long id);

    SubTitleDto addNewSubTitleTOMainTitle(SubTitleRequest subTitleRequest, Long id);
    SubTitleDto updateSubTitleInMainTitleId(SubTitleRequest subTitleRequest, Long id);

    Void deleteSubTitleFromMainTitleById( Long sub_Id);

    List<SubTitleDto> getAllSubTitleByMainTitleId(Long id);


    FileUploadRequest saveFile(MultipartFile file, HttpServletRequest request, Long id)throws IOException;

    List<FileUploadRequest>saveMultipleFile(List<MultipartFile> files,HttpServletRequest request,Long id) throws IOException;

    Void deleteFileUploadsByMainTitleId( Long file_id);

    byte[] getFileContent(String fileName) throws IOException;

       SubTitleDto getSubTitleById(Long sub_Id);



}
