package com.example.moham.gulfxone;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by moham on 26/05/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "localhost/server/Register.php";;
    private Map<String, String> params;

    public RegisterRequest(
            String email,
            String username,
            String password,
            String fingerprint,
            String gloc,
            String ipaddr,
            String mPhoneNumber,
            String mSerialNumber,
            String mModel,
            Response.Listener<String> listener)
        {
        // Execute a post request to the URL with an error listener of null
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
            params.put("fingerprint", fingerprint);
            params.put("gloc", gloc);
            params.put("ipaddr", ipaddr);
            params.put("mPhoneNumber", mPhoneNumber);
            params.put("mSerialNumber",mSerialNumber);
            params.put("mModel",mModel);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
