package com.example.mypython.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypython.MainActivity;
import com.example.mypython.R;
import com.example.mypython.WebActivity.WebViewByUrl;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    List<Map<Integer, List<String>>> carListInfo;
    LayoutInflater inflater;
    private Context context;

    public MyAdapter(Context context) {
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    public void setList(List<Map<Integer, List<String>>> carListInfo) {
        this.carListInfo=carListInfo;
    }

    @Override
    public int getCount() {
        return carListInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return carListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_info_main,null);
            holder = new ViewHolder();
            holder.linearLayout=convertView.findViewById(R.id.liner_layout);
            holder.carName=convertView.findViewById(R.id.car_name);
            holder.carMoney=convertView.findViewById(R.id.car_money);
            holder.carPicture=convertView.findViewById(R.id.car_picture);
            holder.carFroum=convertView.findViewById(R.id.car_froum);
            convertView.setTag(holder);

        }else {
            convertView.clearAnimation();
            holder=(ViewHolder) convertView.getTag();
        }
        holder.carName.setText(carListInfo.get(position).get(position).get(0));
        holder.carMoney.setText(carListInfo.get(position).get(position).get(2));
        holder.carPicture.setText(carListInfo.get(position).get(position).get(4));
        holder.carFroum.setText(carListInfo.get(position).get(position).get(6));



        holder.carMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent intent = new Intent(context, WebViewByUrl.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",carListInfo.get(position).get(position).get(3));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.carPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent intent = new Intent(context, WebViewByUrl.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",carListInfo.get(position).get(position).get(5));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.carFroum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent intent = new Intent(context, WebViewByUrl.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",carListInfo.get(position).get(position).get(7));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });



        return convertView;
    }
    static class ViewHolder{
        LinearLayout linearLayout;
        TextView carName;
        TextView carMoney;
        TextView carPicture;
        TextView carFroum;
    }

}
