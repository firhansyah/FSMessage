package id.co.firhansyah.fsmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.firhansyah.fsmessage.adapters.ContactAdapter;
import id.co.firhansyah.fsmessage.global.Helper;
import id.co.firhansyah.fsmessage.models.Contact;

public class ContactActivity extends AppCompatActivity {

    EditText iptSearch;
    ListView lvContact;

    ContactAdapter adapter;
    ArrayList<Contact> lContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        lContact = Helper.getAllContact(this);

        if (lContact.isEmpty()) {
            Toast.makeText(this, "Contact Empty", Toast.LENGTH_SHORT).show();
        } else {
            initPalette();
        }
    }

    private void initPalette() {
        adapter = new ContactAdapter(this, lContact);

        iptSearch = (EditText) findViewById(R.id.iptSearch);
        iptSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lvContact = (ListView) findViewById(R.id.lvContact);
        lvContact.setAdapter(adapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact data = lContact.get(i);

                Intent intent = new Intent();
                intent.putExtra("number", data.getNumber());
                ContactActivity.this.setResult(201, intent);
                ContactActivity.this.finish();
            }
        });
        adapter.notifyDataSetChanged();

    }
}
