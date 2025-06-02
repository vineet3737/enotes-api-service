package com.becoder.repository;

import com.becoder.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepos extends JpaRepository<Notes, Integer> {
}
