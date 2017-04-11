package br.pedroso.starwars.shared.data.retrofit.services;

import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsCharacter;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsFilm;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsPlanet;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsSpecies;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by felipe on 10/04/17.
 */

public interface StarWarsApiService {
    String BASE_URL = "https://swapi.co/api/";
    String API_DATE_FORMAT = "yyyy-MM-dd";

    @GET
    Observable<RetrofitStarWarsCharacter> getCharacterByUrl(@Url String url);

    @GET
    Observable<RetrofitStarWarsPlanet> getStarWarsPlanetByUrl(@Url String url);

    @GET
    Observable<RetrofitStarWarsFilm> getStarWarsFilms(@Url String url);

    @GET
    Observable<RetrofitStarWarsSpecies> getStarWarsSpecies(@Url String url);
}
