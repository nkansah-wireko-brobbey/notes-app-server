package notes.notes.controller;

import notes.notes.dto.NoteDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    public NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> createNotes(@RequestBody NoteDTO noteDTO) throws InvalidArgumentException{
        System.out.println("NoteDTO: " + noteDTO);
        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null", HttpStatus.BAD_REQUEST);

        NoteDTO newNote = noteService.create(noteDTO);

        return new ResponseEntity<>(newNote, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<NoteDTO> getAllNotes() {
        System.out.println("Get all notes");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<NoteDTO>> getAllNotesByUserId(@PathVariable Integer userId) throws InvalidArgumentException{
        System.out.println("Get all notes by user id");

        if (userId == null)
            throw new InvalidArgumentException("User id is null", HttpStatus.BAD_REQUEST);

        List<NoteDTO> allNotes = noteService.getAllNotes(userId);

        return new ResponseEntity<>(allNotes,HttpStatus.OK);
    }




}
