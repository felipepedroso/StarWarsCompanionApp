package br.pedroso.starwars.shared.data.requery.entities;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToOne;

/**
 * Created by felipe on 04/03/17.
 */
@Entity
public abstract class AbstractRequeryQrEntryLocation {
    @Key @Generated
    long id;

    double latitude;

    double longitude;

    @OneToOne
    RequeryQrEntry qrEntry;
}
