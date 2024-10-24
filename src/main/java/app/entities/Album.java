package app.entities;

import app.dtos.AlbumDTO;
import app.dtos.SongDTO;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "VARCHAR(255)", unique = true)
    private String albumSearchId; //is given right after Artist is persisted
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
        if (dto.getReleaseDate() != null) {
        this.releaseDate = dto.getReleaseDate();
        }
    }

    public void addSongsAsDTO(List<SongDTO> songs) { //this is used by Populate
        if (this.songs.isEmpty() && !songs.isEmpty()) {
            for (SongDTO dto : songs) {
                Song song = new Song(dto);
                String id = this.albumSearchId + "-" + song.getSongNumber();
                song.setSongSearchId(id);
                song.setAlbum(this);
                this.songs.add(song);
            }
                this.totalSongs = this.songs.size();
        }
    }

    public void addSongs(List<Song> songs) {
        if (this.songs.isEmpty() && !songs.isEmpty()) {
            for (Song song : songs) {
                String id;
                if (song.getSongNumber() != 0){
                    id = this.albumSearchId + "-" + song.getSongNumber();
                } else {
                    id = this.albumSearchId + "-" + this.songs.size()+1;
                }
                song.setSongSearchId(id);
                song.setAlbum(this);
                this.songs.add(song);
            }
                this.totalSongs = this.songs.size();
        }
    }

    public void giveId(int existingAlbums){ //do NOT run this if it already connected to an artist
        if (this.artist == null) {
            this.albumSearchId = "0" + "-" + (existingAlbums + 2); //the 2 makes up for counting 1 higher and makes sure 0 isn't used
        }
    }

}
