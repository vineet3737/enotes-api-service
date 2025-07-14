package com.becoder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/saveNotes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam (required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id);

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id);

    @GetMapping("/recycleBin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/hardDelete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id);

    @DeleteMapping("/emptyRecycle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin();

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFavNotes(@PathVariable Integer noteId);

    @DeleteMapping("/unfav/{favId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUnFavNotes(@PathVariable Integer favId);

    @GetMapping("favNotesByUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNotes();

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable int id);

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserSearch(@RequestParam (name = "key", defaultValue = "") String key,
                                                     @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);
}
