package com.simonesolita.pswstorer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.activity.AddCredenzialeActivity;
import com.simonesolita.pswstorer.adapter.CredenzialeAdapter;
import com.simonesolita.pswstorer.constants.IntentConstants;
import com.simonesolita.pswstorer.database.PSWStorerDBManager;
import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        setContent(v);
        return v;
    }

    private void setContent(View v) {
    }
}
