package br.pedroso.starwars.qrEntries.presenter;

import android.util.Log;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.ui.QrEntriesActivity;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.domain.StarWarsCharacter;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felipe on 01/03/17.
 */

public class QrEntriesPresenter implements QrEntriesContract.Presenter {
    private static final String LOG_TAG = QrEntriesPresenter.class.getName();
    private final QrEntriesContract.View view;

    public QrEntriesPresenter(QrEntriesContract.View view) {
        this.view = view;
    }

    @Override
    public void loadQrEntries() {
        view.cleanQrEntriesList();
        view.hideQrEntriesList();
        view.showEmptyEntriesListMessage();

        Consumer<? super QrEntry> onNext = new Consumer<QrEntry>() {
            @Override
            public void accept(@NonNull QrEntry qrEntry) throws Exception {
                view.hideEmptyEntriesListMessage();
                view.showQrEntriesList();
                view.addQrEntry(qrEntry);
            }
        };

        Consumer<? super Throwable> onError = new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                view.showLoadQrEntriesErrorMessage(throwable);
            }
        };

        qrEntriesStub()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    @Override
    public void clickedOnFabScanQrCode() {
        Log.d(LOG_TAG, "Clicked!");

        view.startQrScannerActivity();
    }

    public Flowable<QrEntry> qrEntriesStub() {
        int entriesCount = 50;

        QrEntry[] qrEntries = new QrEntry[entriesCount];

        for (int i = 0; i < entriesCount; i++) {
            StarWarsCharacter character = new StarWarsCharacter("Character " + i, 172, 72, "Human", "Planet", "YHASB");
            QrEntry qrEntry = new QrEntry("Fake URL " + i, System.currentTimeMillis() - (i * 60000));
            qrEntry.setCharacter(character);

            qrEntries[i] = qrEntry;
        }

        return Flowable.fromArray(qrEntries);
    }
}
