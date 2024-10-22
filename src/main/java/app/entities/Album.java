package app.entities;

import app.dtos.AlbumDTO;
import app.dtos.ArtistDTO;
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
    private Artist artist;
    @OneToMany
    @Cascade(CascadeType.PERSIST)
    private List<Song> songs = new ArrayList<>();

    public Album(AlbumDTO dto) {
        this.name = dto.getName();
        this.type = dto.getType();
        this.totalSongs = dto.getTotalSongs();
        addSongs(dto.getTracks().getSongs());
        addReleaseDate(dto.getReleaseDate(), dto.getReleaseDatePrecision());
    }

    public void addSongs(List<SongDTO> songs) {
        if (!this.songs.isEmpty() && !songs.isEmpty()) {
            for (SongDTO dto : songs) {
                Song song = new Song(dto);
                String id = this.id + "-" + song.getSongNumber();
                song.setId(id);
                song.setAlbum(this);
                this.songs.add(song);
            }
        }
    }

    private void addReleaseDate(String rd, String rdp) {
        if (this.releaseDate != null && rd != "" && rdp == "day") {
            this.releaseDate = rd;
        }
        //rd = release date
        //rdp = release date precision
    }

}
