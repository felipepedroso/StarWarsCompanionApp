package br.pedroso.starwars.shared.data.requery.mappers;

import br.pedroso.starwars.shared.data.requery.entities.RequeryQrEntryLocation;
import br.pedroso.starwars.shared.domain.QrEntryLocation;

/**
 * Created by felipe on 05/03/17.
 */
public class QrEntryLocationMapper {
    public static RequeryQrEntryLocation mapDomainToRequery(QrEntryLocation qrEntryLocation) {
        double latitude = qrEntryLocation.getLatitude();
        double longitude = qrEntryLocation.getLongitude();

        RequeryQrEntryLocation requeryQrEntryLocation = new RequeryQrEntryLocation();

        requeryQrEntryLocation.setLatitude(latitude);
        requeryQrEntryLocation.setLongitude(longitude);

        return requeryQrEntryLocation;
    }

    public static QrEntryLocation mapRequeryToDomain(RequeryQrEntryLocation requeryQrEntryLocation) {
        double latitude = requeryQrEntryLocation.getLatitude();
        double longitude = requeryQrEntryLocation.getLongitude();

        return new QrEntryLocation(latitude, longitude);
    }
}
