package com.example.docmenuservice.service.serverImpl;

import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.example.commonservice.response.AuthResponse;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.entity.SubTitle;
import com.example.docmenuservice.model.request.FavoriteRequest;
import com.example.docmenuservice.repository.FavoriteRepository;
import com.example.docmenuservice.service.interfaces.FavoriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final WebClient.Builder webClient;

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
    public void removeFavorite(Long id) {
        Favorite favorite= favoriteRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id Not found"));
         favoriteRepository.deleteById(favorite.getId());
    }

    public AuthResponse validateUser(Long id){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try {
            return covertSpecificClass.convertValue(Objects.requireNonNull(webClient
                    .baseUrl("http://localhost:8082/")
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

    public SubTitle validateSubtitle(Long id){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try {
            return covertSpecificClass.convertValue(Objects.requireNonNull(webClient
                   .baseUrl("http://localhost:8081/")
                   .build()
                   .get()
                   .uri("api/v1/docs/{sub_Id}/SubTitle", id)
                   .retrieve()
                   .bodyToMono(ApiResponse.class)
                   .block()).getPayload(), SubTitle.class);
        }
        catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_SUBTITLE);
        }
    }

}
