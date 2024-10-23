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
    @ManyToOne
    @ToString.Exclude
    private Artist artist; //only used for singles

    public Song(SongDTO dto){
        this.name = dto.getName();
        this.type = dto.getType();
        this.songNumber = dto.getSongNumber();
    }

    public void giveId(int existingSongs){ //do NOT run this if it already connected to an artist/album
        int number = existingSongs + 2; //the 2 makes up for counting 1 higher and makes sure 0 isn't used
        if (this.artist != null){
            this.id = artist.getId() + "-" + "0" + "-" + number;
        }
        if (this.album == null){
            this.id = "0" + "-" + "0" + "-" + number;
        }
        if (this.album.getArtist() == null){
            this.id = this.album.getId() + "-" + number;
        }
    }
}
