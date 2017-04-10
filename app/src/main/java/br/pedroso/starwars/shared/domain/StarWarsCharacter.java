package br.pedroso.starwars.shared.domain;

import java.util.List;

/**
 * Created by felipe on 01/03/17.
 */
public class StarWarsCharacter {
    private String url;

    private String name;

    private int height;

    private int mass;

    private String birthYear;

    private String species;

    private String homeworld;

    private List<StarWarsFilm> films;


    public StarWarsCharacter(String url, String name, int height, int mass, String birthYear, String species, String homeworld, List<StarWarsFilm> films) {
        this.url = url;
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birthYear = birthYear;
        this.species = species;
        this.homeworld = homeworld;
        this.films = films;
    }

    public StarWarsCharacter(String url, String name, int height, int mass, String birthYear, String species, String homeworld) {
        this(url, name, height, mass, birthYear, species, homeworld, null);
    }

    public StarWarsCharacter(String url, String name, int height, int mass, String birthYear) {
        this(url, name, height, mass, birthYear, "", "", null);
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

    public String getBirthYear() {
        return birthYear;
    }

    public String getSpecies() {
        return species;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<StarWarsFilm> getFilms() {
        return films;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public void setFilms(List<StarWarsFilm> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof StarWarsCharacter) {
            String anotherCharacterUrl = ((StarWarsCharacter) obj).url;
            return url.compareTo(anotherCharacterUrl) == 0;
        }

        return false;
    }
}
