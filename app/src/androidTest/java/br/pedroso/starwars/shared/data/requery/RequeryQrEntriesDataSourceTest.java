package br.pedroso.starwars.shared.data.requery;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.pedroso.starwars.BuildConfig;
import br.pedroso.starwars.shared.data.dataSources.QrEntriesDataSource;
import br.pedroso.starwars.shared.data.requery.entities.Models;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.QrEntryLocation;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by felipe on 07/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class RequeryQrEntriesDataSourceTest {

    private static final int FAKE_ENTRIES_COUNT = 10;
    private QrEntriesDataSource dataSource;
    private StarWarsCharacter[] charactersArray;
    private QrEntry[] qrEntries;

    @Before
    public void setup() {
        createQrEntriesDataSource();

        createFakeData();
    }

    @After
    public void tearDown() {
        cleanCharactersDatabase();
    }

    private void createQrEntriesDataSource() {
        Context context = InstrumentationRegistry.getTargetContext();

        DatabaseSource databaseSource = new DatabaseSource(context, Models.DEFAULT, 1);

        if (BuildConfig.DEBUG) {
            databaseSource.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }

        Configuration configuration = databaseSource.getConfiguration();

        ReactiveEntityStore<Persistable> entityStore = ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));

        dataSource = new RequeryQrEntriesDataSource(entityStore);
    }

    private void createFakeData() {
        qrEntries = new QrEntry[FAKE_ENTRIES_COUNT];

        charactersArray = new StarWarsCharacter[FAKE_ENTRIES_COUNT];

        for (int i = 0; i < FAKE_ENTRIES_COUNT; i++) {
            charactersArray[i] = new StarWarsCharacter("Fake Url " + i, "Fake Character" + i, 172, 73, "");

            qrEntries[i] = new QrEntry(charactersArray[i], System.currentTimeMillis() + i, new QrEntryLocation(20.0, 21.0));
        }
    }

    private void insertAllCharacters() {
        dataSource.insertStarWarsCharacter(charactersArray).blockingSubscribe();
    }

    private void cleanCharactersDatabase() {
        dataSource.deleteAllStarWarsCharacters().blockingSubscribe();
    }

    private void cleanQrEntryDatabase() {
        dataSource.deleteAllQrEntries().blockingSubscribe();
    }

    private void insertAllQrEntries() {
        dataSource.insertQrEntry(qrEntries).blockingSubscribe();
    }

    @Test
    public void testInsertSingleQrEntry() {
        cleanQrEntryDatabase();

        QrEntry qrEntry = qrEntries[0];

        Observable<QrEntry> insertQrEntryObservable = dataSource.insertQrEntry(qrEntry);

        insertQrEntryObservable.test()
                .assertNoErrors()
                .assertValue(qrEntry);

        Observable<QrEntry> getAllQrEntriesObservable = dataSource.getAllQrEntries();

        getAllQrEntriesObservable.test()
                .assertResult(qrEntry);
    }

    @Test
    public void testInsertMultipleQrEntries() {
        cleanQrEntryDatabase();

        Observable<QrEntry> insertQrEntriesObservable = dataSource.insertQrEntry(qrEntries);

        insertQrEntriesObservable.test()
                .assertNoErrors()
                .assertValues(qrEntries);

        Observable<QrEntry> getAllQrEntriesObservable = dataSource.getAllQrEntries();

        getAllQrEntriesObservable.test()
                .assertResult(qrEntries);
    }

    @Test
    public void testDeleteSingleQrEntry() {
        cleanQrEntryDatabase();

        QrEntry qrEntry = qrEntries[0];

        dataSource.insertQrEntry(qrEntry).blockingSubscribe();

        Completable deleteSingleQrEntryCompletable = dataSource.deleteQrEntries(qrEntries);

        deleteSingleQrEntryCompletable.test()
                .assertNoErrors()
                .assertNoValues();
    }

    @Test
    public void testDeleteMultipleQrEntries() {
        cleanQrEntryDatabase();

        insertAllQrEntries();

        Completable deleteQrEntriesCompletable = dataSource.deleteQrEntries(qrEntries);

        deleteQrEntriesCompletable.test()
                .assertNoErrors()
                .assertComplete();

        Observable<QrEntry> getAllQrEntriesObservable = dataSource.getAllQrEntries();

        getAllQrEntriesObservable.test()
                .assertResult(qrEntries);
    }

    @Test
    public void testDeleteAllQrEntries(){
        cleanQrEntryDatabase();

        insertAllQrEntries();

        Observable<Integer> deleteAllQrEntriesObservable = dataSource.deleteAllQrEntries();

        deleteAllQrEntriesObservable.test()
                .assertNoErrors()
                .assertValue(qrEntries.length);

        Observable<QrEntry> getAllQrEntriesObservable = dataSource.getAllQrEntries();

        getAllQrEntriesObservable.test()
                .assertNoValues();
    }

    @Test
    public void testGetCharacterByUrl() {
        cleanCharactersDatabase();

        insertAllCharacters();

        StarWarsCharacter starWarsCharacter = charactersArray[0];

        Observable<StarWarsCharacter> getExistentCharacterObservable = dataSource.getCharacterByUrl(starWarsCharacter.getUrl());

        getExistentCharacterObservable.test()
                .assertNoErrors()
                .assertValue(starWarsCharacter);

        Observable<StarWarsCharacter> getInexistentCharacterObservable = dataSource.getCharacterByUrl("INEXISTENT_CHARACTER_URL");

        getInexistentCharacterObservable.test()
                .assertNoValues();
    }

    @Test
    public void testInsertSingleCharacter() {
        cleanCharactersDatabase();

        StarWarsCharacter starWarsCharacter = charactersArray[0];

        Observable<StarWarsCharacter> insertCharacterObservable = dataSource.insertStarWarsCharacter(starWarsCharacter);

        insertCharacterObservable.test()
                .assertNoErrors()
                .assertResult(starWarsCharacter);

        Observable<StarWarsCharacter> getCharacterByUrlObservable = dataSource.getCharacterByUrl(starWarsCharacter.getUrl());

        getCharacterByUrlObservable.test()
                .assertNoErrors()
                .assertResult(starWarsCharacter);
    }

    @Test
    public void testInsertMultipleCharacters() {
        cleanCharactersDatabase();

        Observable<StarWarsCharacter> starWarsCharacterObservable = dataSource.insertStarWarsCharacter(charactersArray);

        starWarsCharacterObservable.test()
                .assertNoErrors()
                .assertResult(charactersArray);

        Observable<StarWarsCharacter> getAllCharactersObservable = dataSource.getAllCharacters();

        getAllCharactersObservable.test()
                .assertNoErrors()
                .assertResult(charactersArray);
    }

    @Test
    public void testDeleteSingleCharacter() {
        cleanCharactersDatabase();

        StarWarsCharacter starWarsCharacter = charactersArray[0];

        String characterUrl = starWarsCharacter.getUrl();

        dataSource.insertStarWarsCharacter(starWarsCharacter).blockingSingle();

        Completable deleteOneCharacterObservable = dataSource.deleteStarWarsCharacters(starWarsCharacter);

        deleteOneCharacterObservable.test()
                .assertNoErrors()
                .assertComplete();

        Observable<StarWarsCharacter> getCharacterByUrlObservable = dataSource.getCharacterByUrl(characterUrl);

        getCharacterByUrlObservable.test()
                .assertNoValues();
    }

    @Test
    public void testDeleteMultipleCharacters() {
        cleanCharactersDatabase();

        insertAllCharacters();

        StarWarsCharacter starWarsCharacter1 = charactersArray[0];

        StarWarsCharacter starWarsCharacter2 = charactersArray[FAKE_ENTRIES_COUNT - 1];

        Completable deleteOneCharacterObservable = dataSource.deleteStarWarsCharacters(starWarsCharacter1, starWarsCharacter2);

        deleteOneCharacterObservable.test()
                .assertNoErrors()
                .assertComplete();

        Observable<StarWarsCharacter> getCharacterByUrlObservable1 = dataSource.getCharacterByUrl(starWarsCharacter1.getUrl());

        getCharacterByUrlObservable1.test()
                .assertNoValues();

        Observable<StarWarsCharacter> getCharacterByUrlObservable2 = dataSource.getCharacterByUrl(starWarsCharacter2.getUrl());

        getCharacterByUrlObservable2.test()
                .assertNoValues();
    }

    @Test
    public void testDeleteAllCharacters() {
        cleanCharactersDatabase();

        insertAllCharacters();

        Observable<Integer> deleteAllCharactersObservable = dataSource.deleteAllStarWarsCharacters();

        deleteAllCharactersObservable.test()
                .assertNoErrors()
                .assertValue(FAKE_ENTRIES_COUNT);

        Observable<StarWarsCharacter> getAllCharactersObservable = dataSource.getAllCharacters();

        getAllCharactersObservable.test()
                .assertNoValues();
    }


}
