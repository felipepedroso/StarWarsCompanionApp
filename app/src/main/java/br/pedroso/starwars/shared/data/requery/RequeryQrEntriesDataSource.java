package br.pedroso.starwars.shared.data.requery;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.entities.RequeryQrEntry;
import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsCharacter;
import br.pedroso.starwars.shared.data.requery.mappers.QrEntryMapper;
import br.pedroso.starwars.shared.data.requery.mappers.StarWarsCharacterMapper;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Observable;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

/**
 * Created by felipe on 03/03/17.
 */

public class RequeryQrEntriesDataSource implements QrEntriesDataSource {
    private ReactiveEntityStore<Persistable> entityStore;

    @Inject
    public RequeryQrEntriesDataSource(ReactiveEntityStore<Persistable> entityStore) {
        this.entityStore = entityStore;
    }

    @Override
    public Observable<QrEntry> insertQrEntry(QrEntry qrEntry) {
        return Observable.just(qrEntry)
                .map(QrEntryMapper::mapDomainToRequery)
                .flatMap(requeryQrEntry -> entityStore.insert(requeryQrEntry).toObservable())
                .map(QrEntryMapper::mapRequeryToDomain);
    }

    @Override
    public Observable<QrEntry> getAllQrEntries() {
        return entityStore.select(RequeryQrEntry.class)
                .get().observable()
                .map(QrEntryMapper::mapRequeryToDomain);
    }

    @Override
    public Observable<StarWarsCharacter> getCharacterByUrl(String url) {
        return entityStore.select(RequeryStarWarsCharacter.class)
                .where(RequeryStarWarsCharacter.URL.equal(url))
                .get().observable()
                .map(StarWarsCharacterMapper::mapRequeryToDomain);
    }

    @Override
    public Observable<StarWarsCharacter> insertStarWarsCharacter(StarWarsCharacter starWarsCharacter) {
        return Observable.just(starWarsCharacter)
                .map(StarWarsCharacterMapper::mapDomainToRequery)
                .flatMap(requeryStarWarsCharacter -> entityStore.insert(requeryStarWarsCharacter).toObservable())
                .map(StarWarsCharacterMapper::mapRequeryToDomain);
    }
}
