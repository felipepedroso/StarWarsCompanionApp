package br.pedroso.starwars.shared.data.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.patloew.rxlocation.RxLocation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.pedroso.starwars.BuildConfig;
import br.pedroso.starwars.shared.data.QrEntriesRepository;
import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.data.location.RxLocationDataSource;
import br.pedroso.starwars.shared.data.requery.RequeryQrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.entities.Models;
import br.pedroso.starwars.shared.data.retrofit.RetrofitStarWarsApiDataSource;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by felipe on 09/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class QrEntriesRepositoryTest {
    private QrEntriesRepository repository;
    private Context context;
    private StarWarsApiDataSource starWarsApiDataSource;
    private QrEntriesDataSource qrEntriesDataSource;
    private LocationDataSource locationDataSource;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();

        starWarsApiDataSource = createStarWarsApiDataSource();

        qrEntriesDataSource = createQrEntriesDataSource();

        locationDataSource = createLocationDataSource();

        repository = new QrEntriesRepositoryImpl(qrEntriesDataSource, locationDataSource, starWarsApiDataSource);
    }

    private LocationDataSource createLocationDataSource() {
        RxLocation rxLocation = new RxLocation(context);

        LocationDataSource locationDataSource = new RxLocationDataSource(rxLocation);

        return locationDataSource;
    }

    private QrEntriesDataSource createQrEntriesDataSource() {
        DatabaseSource databaseSource = new DatabaseSource(context, Models.DEFAULT, 1);

        if (BuildConfig.DEBUG) {
            databaseSource.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }

        Configuration configuration = databaseSource.getConfiguration();

        ReactiveEntityStore<Persistable> entityStore = ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));

        return new RequeryQrEntriesDataSource(entityStore);
    }

    private StarWarsApiDataSource createStarWarsApiDataSource() {
        return new RetrofitStarWarsApiDataSource();
    }

    @After
    public void tearDown() {
        cleanQrEntries();
    }

    private void cleanQrEntries() {
        repository.deleteAllQrEntries().blockingSubscribe();
    }

    @Test
    public void testInsert() {
        cleanQrEntries();

        String characterUrl = "Fake Url";

        Observable<QrEntry> insertNewQrEntryObservable = repository.insertNewQrEntry(characterUrl);

        insertNewQrEntryObservable.test()
                .assertNoErrors();
    }
}
