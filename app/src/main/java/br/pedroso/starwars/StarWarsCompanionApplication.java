package br.pedroso.starwars;

import android.app.Application;

import br.pedroso.starwars.di.application.ApplicationComponent;
import br.pedroso.starwars.di.application.ApplicationModule;
import br.pedroso.starwars.di.application.DaggerApplicationComponent;

/**
 * Created by felipe on 02/03/17.
 */
public class StarWarsCompanionApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        createApplicationComponent();
    }

    private void createApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
