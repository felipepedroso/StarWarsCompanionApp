package br.pedroso.starwars.shared.data.requery;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.entities.RequeryQrEntry;
import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsCharacter;
import br.pedroso.starwars.shared.data.requery.mappers.QrEntryMapper;
import br.pedroso.starwars.shared.data.requery.mappers.StarWarsCharacterMapper;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.requery.Persistable;
import io.requery.query.Conditional;
import io.requery.reactivex.ReactiveEntityStore;

import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;

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
    public Observable<QrEntry> insertQrEntry(QrEntry... qrEntry) {
        return Observable.fromArray(qrEntry)
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
    public Observable<StarWarsCharacter> getAllCharacters() {
        return entityStore.select(RequeryStarWarsCharacter.class)
                .get().observable()
                .map(StarWarsCharacterMapper::mapRequeryToDomain);
    }

    @Override
    public Observable<StarWarsCharacter> insertStarWarsCharacter(StarWarsCharacter... starWarsCharacter) {
        return Observable.fromArray(starWarsCharacter)
                .map(StarWarsCharacterMapper::mapDomainToRequery)
                .flatMap(requeryStarWarsCharacter -> entityStore.insert(requeryStarWarsCharacter).toObservable())
                .map(StarWarsCharacterMapper::mapRequeryToDomain)
                .doOnNext(starWarsCharacter1 -> Log.d("TestTomato",starWarsCharacter1.getUrl()));
    }

    @Override
    public Observable<Integer> deleteAllQrEntries() {
        return entityStore.delete(RequeryQrEntry.class).get().single().toObservable();
    }

    @Override
    public Completable deleteQrEntries(QrEntry... qrEntries) {
        return Observable.fromArray(qrEntries)
                .map(QrEntryMapper::mapDomainToRequery)
                .toList()
                .flatMapCompletable(requeryQrEntries -> entityStore.delete(requeryQrEntries));
    }

    @Override
    public Observable<Integer> deleteAllStarWarsCharacters() {
        return entityStore.delete(RequeryStarWarsCharacter.class).get().single().toObservable();
    }

    @Override
    public Completable deleteStarWarsCharacters(StarWarsCharacter... starWarsCharacters) {
        return Observable.fromArray(starWarsCharacters)
                .map(StarWarsCharacterMapper::mapDomainToRequery)
                .toList()
                .flatMapCompletable(requeryStarWarsCharacters -> entityStore.delete(requeryStarWarsCharacters));
    }
}
