package br.pedroso.starwars.di.qrEntries;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.usecases.GetAllQrEntries;
import br.pedroso.starwars.qrEntries.usecases.InsertQrEntry;
import br.pedroso.starwars.shared.data.QrEntriesRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by felipe on 02/03/17.
 */
@Module
public class QrEntriesPresenterModule {
    private final QrEntriesContract.View view;

    public QrEntriesPresenterModule(QrEntriesContract.View view) {
        this.view = view;
    }

    @Provides
    QrEntriesContract.View provideQrEntriesView() {
        return view;
    }

    @Provides
    GetAllQrEntries provideGetAllQrEntriesUsecase(QrEntriesRepository repository) {
        return new GetAllQrEntries(repository);
    }

    @Provides
    InsertQrEntry provideInsertQrEntryUsecase(QrEntriesRepository repository){
        return new InsertQrEntry(repository);
    }
}
