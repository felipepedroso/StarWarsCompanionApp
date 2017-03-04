package br.pedroso.starwars.shared.data.location;

import android.location.Location;

import com.patloew.rxlocation.RxLocation;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by felipe on 03/03/17.
 */

public class RxLocationDataSource implements LocationDataSource {

    private RxLocation rxLocation;

    @Inject
    public RxLocationDataSource(RxLocation rxLocation) {
        this.rxLocation = rxLocation;
    }

    public RxLocationDataSource() {
        this(null);
    }

    @Override
    public Observable<QrEntryLocation> getLastKnowLocation() {
        Observable<Location> lastKnowLocationObservable;
        try {
            // TODO: understand 'Maybe' a little bit more and refactor this.
            lastKnowLocationObservable = rxLocation.location()
                    .lastLocation()
                    .toObservable();

        } catch (SecurityException ex) {
            // TODO: this is a little bit ugly, we also need to refactor this.
            lastKnowLocationObservable = Observable.create(new ObservableOnSubscribe<Location>() {
                @Override
                public void subscribe(ObservableEmitter<Location> e) throws Exception {
                    e.onError(ex);
                }
            });
        }

        return lastKnowLocationObservable.map(new AndroidLocationToQrEntryLocationMapper());
    }

    private class AndroidLocationToQrEntryLocationMapper implements Function<Location, QrEntryLocation> {

        @Override
        public QrEntryLocation apply(@NonNull Location location) throws Exception {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            return new QrEntryLocation(latitude, longitude);
        }
    }
}
