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

@CrossOrigin
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
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        System.out.println("Get all notes");
        List<NoteDTO> allNotes = noteService.getAllNotes();

        return new ResponseEntity<>(allNotes,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Integer id) throws InvalidArgumentException{
        System.out.println("Get note by id");

        if (id == null)
            throw new InvalidArgumentException("Note id is null", HttpStatus.BAD_REQUEST);

        NoteDTO noteDTO = noteService.getNoteById(id);

        return new ResponseEntity<>(noteDTO,HttpStatus.OK);
    }

    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<NoteDTO>> getAllNotesByUserId(@PathVariable Integer userId) throws InvalidArgumentException{
        System.out.println("Get all notes by user id");

        if (userId == null)
            throw new InvalidArgumentException("User id is null", HttpStatus.BAD_REQUEST);

        List<NoteDTO> allNotes = noteService.getAllNotes();

        return new ResponseEntity<>(allNotes,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNotesById(@PathVariable Integer id, @RequestBody NoteDTO noteDTO) throws InvalidArgumentException{
        System.out.println("Update note by id");

        if (id == null)
            throw new InvalidArgumentException("User id is null", HttpStatus.BAD_REQUEST);

        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null", HttpStatus.BAD_REQUEST);

        NoteDTO updatedNote = noteService.updateNoteById(id, noteDTO);

        return new ResponseEntity<>(updatedNote,HttpStatus.OK);
    }



}
