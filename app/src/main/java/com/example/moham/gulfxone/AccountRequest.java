package com.example.moham.gulfxone;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moham on 26/05/2017.
 */

public class AccountRequest extends StringRequest {
    private static final String ACCOUNT_REQUEST_URL = "localhost/server/Account.php";
    private Map<String, String> params;

    public AccountRequest(String ip_addr, String phone_number, String gloc, String serial_number, String model_number,
                          Response.Listener<String> listener)
    {
        // Execute a post request to store user credentials
        super(Request.Method.POST, ACCOUNT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("ip_addr", ip_addr);
        params.put("phone_number", phone_number);
        params.put("gloc", gloc);
        params.put("serial_number",serial_number);
        params.put("model_number",model_number);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}

