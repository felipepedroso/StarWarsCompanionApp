package br.pedroso.starwars.shared.data.location.mappers;

import android.location.Location;

import br.pedroso.starwars.shared.domain.QrEntryLocation;

/**
 * Created by felipe on 10/04/17.
 */

public class QrEntryLocationMapper {
    public static QrEntryLocation mapAndroidLocationToQrEntryLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        return new QrEntryLocation(latitude, longitude);
    }
}
