package br.pedroso.starwars.di.qrEntries;

import br.pedroso.starwars.di.scopes.ActivitiesScope;
import br.pedroso.starwars.qrEntries.ui.QrEntriesActivity;
import dagger.Component;

/**
 * Created by felipe on 02/03/17.
 */
@ActivitiesScope
@Component(modules = QrEntriesPresenterModule.class)
public interface QrEntriesComponent {
    void inject(QrEntriesActivity activity);
}
