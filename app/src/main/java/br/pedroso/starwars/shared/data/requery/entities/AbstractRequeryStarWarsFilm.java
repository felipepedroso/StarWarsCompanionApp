package br.pedroso.starwars.shared.data.requery.entities;

import java.util.List;

import io.requery.Entity;
import io.requery.Key;
import io.requery.ManyToMany;
import io.requery.ManyToOne;

/**
 * Created by felipe on 04/03/17.
 */
@Entity
public class AbstractRequeryStarWarsFilm {
    @Key
    String url;

    String title;

    int episodeId;

    long releaseDate;

    String directorName;

    String posterUrl;

    @ManyToMany
    List<RequeryStarWarsCharacter> characters;
}
