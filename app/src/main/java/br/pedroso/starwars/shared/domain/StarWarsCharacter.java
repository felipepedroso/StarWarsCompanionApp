package br.pedroso.starwars.shared.domain;

import java.util.List;

/**
 * Created by felipe on 01/03/17.
 */
public class StarWarsCharacter {
    private String name;

    private int height;

    private int mass;

    private String species;

    private List<StarWarsFilm> films;

    private String homeworld;

    private String birthYear;

    public StarWarsCharacter(String name, int height, int mass, String species, String homeworld, String birthYear) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.species = species;
        this.homeworld = homeworld;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getMass() {
        return mass;
    }

    public String getSpecies() {
        return species;
    }

    public List<StarWarsFilm> getFilms() {
        return films;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public String getBirthYear() {
        return birthYear;
    }
}
