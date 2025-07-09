package com.becoder.scheduler;

import com.becoder.entity.Notes;
import com.becoder.repository.NotesRepos;
import com.becoder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    private NotesRepos notesRepos;
    @Autowired
    private CategoryService categoryService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNoteScheduler(){

        LocalDateTime cuttOffDate = LocalDateTime.now().minusDays(7);
        //System.out.println("i");
        List<Notes> deleteNotes =  notesRepos.findAllByIsDeletedAndDeletedOnBefore(true, cuttOffDate);
        notesRepos.deleteAll(deleteNotes);

    }
}
