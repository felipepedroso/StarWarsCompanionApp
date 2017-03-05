package br.pedroso.starwars.shared.data.requery.mappers;

import br.pedroso.starwars.shared.data.requery.entities.RequeryQrEntry;
import br.pedroso.starwars.shared.data.requery.entities.RequeryQrEntryLocation;
import br.pedroso.starwars.shared.data.requery.entities.RequeryStarWarsCharacter;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;

/**
 * Created by felipe on 05/03/17.
 */
public class QrEntryMapper {
    public static QrEntry mapRequeryToDomain(RequeryQrEntry requeryQrEntry) {
        RequeryStarWarsCharacter requeryStarWarsCharacter = requeryQrEntry.getCharacter();
        RequeryQrEntryLocation requeryQrEntryLocation = requeryQrEntry.getLocation();
        long registeredDate = requeryQrEntry.getRegisteredDate();


        StarWarsCharacter character = StarWarsCharacterMapper.mapRequeryToDomain(requeryStarWarsCharacter);

        QrEntryLocation qrEntryLocation = QrEntryLocationMapper.mapRequeryToDomain(requeryQrEntryLocation);

        return new QrEntry(character, registeredDate, qrEntryLocation);
    }

    public static RequeryQrEntry mapDomainToRequery(QrEntry qrEntry) {
        StarWarsCharacter character = qrEntry.getCharacter();
        QrEntryLocation location = qrEntry.getLocation();
        long registeredDate = qrEntry.getRegisteredDate();

        RequeryQrEntry requeryQrEntry = new RequeryQrEntry();

        RequeryStarWarsCharacter requeryStarWarsCharacter = StarWarsCharacterMapper.mapDomainToRequery(character);
        requeryQrEntry.setCharacter(requeryStarWarsCharacter);

        RequeryQrEntryLocation requeryQrEntryLocation = QrEntryLocationMapper.mapDomainToRequery(location);
        requeryQrEntry.setLocation(requeryQrEntryLocation);

        requeryQrEntry.setRegisteredDate(registeredDate);

        return requeryQrEntry;
    }
}
