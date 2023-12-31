package notes.notes.adapter;

import notes.notes.domain.Note;
import notes.notes.dto.NoteDTO;

public class NoteAdapter {

    public static Note getNoteEntity(NoteDTO noteDTO) {
        if (noteDTO == null)
            return new Note();

        return new Note(
                noteDTO.getId(),
                noteDTO.getTitle(),
                noteDTO.getContent(),
                noteDTO.getUserId()
        );
    }

    public static NoteDTO getNoteDTO(Note note) {
        if (note == null)
            return new NoteDTO();

        return new NoteDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getUser().getId()
        );
    }
}
