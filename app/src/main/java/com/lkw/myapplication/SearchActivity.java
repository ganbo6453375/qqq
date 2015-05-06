package com.lkw.myapplication;

import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lkw.myapplication.R;
import com.lkw.myapplication.bean.SearchSave;
import com.lkw.myapplication.util.DbHelper;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {
    private Button btn1, btn2, btn3, btn4, btn5;
    private String[] btnStr = {"明星", "设计", "原创", "DIY", "公益", "演唱会", "有机", "爱心", "首发", "创业", "智能", "手工", "图书"};
    private List<Integer> list = new ArrayList<>();
    private ListView lv;
    private SearchView searchView;
    private List<SearchSave> listData = new ArrayList<>();
    private SearchLvAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initBtn();//实例化5个Button

        searchView = (SearchView) findViewById(R.id.searchInput);
        lv = (ListView) findViewById(R.id.searchHistory);
        //实例化工具类
        DbHelper.init(this);

        if (adapter != null) {
          adapter.notifyDataSetChanged();
        }else{
           adapter = new SearchLvAdapter();
        }
        lv.setAdapter(adapter);
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
    }

    private void initBtn() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        for (int i = 0; i < 5; i++) {
            int num = (int) (Math.random() * (12 - 0 + 1) + 0);
            if (list.contains(num)) {
                i--;
            } else {
                list.add(num);
            }
        }
//        Log.d("!!!!list-->",list.toString());
        btn1.setText(btnStr[list.get(0)]);
        btn2.setText(btnStr[list.get(1)]);
        btn3.setText(btnStr[list.get(2)]);
        btn4.setText(btnStr[list.get(3)]);
        btn5.setText(btnStr[list.get(4)]);
    }

    // 单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        //向数据库添加数据
        if (query != null) {
            SearchSave data = new SearchSave();
            data.setText(query);
//            Log.d("!!!!onQueryTextSubmit-->", query);
            listData.add(data);
        }
        try {
            DbHelper.getUtils().saveOrUpdateAll(listData);
            handler.sendEmptyMessage(0);
        } catch (DbException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    class SearchLvAdapter extends BaseAdapter {
        private List<SearchSave> listSave = new ArrayList<>();

        private void getSearchSaveData() {
            try {
                listSave.clear();
                Selector selector = Selector.from(SearchSave.class);
                listSave = DbHelper.getUtils().findAll(selector);
//                Log.d("!!!!SearchLvAdpater-->", listSave.size() + "");
            } catch (DbException e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getCount() {
            getSearchSaveData();
//            Log.d("!!!!SearchLvAdpater--getCount()-->", listSave.size() + "");
            if (listSave.size() != 0) {
                return listSave.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return listSave.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            SearchSave searchSave = listSave.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_lv_item, parent, false);
            }
            TextView text = (TextView) convertView.findViewById(R.id.search_lv_text);
            text.setText(searchSave.getText().toString());
            ImageButton imgv = (ImageButton) convertView.findViewById(R.id.search_lv_delet_img);

            final int id1 = searchSave.getId();
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DbHelper.getUtils().deleteById(SearchSave.class, id1);
                        getSearchSaveData();
                        handler.sendEmptyMessage(0);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            });
            return convertView;
        }
    }
}
