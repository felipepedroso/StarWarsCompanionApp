package br.pedroso.starwars.di.shared.data;

import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.data.retrofit.RetrofitStarWarsApiDataSource;
import br.pedroso.starwars.shared.data.retrofit.services.StarWarsApiService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felipe on 04/03/17.
 */
@Module
public class StarWarsApiDataSourceModule {
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(StarWarsApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    StarWarsApiService provideStarWarsApiService(Retrofit retrofit) {
        return retrofit.create(StarWarsApiService.class);
    }

    @Provides
    StarWarsApiDataSource provideStarWarsApiDataSource(StarWarsApiService starWarsApiService) {
        return new RetrofitStarWarsApiDataSource(starWarsApiService);
    }
}
