package br.pedroso.starwars.shared.data.retrofit.entities;

/**
 * Created by felipe on 10/04/17.
 */

public class RetrofitStarWarsPlanet {
    private String url;

    private String name;

    public RetrofitStarWarsPlanet(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
