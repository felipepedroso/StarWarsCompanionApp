package br.pedroso.starwars.shared.data.retrofit.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsFilm;
import br.pedroso.starwars.shared.data.retrofit.services.StarWarsApiService;
import br.pedroso.starwars.shared.domain.StarWarsFilm;

/**
 * Created by felipe on 10/04/17.
 */

public class StarWarsFilmMapper {


    public static StarWarsFilm mapRetrofitToDomain(RetrofitStarWarsFilm retrofitStarWarsFilm) {
        String url = retrofitStarWarsFilm.getUrl();

        String title = retrofitStarWarsFilm.getTitle();

        int episodeId = retrofitStarWarsFilm.getEpisode_id();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StarWarsApiService.API_DATE_FORMAT);

        String releaseDateStr = retrofitStarWarsFilm.getRelease_date();

        long releaseDate = 0;

        try {
            releaseDate = simpleDateFormat.parse(releaseDateStr).getTime();
        } catch (ParseException e) {
            releaseDate = -1; // TODO: improve the way to handle this condition
        }

        String directorName = retrofitStarWarsFilm.getDirector();

        return new StarWarsFilm(url, title, episodeId, releaseDate, directorName);
    }
}
