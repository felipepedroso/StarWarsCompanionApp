package br.pedroso.starwars.shared.data.requery.entities;

import java.util.List;

import io.requery.Entity;
import io.requery.JunctionTable;
import io.requery.Key;
import io.requery.ManyToMany;
import io.requery.OneToMany;

/**
 * Created by felipe on 04/03/17.
 */
@Entity
public class AbstractRequeryStarWarsCharacter {
    @Key
    String url;

    String name;

    int height;

    int mass;

    String species;

    @JunctionTable(name = "characters_movies")
    @ManyToMany
    List<RequeryStarWarsFilm> films;

    String homeworld;

    String birthYear;

    @OneToMany(mappedBy = "character")
    List<RequeryQrEntry> qrEntries;
}
