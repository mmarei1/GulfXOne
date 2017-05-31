package com.example.moham.gulfxone;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Date;


@SuppressWarnings("deprecation")
public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        final TextView txvUN = (TextView) findViewById(R.id.txvUN);
        final TextView txvFN = (TextView) findViewById(R.id.txvFN);
        final TextView txvDate = (TextView) findViewById(R.id.date_value);
        final TextView txvTime = (TextView) findViewById(R.id.time_value);
        final TextView ip_value = (TextView) findViewById(R.id.ip_value);

        final Button btnLogout = (Button) findViewById(R.id.btnLogout);

        // Session details should be stored in the server as login_instances
        // the current login session should be stored as last_login

        // TODO: Obtain user geolocation, IP address, phone number, smartphone type and serial number
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Context context = this;
        String iIPv4 = "";
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        iIPv4 = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        // This may not work for all devices. It will only work for devices where the number is registered
        // with device details
        final String mPhoneNumber = tMgr.getLine1Number();
        final String mSerialNumber = Build.SERIAL;
        final String mModel = getDeviceName();
        // IP Address
        ip_value.setText(iIPv4);
        final String ipaddr = iIPv4;
        final String gloc = "Jeddah, Saudi Arabia";

        Context gcontext = this.getApplicationContext();

        RequestQueue queue = AppSingleton.getInstance().getRequestQueue();

        String currentDate = DateFormat.getDateTimeInstance().format(new Date());

        txvDate.setText(currentDate);

        // POST request with all user information


        // OnClick Listener to logout user and return to login screen
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(AccountActivity.this, LoginActivity.class);
                AccountActivity.this.startActivity(logoutIntent);
            }
        });
    }

    // Helper method to get device name
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    // helper method to capitalize letter in device name
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}
