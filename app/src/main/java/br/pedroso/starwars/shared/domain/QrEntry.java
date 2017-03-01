package br.pedroso.starwars.shared.domain;

/**
 * Created by felipe on 01/03/17.
 */

public class QrEntry {
    private String url;

    private StarWarsCharacter character;

    private long registeredDate;

    private double latitude;

    private double longitude;

    public QrEntry(String url, long registeredDate){
        this.url = url;
        this.registeredDate = registeredDate;
    }

    public void setCharacter(StarWarsCharacter character) {
        this.character = character;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public StarWarsCharacter getCharacter() {
        return character;
    }

    public long getRegisteredDate() {
        return registeredDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
