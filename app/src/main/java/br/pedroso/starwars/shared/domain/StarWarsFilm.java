package br.pedroso.starwars.shared.domain;

/**
 * Created by felipe on 01/03/17.
 */
public class StarWarsFilm {
    private String url;

    private String title;

    private int episodeId;

    private long releaseDate;

    private String directorName;

    private String posterUrl;

    public StarWarsFilm(String url, String title, int episodeId, long releaseDate, String directorName, String posterUrl) {
        this.url = url;
        this.title = title;
        this.episodeId = episodeId;
        this.releaseDate = releaseDate;
        this.directorName = directorName;
        this.posterUrl = posterUrl;
    }

    public StarWarsFilm(String url, String title, int episodeId, long releaseDate, String directorName) {
        this(url, title, episodeId, releaseDate, directorName, "");
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getUrl() {
        return url;
    }
}
