package br.pedroso.starwars.shared.data.location;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.patloew.rxlocation.RxLocation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import io.reactivex.Observable;

/**
 * Created by felipe on 07/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class LocationDataSourceTest {

    private static final double FAKE_LATITUDE = 25.7;
    private static final double FAKE_LONGITUDE = 23.1;

    private LocationDataSource locationDataSource;

    @Before
    public void setup() {
        createLocationDataSource();
    }

    private void createLocationDataSource() {
        Context context = InstrumentationRegistry.getTargetContext();

        RxLocation rxLocation = new RxLocation(context);

        locationDataSource = new RxLocationDataSource(rxLocation);
    }

    @Test
    public void testGetLastKnowLocation() {
        Observable<QrEntryLocation> getLocationObservable = locationDataSource.getLocation();

        getLocationObservable.test()
                .assertNoErrors()
                .assertValueCount(1);
    }
}
