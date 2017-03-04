package br.pedroso.starwars.di.shared.data;

import javax.inject.Singleton;

import br.pedroso.starwars.shared.data.QrEntriesRepository;
import br.pedroso.starwars.shared.data.dataSources.LocationDataSource;
import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.data.repository.QrEntriesRepositoryImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 04/03/17.
 */
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    QrEntriesRepository provideQrEntriesRepository(QrEntriesDataSource qrEntriesDataSource, LocationDataSource locationDataSource, StarWarsApiDataSource starWarsApiDataSource) {
        return new QrEntriesRepositoryImpl(qrEntriesDataSource, locationDataSource, starWarsApiDataSource);
    }
}
