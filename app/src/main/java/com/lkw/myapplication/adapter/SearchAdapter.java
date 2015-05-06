package com.lkw.myapplication.adapter;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lkw.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends ActionBarActivity {

    private Spinner spinner_type;
    private String[] data = {"科技", "公益", "出版","娱乐","艺术","农业","全部","商铺","其他","众筹筑屋"};
    private String[] data1 = {"默认排序","最多支持","最多关注","最近更新"};
    private String[] data2 = {"全部状态","筹款中","已成功"};
    private SpinnerAdapter1 adapter;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp2_quanbu);

        spinner_type = ((Spinner) findViewById(R.id.spinner_type));

        adapter = new SpinnerAdapter1();
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    class SpinnerAdapter1 implements SpinnerAdapter {

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (index == position) {
                convertView = LayoutInflater.from(SearchAdapter.this).inflate(R.layout.spinner_item1, null);
                TextView text = (TextView) convertView.findViewById(R.id.spinner_item1_text);
                text.setText(data[position]);
            } else {
                convertView = LayoutInflater.from(SearchAdapter.this).inflate(R.layout.spinner_item2, null);
                TextView text = (TextView) convertView.findViewById(R.id.spinner_item2_text);
                text.setText(data[position]);
            }
            return convertView;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        int count=0;
        ImageView imgv;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SearchAdapter.this).inflate(android.R.layout.simple_spinner_item, null);
            }
            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            text.setText(data[position]);
            LinearLayout viewl = (LinearLayout) convertView.findViewById(R.id.default_item);
            imgv = (ImageView) convertView.findViewById(R.id.spinner_default_imgv);
            imgv.setImageResource(R.drawable.show_arrow);
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
