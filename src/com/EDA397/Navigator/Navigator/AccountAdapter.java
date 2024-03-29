package com.EDA397.Navigator.Navigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.test.R;

public class AccountAdapter extends ArrayAdapter<String> {

    private Context context;
    private SharedPreferences accounts;
    private SharedPreferences.Editor accEdit;
    private ArrayList<String> names;

    public AccountAdapter(Context c, int r, int tv, ArrayList<String> l) {
        super(c,r,tv,l);
        this.context = c;
        this.names = l;
        accounts = c.getSharedPreferences("StoredAccounts", c.MODE_PRIVATE);
        accEdit = accounts.edit();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            final String s = names.get(position);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.account_item, null);
            }
        TextView tv = (TextView) convertView.findViewById(R.id.account_text);
        tv.setText(s);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete_button);
        delete.setFocusableInTouchMode(false);
        delete.setFocusable(false);
        delete.setOnClickListener(
            new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                accEdit.remove(s);
                accEdit.commit();
                names.remove(position);
                remove(s);
                }
            }
        );
        return (convertView);
    }
}