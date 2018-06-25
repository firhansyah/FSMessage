package id.co.firhansyah.fsmessage.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import id.co.firhansyah.fsmessage.DetailSMSActivity;
import id.co.firhansyah.fsmessage.MainActivity;
import id.co.firhansyah.fsmessage.R;
import id.co.firhansyah.fsmessage.adapters.SmsAdapter;
import id.co.firhansyah.fsmessage.global.Helper;
import id.co.firhansyah.fsmessage.models.Sms;

public class InboxFragment extends Fragment {
    ListView lvlInbox;

    ArrayList<Sms> lInbox;

    SmsAdapter adapter;

    public InboxFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_inbox, container, false);

        lInbox = Helper.getAllSms((MainActivity)getActivity(), "inbox");

        initFragment(view);

        return view;
    }

    public void initFragment(View v) {
        MainActivity activity = ((MainActivity)getActivity());
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("INBOX");

        lvlInbox = (ListView) v.findViewById(R.id.lvInbox);
        adapter = new SmsAdapter(lInbox, getActivity());
        lvlInbox.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvlInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sms data = lInbox.get(i);

                String contactName = Helper.getContactName(data.getPhoneNumber(), getActivity());

                Intent intent = new Intent(getActivity(), DetailSMSActivity.class);
                intent.putExtra("txtPhoneReal", data.getPhoneNumber());
                if (contactName != null && !contactName.isEmpty())
                    intent.putExtra("txtPhone", contactName + " (" + data.getPhoneNumber() + ")");
                else
                    intent.putExtra("txtPhone", data.getPhoneNumber());
                intent.putExtra("txtMessage", data.getMessage());
                intent.putExtra("txtDate", data.getDate());
                intent.putExtra("typeMessage", "inbox");
                getActivity().startActivity(intent);
            }
        });
    }
}