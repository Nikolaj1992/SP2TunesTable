package app.entities;

import app.dtos.AlbumDTO;
import app.dtos.SongDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Album {
    @Id
    private String id; //is given right after Artist is persisted
    private String name;
    private String type;
    private int totalSongs;
    private String releaseDate;
    @ManyToOne
    @ToString.Exclude
    private Artist artist;
    @OneToMany
    @Cascade(CascadeType.PERSIST)
    private List<Song> songs = new ArrayList<>();

    public Album(AlbumDTO dto) {
        this.name = dto.getName();
        this.type = dto.getType();
        if (!dto.getTracks().getSongs().isEmpty()){
        this.totalSongs = dto.getTotalSongs();
        }
        this.releaseDate = dto.getReleaseDate();
    }

    public void addSongsAsDTO(List<SongDTO> songs) { //this is used by Populate
        if (this.songs.isEmpty() && !songs.isEmpty()) {
            for (SongDTO dto : songs) {
                Song song = new Song(dto);
                String id = this.id + "-" + song.getSongNumber();
                song.setId(id);
                song.setAlbum(this);
                this.songs.add(song);
            }
                this.totalSongs = this.songs.size();
        }
    }

    public void addSongs(List<Song> songs) { //this is used by Populate
        if (this.songs.isEmpty() && !songs.isEmpty()) {
            for (Song song : songs) {
                String id;
                if (song.getSongNumber() != 0){
                    id = this.id + "-" + song.getSongNumber();
                } else {
                    id = this.id + "-" + this.songs.size()+1;
                }
                song.setId(id);
                song.setAlbum(this);
                this.songs.add(song);
            }
                this.totalSongs = this.songs.size();
        }
    }

    public void giveId(int existingAlbums){ //do NOT run this if it already connected to an artist
        if (this.artist == null) {
            this.id = "0" + "-" + (existingAlbums + 2); //the 2 makes up for counting 1 higher and makes sure 0 isn't used
        }
    }

}
