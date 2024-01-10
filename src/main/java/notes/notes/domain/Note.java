package notes.notes.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(length = 5000)
    private String content;

    @ManyToOne
    private User user;

    public Note(Integer id, String title, String content, Integer userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = new User(userId, null, null, null, null, null, null);
    }
}
