package com.example.moham.gulfxone;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moham on 26/05/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://greatbigthing.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String ulogin, String ulogin_type, String password, Response.Listener<String> listener)
    {
        // Execute a post request to the URL with an error listener of null
        // <TODO: modify to include validation>
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("ulogin", ulogin);
        params.put("ulogin_type", ulogin_type);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
