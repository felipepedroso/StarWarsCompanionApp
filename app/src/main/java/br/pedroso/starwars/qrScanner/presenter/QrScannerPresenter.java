package br.pedroso.starwars.qrScanner.presenter;

import javax.inject.Inject;

import br.pedroso.starwars.qrScanner.QrScannerContract;

/**
 * Created by felipe on 02/03/17.
 */
public class QrScannerPresenter implements QrScannerContract.Presenter {
    private final QrScannerContract.View view;

    @Inject
    public QrScannerPresenter(QrScannerContract.View view) {
        this.view = view;
    }

    @Override
    public void resume() {
        if (view.hasPermissionToAccessCamera()) {
            view.startQrScanner();
        } else {
            view.requestCameraPermission();
        }
    }

    @Override
    public void handleScanResult(String resultText) {
        // TODO: check if the scanned text is a valid URL.
        view.finishActivityWithResult(resultText);
    }

    @Override
    public void pause() {
        view.stopQrScanner();
    }

    @Override
    public void cameraPermissionGranted() {
        view.startQrScanner();
    }

    @Override
    public void cameraPermissionDenied() {
        view.showCameraPermissionDeniedMessage();
        view.finishActivityWithNoResult();
    }
}
