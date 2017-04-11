package br.pedroso.starwars.shared.data.retrofit;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsCharacter;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsPlanet;
import br.pedroso.starwars.shared.data.retrofit.entities.RetrofitStarWarsSpecies;
import br.pedroso.starwars.shared.data.retrofit.mappers.StarWarsFilmMapper;
import br.pedroso.starwars.shared.data.retrofit.services.StarWarsApiService;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import br.pedroso.starwars.shared.domain.StarWarsFilm;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public class RetrofitStarWarsApiDataSource implements StarWarsApiDataSource {

    private StarWarsApiService starWarsApiService;

    @Inject
    public RetrofitStarWarsApiDataSource(StarWarsApiService starWarsApiService) {
        this.starWarsApiService = starWarsApiService;
    }

    @Override
    public Observable<StarWarsCharacter> getStarWarsCharacter(String url) {
        return starWarsApiService.getCharacterByUrl(url)
                .flatMap(this::composeStarwarsCharacterObservable);
    }

    private Observable<StarWarsCharacter> composeStarwarsCharacterObservable(RetrofitStarWarsCharacter retrofitStarWarsCharacter) {
        return Observable.zip(
                Observable.just(retrofitStarWarsCharacter),
                getCharacterFilmsObservable(retrofitStarWarsCharacter.getFilms()),
                getCharacterHomeworldObservable(retrofitStarWarsCharacter.getHomeworld()),
                getCharacterSpeciesObservable(retrofitStarWarsCharacter.getSpecies()),
                this::getStarWarsCharacterZipFunction);
    }

    private StarWarsCharacter getStarWarsCharacterZipFunction(RetrofitStarWarsCharacter retrofitStarWarsCharacter, List<StarWarsFilm> characterFilms, String homeworld, String species) {
        String url = retrofitStarWarsCharacter.getUrl();

        String name = retrofitStarWarsCharacter.getName();

        int height = retrofitStarWarsCharacter.getHeight();

        int mass = retrofitStarWarsCharacter.getMass();

        String birthYear = retrofitStarWarsCharacter.getBirth_year();

        return new StarWarsCharacter(url, name, height, mass, birthYear, species, homeworld, characterFilms);
    }

    private Observable<String> getCharacterSpeciesObservable(List<String> speciesUrls) {
        return Observable.fromIterable(speciesUrls)
                .flatMap(starWarsApiService::getStarWarsSpecies)
                .map(RetrofitStarWarsSpecies::getName)
                .reduce((species1Name, species2Name) -> species1Name + " / " + species2Name).toObservable();
    }

    private Observable<String> getCharacterHomeworldObservable(String homeworldUrl) {
        return Observable.just(homeworldUrl)
                .flatMap(starWarsApiService::getStarWarsPlanetByUrl)
                .map(RetrofitStarWarsPlanet::getName);
    }

    private Observable<List<StarWarsFilm>> getCharacterFilmsObservable(List<String> filmsUrls) {
        return Observable.fromIterable(filmsUrls)
                .flatMap(starWarsApiService::getStarWarsFilms)
                .map(StarWarsFilmMapper::mapRetrofitToDomain)
                .toList().toObservable();
    }


}
