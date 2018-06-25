package id.co.firhansyah.fsmessage.global;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.co.firhansyah.fsmessage.MainActivity;
import id.co.firhansyah.fsmessage.SendSMSActivity;
import id.co.firhansyah.fsmessage.models.Contact;
import id.co.firhansyah.fsmessage.models.Sms;

public class Helper {
    public static String millisToDate(long currentTime) {
        String finalDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        Date date = calendar.getTime();
        finalDate = android.text.format.DateFormat.format("dd/MM/yyyy HH:mm:ss", date).toString();
        return finalDate;
    }

    public static ArrayList<Contact> getAllContact(Context context) {
        ArrayList<Contact> lContact = new ArrayList<>();

        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contactModel = new Contact();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            lContact.add(contactModel);
        }
        phones.close();

        return lContact;
    }

    public static String getContactName(final String phoneNumber, Context context) {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }

    public static boolean sendSMS(final SendSMSActivity activity, String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        try {
            PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0, new Intent(DELIVERED), 0);
            activity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case SendSMSActivity.RESULT_OK:
                            activity.toastMessage("SMS Terkirim");
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            activity.toastMessage("Error!");
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            activity.toastMessage("Tidak ada Layanan!");
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            activity.toastMessage("Null PDU!");
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            activity.toastMessage("Radio Off!");
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            activity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode()) {
                        case SendSMSActivity.RESULT_OK:
                            activity.toastMessage("SMS Terkirim!");
                            break;
                        case SendSMSActivity.RESULT_CANCELED:
                            activity.toastMessage("SMS TIDAK Terkirim!");
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber.replace(" ", "").replace("-", "").replace("+62", "0"), null, message, sentPI, deliveredPI);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<Sms> getAllSms(MainActivity activity, String folder) {
        ArrayList<Sms> lSMS = new ArrayList<Sms>();
        try {
            Sms data = new Sms();
            Uri folderMessage = Uri.parse("content://sms/" + folder);
            ContentResolver cr = activity.getContentResolver();

            Cursor c = cr.query(folderMessage, null, null, null, null);
            activity.startManagingCursor(c);
            int totalSMS = c.getCount();

            if (c.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {

                    data = new Sms();
                    data.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                    data.setPhoneNumber(c.getString(c.getColumnIndexOrThrow("address")));
                    data.setMessage(c.getString(c.getColumnIndexOrThrow("body")));
                    data.setReadFlag(c.getString(c.getColumnIndex("read")));
                    data.setDate(Helper.millisToDate(Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")))));

                    lSMS.add(data);
                    c.moveToNext();
                }
            }

            if (c.isClosed())
                c.close();
        } catch (Exception e) {
            lSMS = null;
        }
        return lSMS;
    }
}
