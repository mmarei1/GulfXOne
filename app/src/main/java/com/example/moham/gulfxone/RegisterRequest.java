package com.example.moham.gulfxone;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by moham on 26/05/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://greatbigthing.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest( String username, String password, String email,  Response.Listener<String> listener)
    {
        // Execute a post request to the URL with an error listener of null
        // <TODO: modify to include validation>
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
