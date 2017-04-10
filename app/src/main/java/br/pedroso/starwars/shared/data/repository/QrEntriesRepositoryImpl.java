package br.pedroso.starwars.shared.data.repository;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.QrEntriesRepository;
import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felipe on 03/03/17.
 */
public class QrEntriesRepositoryImpl implements QrEntriesRepository {

    private QrEntriesDataSource qrEntriesDataSource;
    private LocationDataSource locationDataSource;
    private StarWarsApiDataSource starWarsApiDataSource;

    @Inject
    public QrEntriesRepositoryImpl(QrEntriesDataSource qrEntriesDataSource, LocationDataSource locationDataSource, StarWarsApiDataSource starWarsApiDataSource) {
        this.qrEntriesDataSource = qrEntriesDataSource;
        this.locationDataSource = locationDataSource;
        this.starWarsApiDataSource = starWarsApiDataSource;
    }

    @Override
    public Observable<QrEntry> getAllQrEntries() {
        return qrEntriesDataSource.getAllQrEntries();
    }

    @Override
    public Observable<QrEntry> insertNewQrEntry(String url) {
        final long timestampNow = System.currentTimeMillis();

        Observable<StarWarsCharacter> starWarsApiObservable = starWarsApiDataSource.getStarWarsCharacter(url)
                .doOnNext(starWarsCharacter -> qrEntriesDataSource.insertStarWarsCharacter(starWarsCharacter));

        Observable<StarWarsCharacter> observableCharacter = qrEntriesDataSource
                .getCharacterByUrl(url)
                .switchIfEmpty(starWarsApiObservable);

        Observable<QrEntryLocation> observableLocation = locationDataSource.getLocation();

        return Observable.zip(observableCharacter,
                observableLocation,
                (starWarsCharacter, qrEntryLocation) -> new QrEntry(starWarsCharacter, timestampNow, qrEntryLocation))
                .flatMap(qrEntry -> qrEntriesDataSource.insertQrEntry(qrEntry))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Integer> deleteAllQrEntries() {
        return qrEntriesDataSource.deleteAllQrEntries();
    }
}
