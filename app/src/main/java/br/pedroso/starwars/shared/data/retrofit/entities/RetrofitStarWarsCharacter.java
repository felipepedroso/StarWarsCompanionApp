package br.pedroso.starwars.shared.data.retrofit.entities;

import java.util.List;

import br.pedroso.starwars.shared.domain.StarWarsFilm;

/**
 * Created by felipe on 10/04/17.
 */

public class RetrofitStarWarsCharacter {
    private String url;

    private String name;

    private int height;

    private int mass;

    private String birth_year;

    private List<String> species;

    private String homeworld;

    private List<String> films;

    public RetrofitStarWarsCharacter(String url, String name, int height, int mass, String birth_year, List<String> species, String homeworld, List<String> films) {
        this.url = url;
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birth_year = birth_year;
        this.species = species;
        this.homeworld = homeworld;
        this.films = films;
    }


    public String getUrl() {
        return url;
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

    public String getBirth_year() {
        return birth_year;
    }

    public List<String> getSpecies() {
        return species;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<String> getFilms() {
        return films;
    }
}
