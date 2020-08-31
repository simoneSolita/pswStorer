package com.simonesolita.pswstorer.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.activity.AddCredenzialeActivity;
import com.simonesolita.pswstorer.activity.DetailCredenzialeActivity;
import com.simonesolita.pswstorer.adapter.CredenzialeAdapter;
import com.simonesolita.pswstorer.constants.IntentConstants;
import com.simonesolita.pswstorer.database.PSWStorerDBManager;
import com.simonesolita.pswstorer.entities.Credenziale;
import com.simonesolita.pswstorer.interfaces.OnDeleteListener;
import com.simonesolita.pswstorer.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class ListCredenzialeFragment extends Fragment {

    ArrayList<Credenziale> credenzialiListToShow = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayout;
    private String uuidSelezionato;
    private OnDeleteListener listener;

    public static ListCredenzialeFragment getInstance() {
        return new ListCredenzialeFragment();
    }

    public ListCredenzialeFragment() {
        // Required empty public constructor
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_credenziale_list, container, false);
        getListCredenzialis();
        setContent(v);
        return v;
    }

    private void setContent(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerView_list_credenziali);
        mLayout = new LinearLayoutManager(PswStorerApplication.getCurrentActivity());

        FloatingActionButton floatingButton = v.findViewById(R.id.add_credenziale_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PswStorerApplication.getCurrentActivity(), AddCredenzialeActivity.class);
                getActivity().startActivityForResult(intent, IntentConstants.CREDENZIALI_LIST_REQUEST_CODE_ADD);
            }
        });

        createAdapter();
    }

    public void createAdapter() {
        CredenzialeAdapter mAdapter = new CredenzialeAdapter(credenzialiListToShow);
        mRecyclerView.setLayoutManager(mLayout);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(String id) {
                if (!TextUtils.isEmpty(id)) {
                    Intent intentToDettaglio = new Intent(PswStorerApplication.getCurrentActivity(), DetailCredenzialeActivity.class);
                    intentToDettaglio.putExtra(IntentConstants.CREDENZIALI_LIST_DETAIL_UUID, id);
                    startActivity(intentToDettaglio);
                }
            }

            @Override
            public void OnItemDelete(String id) {
                if (!TextUtils.isEmpty(id)) {
                    uuidSelezionato = id;
                    ArrayList<Credenziale> credenzialiToCheck = PSWStorerDBManager.getInstance().getCredenzialeByCursor(PSWStorerDBManager.getInstance().getCredenzialeByUUID(id));
                    if (!credenzialiToCheck.isEmpty()) {
                        Credenziale credenzialeToDelete = credenzialiToCheck.get(0);
                        new MaterialAlertDialogBuilder(PswStorerApplication.getCurrentActivity())
                                .setTitle(PswStorerApplication.getCurrentActivity().getString(R.string.titolo_conferma_eliminazione_credenziale))
                                .setMessage(String.format(PswStorerApplication.getCurrentActivity().getString(R.string.messaggio_conferma_eliminazione_credenziale_selezionata), credenzialeToDelete.getNome()))
                                .setPositiveButton(PswStorerApplication.getCurrentActivity().getString(R.string.conferma_eliminazione),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                try {
                                                    PSWStorerDBManager.getInstance().deleteCredenziale(uuidSelezionato);
                                                    if (listener != null) {
                                                        listener.OnItemDelete(true);
                                                    }
                                                } catch (Exception e) {
                                                    if (listener != null) {
                                                        listener.OnItemDelete(false);
                                                    }
                                                }
                                                dialog.dismiss();
                                            }
                                        })
                                .setNegativeButton(PswStorerApplication.getCurrentActivity().getString(R.string.annulla_eliminazione),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                if (listener != null) {
                                                    listener.OnItemDelete(false);
                                                }
                                                dialog.dismiss();
                                            }
                                        })
                                .setCancelable(true)
                                .show();
                    }
                }
            }

            @Override
            public void OnLongItemClick(String id) {
                ArrayList<Credenziale> credenzialiToCheck = PSWStorerDBManager.getInstance().getCredenzialeByCursor(PSWStorerDBManager.getInstance().getCredenzialeByUUID(id));
                if (!credenzialiToCheck.isEmpty()) {
                    Credenziale credenzialeToCopy = credenzialiToCheck.get(0);
                    ClipboardManager clipboard = (ClipboardManager) PswStorerApplication.getCurrentActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("psw", credenzialeToCopy.getValore());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(PswStorerApplication.getCurrentActivity(),PswStorerApplication.getCurrentActivity().getString(R.string.password_copiata),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getListCredenzialis() {

        ArrayList<Credenziale> credenzialisFromDB = PSWStorerDBManager.getInstance().getCredenzialeByCursor(PSWStorerDBManager.getInstance().getAllCredenzialis());
        if (credenzialisFromDB != null && !credenzialisFromDB.isEmpty()) {
            credenzialiListToShow.addAll(credenzialisFromDB);
        }
    }
}
