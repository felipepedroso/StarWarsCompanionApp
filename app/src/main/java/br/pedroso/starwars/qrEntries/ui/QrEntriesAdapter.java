package br.pedroso.starwars.qrEntries.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.pedroso.starwars.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by felipe on 01/03/17.
 */
public class QrEntriesAdapter extends RecyclerView.Adapter<QrEntriesAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);

        View view = layoutInflater.inflate(R.layout.item_qr_entry, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCharacterName.setText("Test");
        holder.tvQrEntryUrl.setText("http://fakeurl.com");
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_character_name)
        TextView tvCharacterName;

        @BindView(R.id.tv_qr_entry_url)
        TextView tvQrEntryUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
