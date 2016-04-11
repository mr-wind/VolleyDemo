package example.android.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Data> mDataArrayList = new ArrayList<Data>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.w("TAG", s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.w("TAG",volleyError.getMessage(), volleyError);
                    }
                });
        RequestQueue mQueue = Volley.newRequestQueue(this);
        //mQueue.add(stringRequest);

        //"http://192.168.191.1/get_data.json"这个参数是自己建的服务器，服务返回的是json数组
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.191.1/get_data.json", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.w("MyTag", jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.w("MyTag",volleyError.getMessage(), volleyError);
                    }
                });
        //mQueue.add(jsonObjectRequest);

        //"http://192.168.191.1/get_data.json"这个参数是自己建的服务器，服务返回的是json数组
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.191.1/get_data.json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Data data = new Data();
                                data.setId(jsonObject.getInt("id"));
                                data.setVersion(jsonObject.getDouble("version"));
                                data.setName(jsonObject.getString("name"));
                                //Log.w("TestTag",data.getName() + data.getId() + data.getVersion());
                                //Log.w("TestTag", data.toString());
                                mDataArrayList.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.w("TestTag",mDataArrayList.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mQueue.add(jsonArrayRequest);
    }
}
