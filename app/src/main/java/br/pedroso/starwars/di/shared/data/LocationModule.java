package br.pedroso.starwars.di.shared.data;

import android.content.Context;

import com.patloew.rxlocation.RxLocation;

import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.data.location.RxLocationDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 04/03/17.
 */

@Module
public class LocationModule {

    @Provides
    RxLocation provideRxLocation(Context context) {
        return new RxLocation(context);
    }

    @Provides
    LocationDataSource provideLocationDataSource(RxLocation rxLocation) {
        return new RxLocationDataSource(rxLocation);
    }
}
