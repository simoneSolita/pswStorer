package com.simonesolita.pswstorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CredenzialeAdapter extends RecyclerView.Adapter<CredenzialeAdapter.CredenzialeViewHolder> {

    private List<Credenziale> credenzialeList = new ArrayList<>();
    private Context context;

    public static class CredenzialeViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNomeViewIdCredenziale;
        public TextView textViewDescrizioneViewIdCredenziale;
        public TextView textViewValoreViewIdCredenziale;

        public CredenzialeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeViewIdCredenziale = itemView.findViewById(R.id.textview_credenziale_nome);
            textViewDescrizioneViewIdCredenziale = itemView.findViewById(R.id.textview_credenziale_descrizione);
            textViewValoreViewIdCredenziale = itemView.findViewById(R.id.textview_credenziale_valore);
        }
    }

    public CredenzialeAdapter(ArrayList<Credenziale> credenzialeList, Context context) {
        this.credenzialeList = credenzialeList;
        this.context = context;
    }

    @NonNull
    @Override
    public CredenzialeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_credenziale, parent, false);
        return new CredenzialeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CredenzialeViewHolder holder, int position) {
        Credenziale credenzialeToShow = credenzialeList.get(position);

        holder.textViewNomeViewIdCredenziale.setText(credenzialeToShow.getNome());
        holder.textViewDescrizioneViewIdCredenziale.setText(credenzialeToShow.getDescrizione());
        holder.textViewValoreViewIdCredenziale.setText(credenzialeToShow.getValore());
    }

    @Override
    public int getItemCount() {
        return credenzialeList.size();
    }
}
