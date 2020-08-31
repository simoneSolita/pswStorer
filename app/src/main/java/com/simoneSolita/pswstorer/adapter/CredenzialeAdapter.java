package com.simonesolita.pswstorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private static OnItemClickListener listener;

    public static class CredenzialeViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;
        public TextView textViewDescrizione;
        public TextView textViewValore;
        public TextView textViewUtenza;
        public TextView textViewUUID;
        public ImageView imageEliminaCredenziale;

        public CredenzialeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textview_credenziale_nome_valore);
            textViewUtenza = itemView.findViewById(R.id.textview_credenziale_utenza_valore);
            textViewDescrizione = itemView.findViewById(R.id.textview_credenziale_descrizione_valore);
            textViewValore = itemView.findViewById(R.id.textview_credenziale_valore_valore);
            textViewUUID = itemView.findViewById(R.id.textview_credenziale_id);
            imageEliminaCredenziale = itemView.findViewById(R.id.button_elimina_credenziale);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(textViewUUID.getText().toString());
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnLongItemClick(textViewUUID.getText().toString());
                        }
                    }
                    return true;
                }
            });

            imageEliminaCredenziale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemDelete(textViewUUID.getText().toString());
                        }
                    }
                }
            });
        }
    }

    public CredenzialeAdapter(ArrayList<Credenziale> credenzialeList) {
        this.credenzialeList = credenzialeList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

        holder.textViewNome.setText(credenzialeToShow.getNome());
        holder.textViewDescrizione.setText(credenzialeToShow.getDescrizione());
        holder.textViewUtenza.setText(credenzialeToShow.getUtenza());
        holder.textViewValore.setText(credenzialeToShow.getValore());
        holder.textViewUUID.setText(credenzialeToShow.getUuid());
    }

    @Override
    public int getItemCount() {
        return credenzialeList.size();
    }
}
