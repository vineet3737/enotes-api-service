package com.becoder.repository;

import com.becoder.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepos extends JpaRepository<FavouriteNote, Integer> {
    List<FavouriteNote> findByUserId(int userId);
}
