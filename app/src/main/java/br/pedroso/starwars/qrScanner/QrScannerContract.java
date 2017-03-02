package br.pedroso.starwars.qrScanner;

/**
 * Created by felipe on 02/03/17.
 */

public interface QrScannerContract {

    interface Presenter {
        void resume();

        void handleScanResult(String resultText);

        void pause();

        void cameraPermissionGranted();

        void cameraPermissionDenied();
    }

    interface View {
        boolean hasPermissionToAccessCamera();

        void requestCameraPermission();

        void startQrScanner();

        void stopQrScanner();

        void showCameraPermissionDeniedMessage();

        void finishActivityWithNoResult();

        void finishActivityWithResult(String resultText);
    }
}
