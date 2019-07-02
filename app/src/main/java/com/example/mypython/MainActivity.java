package com.example.mypython;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mypython.Adapter.MyAdapter;
import com.example.mypython.util.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends Activity {
    private String netWorkUrl="https://www.autohome.com.cn";
    private String bykey="box";
    public MyAdapter adapter;


    private ImageView imageView;
    private List<Map<Integer, List<String>>> carListInfo = new ArrayList<Map<Integer, List<String>>>();
    private ListView listView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 001:
                    adapter.setList(carListInfo);
                    listView.setAdapter(adapter);
                    break;
                    default:
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.bg_pic_img);
        listView=findViewById(R.id.car_info);
        adapter= new MyAdapter(this);
        getDateByNetWrok(netWorkUrl,bykey);
        setUpPictures();
    }

    //通过爬虫技术获取数据
    private void getDateByNetWrok(final String url, final String byKey){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Map<Integer, List<String>> map=new HashMap<>();
                    List<String> list1 = null;
                    //通过url 访问 获取html网页
                    Document document =  Jsoup.connect(url).get();
                    //需求通过id/或class获取相关数据
                    Elements elements = document.getElementsByClass(byKey);
                    for (int i=0;i<elements.size();i++){
                        Elements el = elements.get(i).select("a");
                        for (int h=0;h<el.size();h=h+4){
                            list1 = new ArrayList<>();
                            Elements a1 = el.get(0).select("a");
                            String h1 = "http://"+a1.attr("href");
                            list1.add(a1.text());
                            list1.add(h1);

                            Elements a2 = el.get(1).select("a");
                            String h2 = "http:"+a2.attr("href");
                            list1.add(a2.text());
                            list1.add(h2);

                            Elements a3 = el.get(2).select("a");
                            String h3 = "http:"+a3.attr("href");
                            list1.add(a3.text());
                            list1.add(h3);

                            Elements a4 = el.get(3).select("a");
                            String h4 = "http:"+a4.attr("href");
                            list1.add(a4.text());
                            list1.add(h4);
                        }
                        map.put(i,list1);
                        carListInfo.add(map);
                    }
                    if (carListInfo.size()>0){
                        Message mgs = new Message();
                        mgs.what=001;
                        handler.sendMessage(mgs);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    //设置一张网络图片
    private void setUpPictures(){
        String bingPicUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sentOkHttpRequest(bingPicUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(bingPic).into(imageView);
                    }
                });
            }
        });

    }
}
