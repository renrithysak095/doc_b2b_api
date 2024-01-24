package com.example.docmenuservice.service.serverImpl;

import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.example.commonservice.response.AuthResponse;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.entity.SubTitle;
import com.example.docmenuservice.model.request.FavoriteRequest;
import com.example.docmenuservice.repository.FavoriteRepository;
import com.example.docmenuservice.service.interfaces.FavoriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final WebClient.Builder webClient;

    @Value("${authURL}")
    private String authURL;

    @Value("${docURL}")
    private String docURL;


    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, WebClient.Builder webClient) {
        this.favoriteRepository = favoriteRepository;
        this.webClient = webClient;
    }

    @Override
    public Favorite addNewFavorite(FavoriteRequest favoriteRequest) {
        validateUser(favoriteRequest.getUserId());
        validateSubtitle(favoriteRequest.getSubTitleId());
        favoriteRequest.setUserId(favoriteRequest.getUserId());
        favoriteRequest.setSubTitleId(favoriteRequest.getSubTitleId());
        return favoriteRepository.save(favoriteRequest.toEntity());
    }

    @Override
    public List<Favorite> getFavorites(Long userId) {
        validateUser(userId);
        return favoriteRepository.findAllByUserId(userId);
    }

    @Override
    public Void removeFavorite(Long id) {
        Favorite favorite= favoriteRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id Not found"));
         favoriteRepository.deleteById(favorite.getId());
         return null;
    }

    public void validateUser(Long id){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try {
            covertSpecificClass.convertValue(Objects.requireNonNull(webClient
                    .baseUrl(authURL)
                    .build()
                    .get()
                    .uri("api/v1/users/{userId}", id)
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .block()).getPayload(), AuthResponse.class);
        }
        catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_USER);
        }
    }

    public void validateSubtitle(Long id){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try {
            covertSpecificClass.convertValue(Objects.requireNonNull(webClient
                   .baseUrl(docURL)
                   .build()
                   .get()
                   .uri("api/v1/main-titles/sub-title/{subId}", id)
                   .retrieve()
                   .bodyToMono(ApiResponse.class)
                   .block()).getPayload(), SubTitle.class);
        }
        catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_SUBTITLE);
        }
    }

}
