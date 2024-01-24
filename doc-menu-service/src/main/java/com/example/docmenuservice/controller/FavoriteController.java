package com.example.docmenuservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.request.FavoriteRequest;
import com.example.docmenuservice.service.interfaces.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/favorites")
@Tag(name = "Favorite - Service")
@CrossOrigin
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Favorite>> addNewFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        return new ResponseEntity<>(new ApiResponse<>(
                "favorite added successfully",
                favoriteService.addNewFavorite(favoriteRequest),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Favorite>>> getFavorites(@PathVariable Long userId) {
        return new ResponseEntity<>(new ApiResponse<>(
                "get favorite by user id successfully",
                favoriteService.getFavorites(userId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete favorite")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "remove department by successfully",
                favoriteService.removeFavorite(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
}

