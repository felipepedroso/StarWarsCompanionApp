package br.pedroso.starwars.di.shared.data;

import javax.inject.Singleton;

import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.RequeryQrEntriesDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 04/03/17.
 */
@Module
public class QrEntriesDatasourceModule {

    @Singleton
    @Provides
    QrEntriesDataSource provideQrEntryDataSource(){
        return new RequeryQrEntriesDataSource();
    }
}
