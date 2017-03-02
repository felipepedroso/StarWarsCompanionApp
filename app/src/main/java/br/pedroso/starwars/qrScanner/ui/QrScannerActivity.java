package br.pedroso.starwars.qrScanner.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import br.pedroso.starwars.R;
import br.pedroso.starwars.qrEntries.ui.QrEntriesActivity;
import br.pedroso.starwars.qrScanner.QrScannerContract;
import br.pedroso.starwars.qrScanner.presenter.QrScannerPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by felipe on 02/03/17.
 */

public class QrScannerActivity extends AppCompatActivity implements QrScannerContract.View {

    private static final String TAG = QrScannerActivity.class.getName();

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    public static final String EXTRA_QR_SCAN_RESULT = "extra_qr_scan_result";

    @BindView(R.id.zxsv_scanner_view)
    ZXingScannerView zxsvScannerView;

    private QrScannerPresenter presenter;

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
        // TODO: replace by dependency injection
        presenter = new QrScannerPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.resume();
    }

    @Override
    public void requestCameraPermission() {
        // TODO: check if the user needs an explanation for this permission and show a message.
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public boolean hasPermissionToAccessCamera() {
        int cameraPermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        return cameraPermissionStatus == PackageManager.PERMISSION_GRANTED;
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
        zxsvScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                presenter.handleScanResult(result.getText());
            }
        });
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
    public void showCameraPermissionDeniedMessage() {
        Toast.makeText(this, R.string.permission_denied_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivityWithNoResult() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void finishActivityWithResult(String resultText) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_QR_SCAN_RESULT, resultText);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.cameraPermissionGranted();
            } else {
                presenter.cameraPermissionDenied();
            }
        }
    }
}
