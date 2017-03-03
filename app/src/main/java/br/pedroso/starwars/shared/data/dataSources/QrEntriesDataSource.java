package br.pedroso.starwars.shared.data.dataSources;

import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public interface QrEntriesDataSource {
    Observable<QrEntry> insertQrEntry(QrEntry qrEntry);

    Observable<QrEntry> getAllQrEntries();

    Observable<StarWarsCharacter> getCharacterByUrl(String url);

    Observable<StarWarsCharacter> insertStarWarsCharacter(StarWarsCharacter starWarsCharacter);
}
