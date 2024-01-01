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


}
