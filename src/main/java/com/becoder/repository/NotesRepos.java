package com.becoder.repository;

import com.becoder.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepos extends JpaRepository<Notes, Integer> {
    Page<Notes> findByCreatedBy(int userId, Pageable pageable);

    List<Notes> findByCreatedByAndIsDeletedTrue(int userId);

    Page<Notes> findByCreatedByAndIsDeletedFalse(int userId, Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cuttOffDate);


}
