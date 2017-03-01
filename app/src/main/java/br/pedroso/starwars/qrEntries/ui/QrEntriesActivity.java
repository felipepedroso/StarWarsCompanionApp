package br.pedroso.starwars.qrEntries.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.pedroso.starwars.R;
import br.pedroso.starwars.qrEntries.QrEntriesContract;

public class QrEntriesActivity extends AppCompatActivity implements QrEntriesContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_entries);
    }
}
