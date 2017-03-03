package br.pedroso.starwars.shared.data.requery;

import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public class RequeryQrEntriesDataSource implements QrEntriesDataSource {

    @Override
    public Observable<QrEntry> insertQrEntry(QrEntry qrEntry) {
        return null;
    }

    @Override
    public Observable<QrEntry> getAllQrEntries() {
        int entriesCount = 50;

        QrEntry[] qrEntries = new QrEntry[entriesCount];

        for (int i = 0; i < entriesCount; i++) {
            StarWarsCharacter character = new StarWarsCharacter("Fake URL " + i, "Character " + i, 172, 72, "YHASB");
            QrEntry qrEntry = new QrEntry(character, System.currentTimeMillis() - (i * 60000), new QrEntryLocation(0, 0));
            qrEntries[i] = qrEntry;
        }

        return Observable.fromArray(qrEntries);
    }

    @Override
    public Observable<StarWarsCharacter> getCharacterByUrl(String url) {
        return null;
    }

    @Override
    public Observable<StarWarsCharacter> insertStarWarsCharacter(StarWarsCharacter starWarsCharacter) {
        return null;
    }
}
