package br.pedroso.starwars.shared.data;

import br.pedroso.starwars.shared.domain.QrEntry;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public interface QrEntriesRepository {
    Observable<QrEntry> getAllQrEntries();

    Observable<QrEntry> insertNewQrEntry(String url);
}
