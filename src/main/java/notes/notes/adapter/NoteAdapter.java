package notes.notes.adapter;

import notes.notes.domain.Note;
import notes.notes.dto.NoteDTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<NoteDTO> getNoteDTOList(List<Note> notes) {
            if (notes == null)
                return new ArrayList<>();

        return notes.stream()
                .map(NoteAdapter::getNoteDTO)
                .toList();

    }

    public static List<Note> getNoteEntityList(List<NoteDTO> noteDTOs) {
        if (noteDTOs == null)
            return new ArrayList<>();

        return noteDTOs.stream()
                .map(NoteAdapter::getNoteEntity)
                .toList();
    }
}
