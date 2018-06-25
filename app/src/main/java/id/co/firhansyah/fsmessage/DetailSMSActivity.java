package id.co.firhansyah.fsmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailSMSActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtPhone, txtMessage, txtDate;
    Button btnBack, btnReply;

    private String PhoneReal, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sms);

        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtDate = (TextView) findViewById(R.id.txtDate);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnReply = (Button) findViewById(R.id.btnReply);
        btnReply.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            PhoneReal = extras.getString("txtPhoneReal");
            txtPhone.setText(extras.getString("txtPhone"));
            txtMessage.setText(extras.getString("txtMessage"));
            txtDate.setText(extras.getString("txtDate"));
            type = extras.getString("typeMessage");
        }

        if (type != null && !type.isEmpty())
            if (type.equals("sent"))
                btnReply.setText("Send Again");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnReply:
                Intent intentSend = new Intent(DetailSMSActivity.this, SendSMSActivity.class);
                intentSend.putExtra("PhoneReal", PhoneReal);
                startActivity(intentSend);
                break;
        }
    }
}
