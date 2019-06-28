package com.example.mypython;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mypython.util.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends Activity {
    private String netWorkUrl="https://www.autohome.com.cn";
    private String bykey="box";


    private ImageView imageView;

    private Map <Integer ,String > carNameListDate;
    private Map <Integer ,String > carHrefListDate;


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
        getDateByNetWrok(netWorkUrl,bykey);
        setUpPictures();
    }
    private void getDateByNetWrok(final String url, final String byKey){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //创建数组对象
                    carNameListDate = new ArrayMap<>();
                    carHrefListDate= new ArrayMap<>();
                    //通过url 访问 获取html网页
                    Document document =  Jsoup.connect(url).get();
                    //需求通过id/或class获取相关数据
                    Elements elements = document.getElementsByClass(byKey);
                    for (int i=0;i<elements.size();i++){
                        Elements el = elements.get(i).select("a");
                        for (int h=0;h<el.size();h=h+4){
                            Elements a1 = el.get(0).select("a");
                            String h1 = a1.attr("href");
                            //正则表达获取文字
//                            String patternTag = "</?[a-zA-Z]+[^><]*>";
//                            String patternBlank = "(^\\s*)|(\\s*$)";
//                            String a11=String.valueOf(a1).replaceAll(patternTag,"").replaceAll(patternBlank,"");
                            //直接获取文字
                            String a11=a1.text();
                            carNameListDate.put(i,a11);
                            carNameListDate.clear();


                            Elements a2 = el.get(1).select("a");
                            String h2 = "http:"+a2.attr("href");
                            carHrefListDate.put(i,h2);
                            //System.out.println(h2);
                            carHrefListDate.clear();

                            Elements a3 = el.get(2).select("a");
                            String h3 = a2.attr("href");

                            Elements a4 = el.get(3).select("a");
                            String h4 = a4.attr("href");

                            //System.out.println(a1+"==="+a2+"======="+a3+"========"+a4);
                            //System.out.println(h1+"==="+h2+"======="+h3+"================"+h4);

                        }
                        String href = el.attr("href");
                        //System.out.println(el);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    //设置一张图片试试看
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
                System.out.println(bingPic);
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
