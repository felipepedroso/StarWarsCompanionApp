package br.pedroso.starwars.di.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by felipe on 02/03/17.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivitiesScope {
}
