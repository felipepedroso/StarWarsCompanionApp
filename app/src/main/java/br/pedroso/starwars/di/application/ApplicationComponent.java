package br.pedroso.starwars.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by felipe on 02/03/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context applicationContext();
}
