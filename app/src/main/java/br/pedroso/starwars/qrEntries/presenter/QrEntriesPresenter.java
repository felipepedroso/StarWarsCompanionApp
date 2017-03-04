package br.pedroso.starwars.qrEntries.presenter;

import android.util.Log;

import javax.inject.Inject;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.usecases.GetAllQrEntries;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.utils.StarWarsApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by felipe on 01/03/17.
 */

public class QrEntriesPresenter implements QrEntriesContract.Presenter {
    private static final String LOG_TAG = QrEntriesPresenter.class.getName();
    private final QrEntriesContract.View view;
    private GetAllQrEntries getAllQrEntries;

    @Inject
    public QrEntriesPresenter(QrEntriesContract.View view, GetAllQrEntries getAllQrEntries) {
        this.view = view;
        this.getAllQrEntries = getAllQrEntries;
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

        getAllQrEntries.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    @Override
    public void clickedOnFabScanQrCode() {
        if (view.hasRequiredPermissions()) {
            view.startQrScannerActivity();
        } else {
            view.requestRequiredPermissions();
        }
    }

    @Override
    public void handleQrCodeScanResult(String qrCodeScanResult) {
        if (StarWarsApiUtils.isStarWarsPeopleApiUrl(qrCodeScanResult)) {
            Log.d(LOG_TAG, "Valid url.");
        } else {
            Log.d(LOG_TAG, "Invalid url.");
        }
    }

    @Override
    public void requiredPermissionsDenied() {
        view.showPermissionsDeniedMessage();
    }

    @Override
    public void requiredPermissionsGranted() {
        view.startQrScannerActivity();
    }

    @Override
    public void userRequestedToStopPermissionsDialog() {
        view.showPermissionsSettingsMessage();
    }
}
