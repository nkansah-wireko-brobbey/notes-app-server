package notes.notes.service;

import jakarta.transaction.Transactional;
import notes.notes.adapter.NoteAdapter;
import notes.notes.domain.Note;
import notes.notes.domain.User;
import notes.notes.dto.NoteDTO;
import notes.notes.dto.UserDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.NotesRepository;
import notes.notes.repository.UserRepository;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {

    private final NotesRepository notesRepository;

    private final UserRepository userRepository;

    public NoteService(NotesRepository notesRepository, UserRepository userRepository) {
        this.notesRepository = notesRepository;
        this.userRepository = userRepository;
    }

    public NoteDTO create(NoteDTO noteDTO) throws InvalidArgumentException {
        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null");

        if (noteDTO.getUserId() != null)
            throw new InvalidArgumentException("User id is not null");

        noteDTO.setUserId(getCurrentUserId());

        if (!userRepository.existsById(noteDTO.getUserId()))
            throw new InvalidArgumentException("User with id " + noteDTO.getUserId() + " does not exist", HttpStatus.NOT_FOUND);

        Note newNote = notesRepository.save(NoteAdapter.getNoteEntity(noteDTO));

        return NoteAdapter.getNoteDTO(newNote);
    }

    public NoteDTO updateNote(NoteDTO noteDTO) throws InvalidArgumentException {
        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null");

        if (noteDTO.getId() == null)
            throw new InvalidArgumentException("Note id is null");

        if (!notesRepository.existsById(noteDTO.getId()))
            throw new InvalidArgumentException("Note with id " + noteDTO.getId() + " does not exist", HttpStatus.NOT_FOUND);

        if (noteDTO.getUserId() == null)
            throw new InvalidArgumentException("User id is null");

        if (!userRepository.existsById(noteDTO.getUserId()))
            throw new InvalidArgumentException("User with id " + noteDTO.getUserId() + " does not exist", HttpStatus.NOT_FOUND);

        Note newNote = notesRepository.save(NoteAdapter.getNoteEntity(noteDTO));

        return NoteAdapter.getNoteDTO(newNote);
    }

    public List<NoteDTO> getAllNotes() throws InvalidArgumentException {

        Integer userId = getCurrentUserId();

        if (userId == null)
            throw new InvalidArgumentException("User id is null");

        if (!userRepository.existsById(userId))
            throw new InvalidArgumentException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND);

        List<Note> notes = notesRepository.findAllByUserId(userId);

        if (notes == null)
            throw new InvalidArgumentException("Notes with user id " + userId + " does not exist", HttpStatus.NOT_FOUND);

        return NoteAdapter.getNoteDTOList(notes);
    }

    private String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication.getName(): " + authentication.getName());
        return authentication.getName();
    }

    private Integer getCurrentUserId(){
        String email = getCurrentUser();

        if (email == null)
            throw new InvalidArgumentException("User is not authenticated", HttpStatus.UNAUTHORIZED);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty())
            throw new InvalidArgumentException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);

        return userOptional.get().getId();
    }

    private String getCurrentUserRole(){
        String email = getCurrentUser();

        if (email == null)
            throw new InvalidArgumentException("User is not authenticated", HttpStatus.UNAUTHORIZED);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty())
            throw new InvalidArgumentException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);

        return userOptional.get().getRole().toString();
    }

    public NoteDTO getNoteById(Integer id) throws InvalidArgumentException {
        if (id == null)
            throw new InvalidArgumentException("Note id is null");

        Optional<Note> noteOptional = notesRepository.findById(id);

        if (noteOptional.isEmpty())
            throw new InvalidArgumentException("Note with id " + id + " does not exist", HttpStatus.NOT_FOUND);

        Note note = noteOptional.get();

        if (!note.getUser().getId().equals(getCurrentUserId()) && !getCurrentUserRole().equals("ADMIN"))
            throw new InvalidArgumentException("User is not authorized to get this note", HttpStatus.UNAUTHORIZED);

        return NoteAdapter.getNoteDTO(note);
    }

    public NoteDTO updateNoteById(Integer id, NoteDTO noteDTO) throws InvalidArgumentException {
        if (id == null)
            throw new InvalidArgumentException("Note id is null");

        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null");

        if (!notesRepository.existsById(id))
            throw new InvalidArgumentException("Note with id " + id + " does not exist", HttpStatus.NOT_FOUND);

        if (noteDTO.getUserId() == null)
            throw new InvalidArgumentException("User id is null");

        if (!userRepository.existsById(noteDTO.getUserId()))
            throw new InvalidArgumentException("User with id " + noteDTO.getUserId() + " does not exist", HttpStatus.NOT_FOUND);

        Note note = NoteAdapter.getNoteEntity(noteDTO);
        note.setId(id);

        Note newNote = notesRepository.save(note);

        return NoteAdapter.getNoteDTO(newNote);
    }

}
