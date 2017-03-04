package br.pedroso.starwars.qrEntries.usecases;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.QrEntriesRepository;
import br.pedroso.starwars.shared.domain.QrEntry;
import io.reactivex.Observable;

/**
 * Created by felipe on 04/03/17.
 */

public class GetAllQrEntries {
    private QrEntriesRepository repository;

    @Inject
    public GetAllQrEntries(QrEntriesRepository repository) {
        this.repository = repository;
    }

    public Observable<QrEntry> execute() {
        return repository.getAllQrEntries();
    }
}
