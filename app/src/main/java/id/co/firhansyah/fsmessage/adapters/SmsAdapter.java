package id.co.firhansyah.fsmessage.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.firhansyah.fsmessage.DetailSMSActivity;
import id.co.firhansyah.fsmessage.R;
import id.co.firhansyah.fsmessage.global.Helper;
import id.co.firhansyah.fsmessage.models.Sms;

public class SmsAdapter extends ArrayAdapter<Sms>{

    private ArrayList<Sms> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtPhone;
        TextView txtMessage;
        TextView txtDate;
    }

    public SmsAdapter(ArrayList<Sms> data, Context context) {
        super(context, R.layout.list_item_sms, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sms dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_sms, parent, false);
            viewHolder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String contactName = Helper.getContactName(dataModel.getPhoneNumber(), mContext);

        if (contactName != null && !contactName.isEmpty())
            viewHolder.txtPhone.setText(contactName + " (" + dataModel.getPhoneNumber() + ")");
        else
            viewHolder.txtPhone.setText(dataModel.getPhoneNumber());

        if (dataModel.getMessage().length() >= 25)
            viewHolder.txtMessage.setText(dataModel.getMessage().replace("\\n", " ").substring(0, 25) + "...");
        else
            viewHolder.txtMessage.setText(dataModel.getMessage());

        viewHolder.txtDate.setText(dataModel.getDate());
        return convertView;
    }
}
