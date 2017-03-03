package br.pedroso.starwars.di.qrScanner;

import br.pedroso.starwars.di.scopes.ActivitiesScope;
import br.pedroso.starwars.qrScanner.ui.QrScannerActivity;
import dagger.Component;

/**
 * Created by felipe on 02/03/17.
 */
@ActivitiesScope
@Component(modules = QrScannerPresenterModule.class)
public interface QrScannerComponent {
    void inject(QrScannerActivity activity);
}
