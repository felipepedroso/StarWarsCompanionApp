package br.pedroso.starwars.shared.data.requery.mappers;

import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsFilm;
import br.pedroso.starwars.shared.domain.StarWarsFilm;

/**
 * Created by felipe on 05/03/17.
 */

public class StarWarsFilmMapper {

    public static RequeryStarWarsFilm mapDomainToRequery(StarWarsFilm starWarsFilm) {
        String url = starWarsFilm.getUrl();
        String directorName = starWarsFilm.getDirectorName();
        int episodeId = starWarsFilm.getEpisodeId();
        String posterUrl = starWarsFilm.getPosterUrl();
        long releaseDate = starWarsFilm.getReleaseDate();
        String title = starWarsFilm.getTitle();

        RequeryStarWarsFilm requeryStarWarsFilm = new RequeryStarWarsFilm();

        requeryStarWarsFilm.setUrl(url);
        requeryStarWarsFilm.setDirectorName(directorName);
        requeryStarWarsFilm.setEpisodeId(episodeId);
        requeryStarWarsFilm.setPosterUrl(posterUrl);
        requeryStarWarsFilm.setReleaseDate(releaseDate);
        requeryStarWarsFilm.setTitle(title);

        return requeryStarWarsFilm;
    }

    public static StarWarsFilm mapRequeryToDomain(RequeryStarWarsFilm requeryStarWarsFilm) {
        String url = requeryStarWarsFilm.getUrl();

        String directorName = requeryStarWarsFilm.getDirectorName();

        int episodeId = requeryStarWarsFilm.getEpisodeId();

        String posterUrl = requeryStarWarsFilm.getPosterUrl();

        long releaseDate = requeryStarWarsFilm.getReleaseDate();

        String title = requeryStarWarsFilm.getTitle();

        return new StarWarsFilm(url, title, episodeId, releaseDate, directorName, posterUrl);
    }

}
