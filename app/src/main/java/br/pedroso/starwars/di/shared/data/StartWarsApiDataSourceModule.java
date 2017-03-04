package br.pedroso.starwars.di.shared.data;

import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.data.retrofit.RetrofitStarWarsApiDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 04/03/17.
 */
@Module
public class StartWarsApiDataSourceModule {
    @Provides
    StarWarsApiDataSource provideStarWarsApiDataSource() {
        return new RetrofitStarWarsApiDataSource();
    }
}
