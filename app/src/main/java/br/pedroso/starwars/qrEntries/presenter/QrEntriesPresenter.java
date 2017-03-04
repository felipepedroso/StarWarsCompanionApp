package br.pedroso.starwars.qrEntries.presenter;

import android.util.Log;

import javax.inject.Inject;

import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.shared.data.location.LocationDataSourceImpl;
import br.pedroso.starwars.shared.data.repository.QrEntriesRepositoryImpl;
import br.pedroso.starwars.shared.data.requery.RequeryQrEntriesDataSource;
import br.pedroso.starwars.shared.data.retrofit.RetrofitStarWarsApiDataSource;
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
    private final QrEntriesRepositoryImpl repository;

    @Inject
    public QrEntriesPresenter(QrEntriesContract.View view) {
        this.view = view;

        // TODO: replace this by usecases and (please) use Dagger2
        repository = new QrEntriesRepositoryImpl(new RequeryQrEntriesDataSource(), new LocationDataSourceImpl(), new RetrofitStarWarsApiDataSource());
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

        // TODO: Replace this by an usecase execution.
        repository.getAllQrEntries()
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
