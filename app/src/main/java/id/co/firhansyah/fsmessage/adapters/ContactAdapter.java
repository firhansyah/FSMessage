package id.co.firhansyah.fsmessage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.firhansyah.fsmessage.R;
import id.co.firhansyah.fsmessage.models.Contact;

public class ContactAdapter extends ArrayAdapter<Contact> {

    ViewHolder holder;

    public ContactAdapter(Context _context, ArrayList<Contact> lData) {
        super(_context, 0,lData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact data = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_contact, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.number);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(data.getName());
        holder.tvnumber.setText(data.getNumber());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvnumber;

    }
}