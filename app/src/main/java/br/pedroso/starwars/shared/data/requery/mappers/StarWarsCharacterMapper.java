package br.pedroso.starwars.shared.data.requery.mappers;

import java.util.ArrayList;
import java.util.List;

import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsCharacter;
import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsFilm;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import br.pedroso.starwars.shared.domain.StarWarsFilm;

/**
 * Created by felipe on 05/03/17.
 */

public class StarWarsCharacterMapper {
    public static RequeryStarWarsCharacter mapDomainToRequery(StarWarsCharacter starWarsCharacter) {
        String birthYear = starWarsCharacter.getBirthYear();
        int height = starWarsCharacter.getHeight();
        String homeworld = starWarsCharacter.getHomeworld();
        int mass = starWarsCharacter.getMass();
        String name = starWarsCharacter.getName();
        String species = starWarsCharacter.getSpecies();
        String url = starWarsCharacter.getUrl();

        RequeryStarWarsCharacter requeryStarWarsCharacter = new RequeryStarWarsCharacter();

        requeryStarWarsCharacter.setBirthYear(birthYear);
        requeryStarWarsCharacter.setHeight(height);
        requeryStarWarsCharacter.setHomeworld(homeworld);
        requeryStarWarsCharacter.setMass(mass);
        requeryStarWarsCharacter.setName(name);
        requeryStarWarsCharacter.setSpecies(species);
        requeryStarWarsCharacter.setUrl(url);

        if(starWarsCharacter.getFilms() != null) {
            for (StarWarsFilm starWarsFilm : starWarsCharacter.getFilms()) {
                RequeryStarWarsFilm requeryStarWarsFilm = StarWarsFilmMapper.mapDomainToRequery(starWarsFilm);
                requeryStarWarsCharacter.getFilms().add(requeryStarWarsFilm);
            }
        }

        return requeryStarWarsCharacter;
    }

    public static StarWarsCharacter mapRequeryToDomain(RequeryStarWarsCharacter requeryStarWarsCharacter) {
        String birthYear = requeryStarWarsCharacter.getBirthYear();
        int height = requeryStarWarsCharacter.getHeight();
        String homeworld = requeryStarWarsCharacter.getHomeworld();
        int mass = requeryStarWarsCharacter.getMass();
        String name = requeryStarWarsCharacter.getName();
        String species = requeryStarWarsCharacter.getSpecies();
        String url = requeryStarWarsCharacter.getUrl();

        List<StarWarsFilm> films = new ArrayList<>();

        for (RequeryStarWarsFilm requeryStarWarsFilm : requeryStarWarsCharacter.getFilms()) {
            StarWarsFilm starWarsFilm = StarWarsFilmMapper.mapRequeryToDomain(requeryStarWarsFilm);

            films.add(starWarsFilm);
        }

        return new StarWarsCharacter(url, name, height, mass, birthYear, species, homeworld, films);
    }

}
