package com.example.moham.gulfxone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramSocket;


import java.net.NetworkInterface;
import java.util.Enumeration;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI Element References: Declaration
        final EditText etEmailUsr = (EditText) findViewById(R.id.etEmailUsr);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final ImageButton btnFingerprint = (ImageButton) findViewById(R.id.btnFingerprint);
        final TextView registerLink = (TextView) findViewById(R.id.linkToRegister);

        // Temporarily remove fingerprint authentication feature.
        btnFingerprint.setVisibility(View.GONE);
        /*if (Build.VERSION.SDK_INT < 23) {
            btnFingerprint.setVisibility(View.GONE);
        } else {
            Context context = this;
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

            // Just checking if the device has this sensor. No post-authentication required for now
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                btnFingerprint.setVisibility(View.GONE);
                return;
            }
            // If hardware is missing, remove button
            if (!fingerprintManager.isHardwareDetected()) {
                btnFingerprint.setVisibility(View.GONE);
                // otherwise show button
            } else {
                btnFingerprint.setVisibility(View.GONE);
            }
        }

        // Go to fingerprint authentication activity on click
        btnFingerprint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent fingerprintIntent = new Intent(LoginActivity.this, FingerprintActivity.class);
                LoginActivity.this.startActivity(fingerprintIntent);

            }
        });*/

        // Start Register Activity on click
        registerLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String username = etEmailUsr.getText().toString();
                final String email = etEmailUsr.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                String fullname = jsonResponse.getString("fullname");
                                String email = jsonResponse.getString("email");

                                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);

                                LoginActivity.this.startActivity(intent);

                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Error: Username or Password incorrect.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, email, password, responseListener);
            }
        });
    }
}
