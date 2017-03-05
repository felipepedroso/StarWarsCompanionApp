package br.pedroso.starwars.qrEntries.usecases;

import javax.inject.Inject;

import br.pedroso.starwars.shared.data.QrEntriesRepository;
import br.pedroso.starwars.shared.domain.QrEntry;
import io.reactivex.Observable;

/**
 * Created by felipe on 05/03/17.
 */

public class InsertQrEntry {
    private QrEntriesRepository repository;

    @Inject
    public InsertQrEntry(QrEntriesRepository repository) {
        this.repository = repository;
    }

    public Observable<QrEntry> execute(String url){
        return repository.insertNewQrEntry(url);
    }
}
