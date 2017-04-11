package br.pedroso.starwars.shared.data.retrofit;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.pedroso.starwars.shared.data.dataSources.StarWarsApiDataSource;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.observers.TestObserver;

/**
 * Created by felipe on 07/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class RetrofitStarWarsApiDataSourceTest {
    private StarWarsApiDataSource starWarsApiDataSource;

    @Before
    public void setup(){
        createStarWarsApiDataSource();
    }

    private void createStarWarsApiDataSource() {
//        starWarsApiDataSource = new RetrofitStarWarsApiDataSource();
    }

    @Test
    public void testGetCharacterByUrl(){
//        String url = "Fake Url";
//
//        TestObserver<StarWarsCharacter> getStarWarsCharacterByUrlTestObserver = starWarsApiDataSource.getStarWarsCharacter(url).test();
//
//        getStarWarsCharacterByUrlTestObserver.assertNoErrors()
//                .assertValue(starWarsCharacter -> starWarsCharacter.getUrl().compareTo(url) == 0);
    }
}
