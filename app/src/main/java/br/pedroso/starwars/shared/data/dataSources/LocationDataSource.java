package br.pedroso.starwars.shared.data.dataSources;

import br.pedroso.starwars.shared.domain.QrEntryLocation;
import io.reactivex.Observable;

/**
 * Created by felipe on 03/03/17.
 */

public interface LocationDataSource {
    Observable<QrEntryLocation> getLocation();
}
