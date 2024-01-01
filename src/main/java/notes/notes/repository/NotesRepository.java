package notes.notes.repository;

import notes.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByUserId(Integer userId);
}
