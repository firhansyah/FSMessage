package id.co.firhansyah.fsmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.co.firhansyah.fsmessage.global.Helper;

public class SendSMSActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText iptPhone, iptMessage;
    private Button btnContact, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        initPalette();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            if (extras.containsKey("PhoneReal"))
                iptPhone.setText(extras.getString("PhoneReal"));
    }

    private void initPalette() {
        iptPhone = (EditText) findViewById(R.id.iptPhone);
        iptMessage = (EditText) findViewById(R.id.iptMessage);

        btnContact = (Button) findViewById(R.id.btnContact);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnContact.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContact:
                Intent intentContact = new Intent(SendSMSActivity.this, ContactActivity.class);
                startActivityForResult(intentContact, 201);
                break;

            case R.id.btnSend:
                String phoneNumber = iptPhone.getText().toString();
                String message = iptMessage.getText().toString();
                
                if (phoneNumber.isEmpty() || message.isEmpty()) {
                    Toast.makeText(this, "Please fill Phone & Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (Helper.sendSMS(SendSMSActivity.this, phoneNumber, message)) {
                        iptMessage.setText("");
                        iptMessage.requestFocus();
                    } else {
                        Toast.makeText(this, "Something Trouble, Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 201) {
            if (data != null && data.hasExtra("number")) {
                String phoneNumber=data.getStringExtra("number");
                iptPhone.setText(phoneNumber);
            }
        }
    }

    public void toastMessage(String messageToast) {
        Toast.makeText(this, messageToast, Toast.LENGTH_SHORT).show();
    }
}
