package example.android.volleydemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    private List<Data> mDataArrayList = new ArrayList<Data>();

    private ImageView imageView;

    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.image_view);
        networkImageView = (NetworkImageView)findViewById(R.id.network_image_view);
        mQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.w("TAG", s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.w("TAG", volleyError.getMessage(), volleyError);
                    }
                });

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
                        Log.w("MyTag", volleyError.getMessage(), volleyError);
                    }
                });
        //mQueue.add(jsonObjectRequest);

        //"http://192.168.191.1/get_data.json"这个参数是自己建的服务器，服务返回的是json数组
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.191.1/get_data.json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
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
                        Log.w("TestTag", mDataArrayList.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mQueue.add(jsonArrayRequest);


    }

    /**
     * 使用ImageRequest加载图片<br/>
     * 获取imageRequest实例，设置6个参数<br/>
     * 添加到RequestQueue中
     * */
    public void imageRequestHandler(View view){
        ImageRequest imageRequest = new ImageRequest(
                "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                    }
                });

        mQueue.add(imageRequest);
    }

    /**
     * 使用loadImage加载图片<br/>
     * 创建ImageLoader对象，new 一个实例，传入RequestQueue对象和ImageCache对象<br/>
     * 创建监听ImageLoader.ImageListener,并获取ImageLoader的监听，参数为imageview，默认图，错误图</br>
     * 最后使用imageloader.get(url，listener) ，传入网络地址和监听器
     * */
    public void loadImage(View view){
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.ic_aspect_ratio_black_48dp, R.drawable.ic_mood_bad_black_24dp);

        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener);
    }

    /**
     * 使用NetworkImageView自定义控件<br/>
     * 创建imageloader对象，new 时 传入RequestQueue对象和imageCache对象<br/>
     * 通过控件的set，设置默认图片和错误图片<br/>
     * 通过set设置图片url和imageloader
     * */
    public void loadNetworkImage(View view){

        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        networkImageView.setDefaultImageResId(R.drawable.ic_aspect_ratio_black_48dp);
        networkImageView.setErrorImageResId(R.drawable.ic_mood_bad_black_24dp);
        networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                imageLoader);
    }

}
