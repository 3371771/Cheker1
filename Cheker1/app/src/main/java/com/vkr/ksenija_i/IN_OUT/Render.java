package com.vkr.ksenija_i.IN_OUT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Render extends AppCompatActivity {

    String trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);

        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        if (pref.getToken().equals("1")) {
            performTagOperations(getIntent());
        } else {
            Intent intent_login;
            intent_login = new Intent(this, WhoActivity.class);
            startActivity(intent_login);
        }
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        performTagOperations(intent);
//    }

    private void performTagOperations(Intent intent) {
        String action = intent.getAction();
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                Parcelable[] msgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefRecord firstRecord = ((NdefMessage) msgs[0]).getRecords()[0];
                byte[] payload = firstRecord.getPayload();
                int payloadLength = payload.length;
                int langLength = payload[0];
                int textLength = payloadLength - langLength - 1;
                byte[] text = new byte[textLength];
                System.arraycopy(payload, 1 + langLength, text, 0, textLength);

                switch (new String(text)) {
                    case "1":
                        Intent intent2;
                        intent2 = new Intent(this, MorningActivity.class);
                        startActivity(intent2);
                        break;
                    case "Morning":
                        Intent intent3;
                        intent3 = new Intent(this, EveningActivity.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

