package br.pedroso.starwars.qrEntries;

import br.pedroso.starwars.shared.domain.QrEntry;

/**
 * Created by felipe on 01/03/17.
 */

public interface QrEntriesContract {
    interface Presenter {

        void loadQrEntries();

        void clickedOnFabScanQrCode();

        void handleQrCodeScanResult(String qrCodeScanResult);
    }

    interface View {
        void cleanQrEntriesList();

        void showEmptyEntriesListMessage();

        void hideEmptyEntriesListMessage();

        void addQrEntry(QrEntry qrEntry);

        void showLoadQrEntriesErrorMessage(Throwable throwable);

        void hideQrEntriesList();

        void showQrEntriesList();

        void startQrScannerActivity();
    }
}
