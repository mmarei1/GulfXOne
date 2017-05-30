package com.example.moham.gulfxone;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // UI Element References: Declaration
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPasswordC = (EditText) findViewById(R.id.etPasswordC);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);

        final String username = etUsername.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String passwordC = etPasswordC.getText().toString();


        // Step 1: verify password is the same as confirmed password meets conditions
        List<String> errorList = new ArrayList<String>();

        // Call to password authentication function and printing out resulting errors
        if (!isValid(password, passwordC, errorList)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            String bm = "Registration Error:\r\n";
            for (String error : errorList) {
                bm += error;
                bm += "\r\n";
            }
            // Create an error message with all password errors
            builder.setMessage(bm)
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }

        // Try to generate secret key; catch exceptions
        SecretKey secretKey = null;
        try {
            secretKey = EncryptorDecryptor.generateKey();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        // try to encrypt password using secret key; catch exceptions
        try {
            EncryptorDecryptor.encryptMsg(password, secretKey);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | InvalidParameterSpecException
                | IllegalBlockSizeException
                | BadPaddingException
                | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        // Register Request using successful encryption result
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // If JSONResponse creation unsuccessful, throw an exception
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // If successful, take the user back to the login page
                            if (success) {
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(loginIntent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Error")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(username, email, password,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
}
    // Password Validation using regexp patterns to identify missing characters and ensure correct string length
    public static boolean isValid(String password, String passwordC, List<String> errorList) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        errorList.clear();

        boolean flag = true;

        if (!password.equals(passwordC)) {
            errorList.add("password and confirm password do not match");
            flag = false;
        }
        if (password.length() < 8) {
            errorList.add("Password must have at least 8 characters");
            flag = false;
        }
        if (!specailCharPatten.matcher(password).find()) {
            errorList.add("Password must have at least one specail character");
            flag = false;
        }
        if (!UpperCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one uppercase character");
            flag = false;
        }
        if (!lowerCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one lowercase character");
            flag = false;
        }
        if (!digitCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one digit character");
            flag = false;
        }
        return flag;
    }

}