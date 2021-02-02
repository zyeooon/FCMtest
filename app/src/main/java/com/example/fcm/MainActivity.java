package com.example.fcm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    List<NameValuePair> nameValuePairs;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvToken = (TextView) findViewById(R.id.textView);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //FirebaseInstanceId.getInstance().getToken();
        new Thread(new Runnable() {
            @Override
            public void run() {
                token();
            }
        }).start();
    }
    public void token(){
        try {

            String token = FirebaseInstanceId.getInstance().getToken();
            //TextView tvToken = (TextView) findViewById(R.id.textView);
            //tvToken.setText(token);

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://192.168.219.161/register.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "2"));
            nameValuePairs.add(new BasicNameValuePair("Token", token));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

        }catch (IOException e){
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
