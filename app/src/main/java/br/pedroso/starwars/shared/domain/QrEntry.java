package br.pedroso.starwars.shared.domain;

/**
 * Created by felipe on 01/03/17.
 */

public class QrEntry {
    private StarWarsCharacter character;

    private long registeredDate;

    private QrEntryLocation location;

    public QrEntry(StarWarsCharacter character, long registeredDate, QrEntryLocation location) {
        this.character = character;
        this.registeredDate = registeredDate;
        this.location = location;
    }

    public StarWarsCharacter getCharacter() {
        return character;
    }

    public long getRegisteredDate() {
        return registeredDate;
    }

    public QrEntryLocation getLocation() {
        return location;
    }
}
