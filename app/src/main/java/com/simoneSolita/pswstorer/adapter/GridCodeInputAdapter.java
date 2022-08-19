package com.simonesolita.pswstorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.button.MaterialButton;
import com.simonesolita.pswstorer.R;

import java.util.Objects;

public class GridCodeInputAdapter extends BaseAdapter {

    Context context;
    int[] digits;
    LayoutInflater inflater;

    public GridCodeInputAdapter(Context applicationContext, int[] digits) {
        this.context = applicationContext;
        this.digits = digits;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return digits.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.button_code_login, null); // inflate the layout
        ImageView img = (ImageView) view.findViewById(R.id.imageView_code_digit);
        img.setImageResource(digits[i]);
        return view;
    }
}
