package br.pedroso.starwars.shared.data.repository;

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
                .flatMap(starWarsCharacter -> qrEntriesDataSource.insertStarWarsCharacter(starWarsCharacter));

        Observable<StarWarsCharacter> observableCharacter = qrEntriesDataSource
                .getCharacterByUrl(url)
                .onErrorResumeNext(starWarsApiObservable);

        Observable<QrEntryLocation> observableLocation = locationDataSource.getLastKnowLocation();

        return Observable.zip(observableCharacter,
                observableLocation,
                (starWarsCharacter, qrEntryLocation) -> new QrEntry(starWarsCharacter, timestampNow, qrEntryLocation))
                .flatMap(qrEntry -> qrEntriesDataSource.insertQrEntry(qrEntry))
                .subscribeOn(Schedulers.io());
    }
}
