package br.pedroso.starwars.qrEntries.presenter;

import android.util.Log;

import javax.inject.Inject;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.usecases.GetAllQrEntries;
import br.pedroso.starwars.qrEntries.usecases.InsertQrEntry;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.utils.StarWarsApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by felipe on 01/03/17.
 */

public class QrEntriesPresenter implements QrEntriesContract.Presenter {
    private static final String LOG_TAG = QrEntriesPresenter.class.getName();
    private final QrEntriesContract.View view;
    private final InsertQrEntry insertQrEntry;
    private GetAllQrEntries getAllQrEntries;

    @Inject
    public QrEntriesPresenter(QrEntriesContract.View view, GetAllQrEntries getAllQrEntries, InsertQrEntry insertQrEntry) {
        this.view = view;
        this.getAllQrEntries = getAllQrEntries;
        this.insertQrEntry = insertQrEntry;
    }

    @Override
    public void loadQrEntries() {
        view.cleanQrEntriesList();
        view.hideQrEntriesList();
        view.showEmptyEntriesListMessage();

        getAllQrEntries.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addNewQrEntry, this::handleLoadQrEntriesError);
    }

    private void handleLoadQrEntriesError(Throwable throwable) {
        view.showLoadQrEntriesErrorMessage(throwable);
    }

    @Override
    public void clickedOnFabScanQrCode() {
        if (view.hasRequiredPermissions()) {
            //view.startQrScannerActivity();
            handleQrCodeScanResult("http://swapi.co/api/people/1/");
        } else {
            view.requestRequiredPermissions();
        }
    }

    @Override
    public void handleQrCodeScanResult(String qrCodeScanResult) {
        if (StarWarsApiUtils.isStarWarsPeopleApiUrl(qrCodeScanResult)) {
            insertQrEntry.execute(qrCodeScanResult)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::addNewQrEntry, this::handleAddNewQrEntryError);
        } else {
            Log.d(LOG_TAG, "Invalid url.");
        }
    }

    private void handleAddNewQrEntryError(Throwable throwable) {
        // TODO
        Log.d(LOG_TAG, "Failed to add new qr entry");
    }

    private void addNewQrEntry(QrEntry qrEntry) {
        view.hideEmptyEntriesListMessage();
        view.showQrEntriesList();
        view.addQrEntry(qrEntry);
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
