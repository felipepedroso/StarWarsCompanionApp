package br.pedroso.starwars.shared.data.retrofit;

import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public class RetrofitStarWarsApiDataSource implements StarWarsApiDataSource {
    @Override
    public Observable<StarWarsCharacter> getStarWarsCharacter(String url) {
        return Observable.just(new StarWarsCharacter("Fake Url", "Fake Character", 172, 73, ""));
    }
}
