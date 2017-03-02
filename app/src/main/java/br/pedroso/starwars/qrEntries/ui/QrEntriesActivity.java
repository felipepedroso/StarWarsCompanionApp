package br.pedroso.starwars.qrEntries.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.pedroso.starwars.R;
import br.pedroso.starwars.qrEntries.QrEntriesContract;
import br.pedroso.starwars.qrEntries.presenter.QrEntriesPresenter;
import br.pedroso.starwars.shared.domain.QrEntry;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QrEntriesActivity extends AppCompatActivity implements QrEntriesContract.View {

    @BindView(R.id.rv_qr_entries)
    RecyclerView rvQrEntries;

    @BindView(R.id.fab_scan_qr_code)
    FloatingActionButton fabScanQrCode;

    @BindView(R.id.tv_empty_qr_entries_message)
    TextView tvEmptyQrEntriesMessage;

    private QrEntriesAdapter qrEntriesAdapter;

    private QrEntriesPresenter presenter;

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
        // TODO: replace this by Dagger
        presenter = new QrEntriesPresenter(this);
    }

    private void setupViews() {
        setContentView(R.layout.activity_qr_entries);

        ButterKnife.bind(this);

        setupRecyclerViewQrEntries();

        setupFabListener();
    }

    private void setupFabListener() {
        fabScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickedOnFabScanQrCode();
            }
        });
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
}
