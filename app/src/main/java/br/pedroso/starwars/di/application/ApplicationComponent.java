package br.pedroso.starwars.di.application;

import javax.inject.Singleton;

import br.pedroso.starwars.di.shared.data.LocationModule;
import br.pedroso.starwars.di.shared.data.QrEntriesDatasourceModule;
import br.pedroso.starwars.di.shared.data.RepositoryModule;
import br.pedroso.starwars.di.shared.data.StartWarsApiDataSourceModule;
import br.pedroso.starwars.shared.data.QrEntriesRepository;
import dagger.Component;

/**
 * Created by felipe on 02/03/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class, LocationModule.class, StartWarsApiDataSourceModule.class, QrEntriesDatasourceModule.class})
public interface ApplicationComponent {
    QrEntriesRepository qrEntriesRepository();
}
