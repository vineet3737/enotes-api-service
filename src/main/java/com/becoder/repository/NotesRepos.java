package com.becoder.repository;

import com.becoder.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepos extends JpaRepository<Notes, Integer> {
    Page<Notes> findByCreatedBy(int userId, Pageable pageable);

    List<Notes> findByCreatedByAndIsDeletedTrue(int userId);

    Page<Notes> findByCreatedByAndIsDeletedFalse(int userId, Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cuttOffDate);


    @Query("select n from Notes n where (Lower(n.title) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.description) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.category.name) like lower(concat('%',:keyword,'%'))) "
            + "and n.isDeleted=false "
            + "and n.createdBy=:userId")
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userId") Integer userId, Pageable pageable);


}
