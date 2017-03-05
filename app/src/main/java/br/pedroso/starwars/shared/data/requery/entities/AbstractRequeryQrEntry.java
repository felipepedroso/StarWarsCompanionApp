package br.pedroso.starwars.shared.data.requery.entities;

import io.requery.Entity;
import io.requery.ForeignKey;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.OneToOne;

/**
 * Created by felipe on 04/03/17.
 */

@Entity
public abstract class AbstractRequeryQrEntry {
    @ManyToOne
    @ForeignKey
    RequeryStarWarsCharacter character;

    @Key
    long registeredDate;

    @OneToOne
    @ForeignKey
    RequeryQrEntryLocation location;
}
