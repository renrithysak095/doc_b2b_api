package com.example.docmenuservice.controller;

import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.request.FavoriteRequest;
import com.example.docmenuservice.model.response.ApiResponse;
import com.example.docmenuservice.service.interfaces.FavoriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/docs")
@Tag(name = "Favorite - Service")
@CrossOrigin
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/addFavorite")
    public ResponseEntity<?> addNewFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteService.addNewFavorite(favoriteRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message("Favorite added successfully")
                .payload(favorite)
                .date(LocalDateTime.now())
                .status(200)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getFavorites/{userId}")
    public ResponseEntity<?> getFavorites(@PathVariable Long userId) {
        List<Favorite> favorite = favoriteService.getFavorites(userId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message("Get favorite by user successfully")
                .payload(favorite)
                .date(LocalDateTime.now())
                .status(200)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/removeFavorite/{id}")
    public ResponseEntity<ApiResponse<Object>> removeFavorite(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message("Get favorite by user successfully")
                .payload(null)
                .date(LocalDateTime.now())
                .status(200)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}

