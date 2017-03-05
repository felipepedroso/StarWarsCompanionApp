package br.pedroso.starwars.di.shared.data;

import android.content.Context;

import javax.inject.Singleton;

import br.pedroso.starwars.BuildConfig;
import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.RequeryQrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.entities.Models;
import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by felipe on 04/03/17.
 */
@Module
public class QrEntriesDatasourceModule {
    private static final int DATABASE_VERSION = 1;

    @Provides
    @Singleton
    DatabaseSource provideDatabaseSource(Context context) {
        DatabaseSource databaseSource = new DatabaseSource(context, Models.DEFAULT, DATABASE_VERSION);

        if (BuildConfig.DEBUG) {
            databaseSource.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }

        return databaseSource;
    }


    @Singleton
    @Provides
    ReactiveEntityStore<Persistable> provideEntityStore(DatabaseSource databaseSource) {
        Configuration configuration = databaseSource.getConfiguration();

        return ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));
    }

    @Singleton
    @Provides
    QrEntriesDataSource provideQrEntryDataSource(ReactiveEntityStore<Persistable> entityStore) {
        return new RequeryQrEntriesDataSource(entityStore);
    }
}
