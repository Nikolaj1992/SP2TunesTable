package app.entities;

import app.dtos.SongDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Song {
    @Id
    private String id; //is given when an album is created, after an artist is persisted
    private String name;
    private String type;
    private int songNumber;
    @ManyToOne
    @ToString.Exclude
    private Album album;

    public Song(SongDTO dto){
        this.name = dto.getName();
        this.type = dto.getType();
        this.songNumber = dto.getSongNumber();
    }
}
