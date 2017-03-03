package br.pedroso.starwars.shared.data.location;

import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public class LocationDataSourceImpl implements LocationDataSource {
    @Override
    public Observable<QrEntryLocation> getLastKnowLocation() {
        return Observable.just(new QrEntryLocation(0, 0));
    }
}
