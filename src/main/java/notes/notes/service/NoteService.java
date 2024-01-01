package notes.notes.service;

import jakarta.transaction.Transactional;
import notes.notes.adapter.NoteAdapter;
import notes.notes.domain.Note;
import notes.notes.dto.NoteDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.NotesRepository;
import notes.notes.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if (noteDTO.getUserId() == null)
            throw new InvalidArgumentException("User id is null");

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

    public List<NoteDTO> getAllNotes(Integer userId) throws InvalidArgumentException {

        if (userId == null)
            throw new InvalidArgumentException("User id is null");

        if (!userRepository.existsById(userId))
            throw new InvalidArgumentException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND);

        List<Note> notes = notesRepository.findAllByUserId(userId);

        if (notes == null)
            throw new InvalidArgumentException("Notes with user id " + userId + " does not exist", HttpStatus.NOT_FOUND);

        return NoteAdapter.getNoteDTOList(notes);
    }
}
