package br.pedroso.starwars.shared.data.retrofit.entities;

/**
 * Created by felipe on 10/04/17.
 */

public class RetrofitStarWarsFilm {
    private String url;

    private String title;

    private int episode_id;

    private String release_date;

    private String director;

    public RetrofitStarWarsFilm(String url, String title, int episode_id, String release_date, String director) {
        this.url = url;
        this.title = title;
        this.episode_id = episode_id;
        this.release_date = release_date;
        this.director = director;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getDirector() {
        return director;
    }
}
