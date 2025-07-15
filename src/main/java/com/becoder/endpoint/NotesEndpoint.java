package com.becoder.endpoint;

import com.becoder.dto.NotesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Notes", description = "All the Notes Operation APIs")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save Notes", tags = { "Notes", "User" }, description = "User Save Notes")
    @PostMapping(value = "/saveNotes",consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam
                                           @Parameter(description = "Json String Notes",required = true,
                                                   content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes,
                                       @RequestParam (required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Download Upload File", tags = { "Notes", "User" }, description = "Download file ")
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get All Notes", tags = { "Notes" }, description = "Get All Notes Admin")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes();

    @Operation(summary = "Get All notes For User", tags = { "Notes", "User" }, description = "Get All notes For User")
    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @Operation(summary = "Delete Notes", tags = { "Notes", "User" }, description = "Delete Notes By user")
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id);

    @Operation(summary = "Restore Delete Notes", tags = { "Notes",
            "User" }, description = "Restore Delete Notes from Recycle Bin")
    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id);

    @Operation(summary = "Get Notes From Recycle Bin", tags = { "Notes",
            "User" }, description = "Get Notes From Recycle Bin")
    @GetMapping("/recycleBin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes();

    @Operation(summary = "Hard Delete Notes", tags = { "Notes", "User" }, description = "Hard Delete Notes")
    @DeleteMapping("/hardDelete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id);

    @Operation(summary = "Empty User Recycle Bin", tags = { "Notes", "User" }, description = "Empty User Recycle Bin")
    @DeleteMapping("/emptyRecycle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin();

    @Operation(summary = "Favorite Note", tags = { "Notes", "User" }, description = "User favorite notes")
    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFavNotes(@PathVariable Integer noteId);

    @Operation(summary = "UnFavoriteNote", tags = { "Notes", "User" }, description = "User UnFavorite Notes")
    @DeleteMapping("/unfav/{favId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUnFavNotes(@PathVariable Integer favId);

    @Operation(summary = "Get User Favorite Notes", tags = { "Notes", "User" }, description = "User Favorite Notes")
    @GetMapping("favNotesByUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNotes();

    @Operation(summary = "Copy Notes", tags = { "Notes", "User" }, description = "Copy Notes")
    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable int id);

    @Operation(summary = "Search Notes", tags = { "Notes", "User" }, description = "User Search Notes")
    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserSearch(@RequestParam (name = "key", defaultValue = "") String key,
                                                     @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);
}
