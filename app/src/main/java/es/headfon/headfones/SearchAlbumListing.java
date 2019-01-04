package es.headfon.headfones;

public class SearchAlbumListing {
    private String albumName;
    private String albumCover;

    public SearchAlbumListing(String albumName, String albumCover) {
        this.albumName = albumName;
        this.albumCover = albumCover;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }
}
