package br.pedroso.starwars.qrEntries.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import br.pedroso.starwars.R;
import br.pedroso.starwars.StarWarsCompanionApplication;
import br.pedroso.starwars.di.application.ApplicationComponent;
import br.pedroso.starwars.di.qrEntries.DaggerQrEntriesComponent;
import br.pedroso.starwars.di.qrEntries.QrEntriesPresenterModule;
import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.presenter.QrEntriesPresenter;
import br.pedroso.starwars.qrScanner.ui.QrScannerActivity;
import br.pedroso.starwars.shared.domain.QrEntry;
import br.pedroso.starwars.shared.utils.PermissionsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QrEntriesActivity extends AppCompatActivity implements QrEntriesContract.View {

    public static final int SCAN_QR_CODE_REQUEST = 12;
    private static final int PERMISSIONS_REQUEST_CODE = 12;
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};

    @BindView(R.id.rv_qr_entries)
    RecyclerView rvQrEntries;

    @BindView(R.id.fab_scan_qr_code)
    FloatingActionButton fabScanQrCode;

    @BindView(R.id.tv_empty_qr_entries_message)
    TextView tvEmptyQrEntriesMessage;

    @BindView(R.id.cl_qr_entries_root)
    CoordinatorLayout clQrEntriesRoot;

    private QrEntriesAdapter qrEntriesAdapter;

    @Inject
    QrEntriesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();

        injectPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadQrEntries();
    }

    private void injectPresenter() {
        StarWarsCompanionApplication application = (StarWarsCompanionApplication) getApplication();

        ApplicationComponent applicationComponent = application.getApplicationComponent();

        DaggerQrEntriesComponent.builder()
                .qrEntriesPresenterModule(new QrEntriesPresenterModule(this))
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    private void setupViews() {
        setContentView(R.layout.activity_qr_entries);

        ButterKnife.bind(this);

        setupRecyclerViewQrEntries();

        setupFabListener();
    }

    private void setupFabListener() {
        fabScanQrCode.setOnClickListener(view -> presenter.clickedOnFabScanQrCode());
    }

    private void setupRecyclerViewQrEntries() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvQrEntries.setLayoutManager(layoutManager);

        qrEntriesAdapter = new QrEntriesAdapter();
        rvQrEntries.setAdapter(qrEntriesAdapter);
    }

    @Override
    public void cleanQrEntriesList() {
        qrEntriesAdapter.clearItems();
    }

    @Override
    public void showEmptyEntriesListMessage() {
        tvEmptyQrEntriesMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyEntriesListMessage() {
        tvEmptyQrEntriesMessage.setVisibility(View.GONE);
    }

    @Override
    public void addQrEntry(QrEntry qrEntry) {
        qrEntriesAdapter.addQrEntry(qrEntry);
    }

    @Override
    public void showLoadQrEntriesErrorMessage(Throwable throwable) {
        // TODO
    }

    @Override
    public void hideQrEntriesList() {
        rvQrEntries.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showQrEntriesList() {
        rvQrEntries.setVisibility(View.VISIBLE);
    }

    @Override
    public void startQrScannerActivity() {
        Intent intent = new Intent(this, QrScannerActivity.class);
        startActivityForResult(intent, SCAN_QR_CODE_REQUEST);
    }

    @Override
    public void requestRequiredPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public boolean hasRequiredPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (!PermissionsUtils.hasPermission(this, permission)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void showPermissionsSettingsMessage() {
        Snackbar.make(clQrEntriesRoot, R.string.check_permission_settings, Snackbar.LENGTH_LONG)
                .setAction(R.string.permission_settings, view -> openApplicationPermissionSettings())
                .show();
    }

    private void openApplicationPermissionSettings() {
        Intent permissionsSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        permissionsSettingsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        permissionsSettingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(permissionsSettingsIntent);
    }

    @Override
    public void showPermissionsDeniedMessage() {
        Snackbar.make(clQrEntriesRoot, R.string.permission_denied_message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SCAN_QR_CODE_REQUEST) {
                String qrCodeScanResult = data.getStringExtra(QrScannerActivity.EXTRA_QR_CODE_SCAN_RESULT);

                presenter.handleQrCodeScanResult(qrCodeScanResult);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // TODO: request permissions using RxJava: https://github.com/beworker/rx-android-permissions
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (hasGrantedPermissions(grantResults)) {
                presenter.requiredPermissionsGranted();
            } else {
                if (hasRequestedToStopPermissionsDialog(permissions)) {
                    presenter.userRequestedToStopPermissionsDialog();
                } else {
                    presenter.requiredPermissionsDenied();
                }
            }
        }
    }

    private boolean hasRequestedToStopPermissionsDialog(String[] permissions) {
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasGrantedPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
