package br.pedroso.starwars.qrScanner.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.pedroso.starwars.R;
import br.pedroso.starwars.di.qrScanner.DaggerQrScannerComponent;
import br.pedroso.starwars.di.qrScanner.QrScannerPresenterModule;
import br.pedroso.starwars.qrScanner.QrScannerContract;
import br.pedroso.starwars.qrScanner.presenter.QrScannerPresenter;
import br.pedroso.starwars.shared.utils.PermissionsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by felipe on 02/03/17.
 */

public class QrScannerActivity extends AppCompatActivity implements QrScannerContract.View {

    private static final String TAG = QrScannerActivity.class.getName();

    public static final String EXTRA_QR_CODE_SCAN_RESULT = "extra_qr_scan_result";

    @BindView(R.id.zxsv_scanner_view)
    ZXingScannerView zxsvScannerView;

    @Inject
    QrScannerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupView();

        injectPresenter();
    }

    private void setupView() {
        setContentView(R.layout.activity_qr_scanner);
        ButterKnife.bind(this);
    }

    private void injectPresenter() {
        DaggerQrScannerComponent.builder()
                .qrScannerPresenterModule(new QrScannerPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.resume();
    }

    @Override
    public boolean hasPermissionToAccessCamera() {
        return PermissionsUtils.hasPermission(this, Manifest.permission.CAMERA);
    }

    @Override
    public void startQrScanner() {
        setupQrScanner();

        zxsvScannerView.startCamera();
    }

    private void setupQrScanner() {
        setupSupportedFormat();

        setupResultHandler();
    }

    private void setupResultHandler() {
        zxsvScannerView.setResultHandler(result -> presenter.handleScanResult(result.getText()));
    }

    private void setupSupportedFormat() {
        List<BarcodeFormat> formats = new ArrayList<>();

        formats.add(BarcodeFormat.QR_CODE); // We are only supporting QR_CODE in this project. ;)

        zxsvScannerView.setFormats(formats);
    }

    @Override
    public void stopQrScanner() {
        zxsvScannerView.setResultHandler(null);
        zxsvScannerView.stopCamera();
    }

    @Override
    public void finishActivityWithNoResult() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void finishActivityWithResult(String resultText) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_QR_CODE_SCAN_RESULT, resultText);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }
}
