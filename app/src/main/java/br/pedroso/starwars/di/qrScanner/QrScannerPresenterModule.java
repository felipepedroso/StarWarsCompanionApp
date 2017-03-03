package br.pedroso.starwars.di.qrScanner;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrScanner.QrScannerContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 02/03/17.
 */
@Module
public class QrScannerPresenterModule {
    private final QrScannerContract.View view;

    public QrScannerPresenterModule(QrScannerContract.View view) {
        this.view = view;
    }

    @Provides
    QrScannerContract.View provideQrEntriesView(){
        return view;
    }
}
