package br.pedroso.starwars.shared.domain;

/**
 * Created by felipe on 03/03/17.
 */

public class QrEntryLocation {
    private double latitude;

    private double longitude;

    public QrEntryLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
