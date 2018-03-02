package com.guoyaohua.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guoyaohua.okhttpdemo.Entity.User;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    private EditText et_name;
    private EditText et_psw;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWigit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().isEmpty() || et_psw.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(et_name.getText().toString(), et_psw.getText().toString());
                    Gson gson = new Gson();
                    String jsonstr = gson.toJson(user);
                    String url = "http://10.103.72.217:8080/ServletDemo/LoginServlet";
                    try {
                        post(url, jsonstr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("state", "POST请求失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = inputStream2String(response.body().byteStream());
                Log.i("resp", result);

                if (result.equals("access")) {
                    Log.i("state", "登录成功");
                } else if (result.equals("deny")) {
                    Log.i("state", "登录失败");
                }


            }
        });

    }

    String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private void initWigit() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        login = (Button) findViewById(R.id.login);

    }
}
