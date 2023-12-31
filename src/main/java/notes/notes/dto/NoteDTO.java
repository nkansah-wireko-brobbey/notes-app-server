package notes.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;

}
