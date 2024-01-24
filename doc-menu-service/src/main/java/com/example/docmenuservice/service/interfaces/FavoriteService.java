package com.example.docmenuservice.service.interfaces;

import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.request.FavoriteRequest;

import java.util.List;

public interface FavoriteService {
    Favorite addNewFavorite(FavoriteRequest favoriteRequest);
    List<Favorite> getFavorites(Long userId);
    Void removeFavorite(Long id);
}
