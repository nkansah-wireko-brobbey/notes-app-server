package notes.notes.service;

import jakarta.transaction.Transactional;
import notes.notes.adapter.NoteAdapter;
import notes.notes.domain.Note;
import notes.notes.dto.NoteDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.NotesRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteService {

    private final NotesRepository notesRepository;

    public NoteService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public NoteDTO create(NoteDTO noteDTO) throws InvalidArgumentException {
        if (noteDTO == null)
            throw new InvalidArgumentException("NoteDTO is null");

            Note newNote = notesRepository.save(NoteAdapter.getNoteEntity(noteDTO));

        return NoteAdapter.getNoteDTO(newNote);
    }


}
