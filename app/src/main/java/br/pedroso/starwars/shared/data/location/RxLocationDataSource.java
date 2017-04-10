package br.pedroso.starwars.shared.data.location;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.data.location.mappers.QrEntryLocationMapper;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public class RxLocationDataSource implements LocationDataSource {

    private final LocationRequest locationRequest;
    private RxLocation rxLocation;

    @Inject
    public RxLocationDataSource(RxLocation rxLocation) {
        this.rxLocation = rxLocation;

        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1);
    }

    @Override
    public Observable<QrEntryLocation> getLocation() {
        return rxLocation.settings()
                .checkAndHandleResolution(locationRequest)
                .flatMapObservable(this::getLocationObservable)
                .map(QrEntryLocationMapper::mapAndroidLocationToQrEntryLocation);
    }

    private Observable<Location> getLocationObservable(Boolean hasLocationAvailable) {
        Observable<Location> locationObservable;

        if(hasLocationAvailable) {
            locationObservable = rxLocation.location().updates(locationRequest);
        } else {
            locationObservable =  rxLocation.location().lastLocation().toObservable();
        }

        return locationObservable;
    }
}
