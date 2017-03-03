package br.pedroso.starwars.shared.data.dataSources;

import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public interface StarWarsApiDataSource {
    Observable<StarWarsCharacter> getStarWarsCharacter(String url);
}
