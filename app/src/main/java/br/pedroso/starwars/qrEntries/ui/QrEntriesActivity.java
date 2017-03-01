package br.pedroso.starwars.qrEntries.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import br.pedroso.starwars.R;
import br.pedroso.starwars.qrEntries.QrEntriesContract;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QrEntriesActivity extends AppCompatActivity implements QrEntriesContract.View{

    @BindView(R.id.rv_qr_entries)
    RecyclerView rvQrEntries;

    @BindView(R.id.fab_scan_qr_code)
    FloatingActionButton fabScanQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();
    }

    private void setupViews() {
        setContentView(R.layout.activity_qr_entries);

        ButterKnife.bind(this);
    }
}
