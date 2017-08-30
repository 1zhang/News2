package com.bawei.news2;

import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.news2.api.Api;
import com.bawei.news2.bean.Bean;
import com.bawei.news2.fragment.F1;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity implements XListView.IXListViewListener {
@ViewInject(R.id.xlv)
    XListView xlv;
    private List<Bean> list;
     public final int  a = 0;
     public final int b=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        x.view().inject(this);
        list = new ArrayList<>();
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
       // 添加左菜单
        setBehindContentView(R.layout.left2);
        getSupportFragmentManager().beginTransaction().replace(R.id.left,new F1()).commit();
        SlidingMenu menu = getSlidingMenu();
        //设置左滑菜单
        menu.setMode(SlidingMenu.LEFT);
        //设置滑动屏幕的范围
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //滑动时作业面的剩余宽度
        menu.setBehindOffsetRes(R.dimen.YK);
        menu.setShadowDrawable(R.drawable.d);
        menu.setFadeDegree(0.3f);
        menu.setShadowWidthRes(R.dimen.activity_vertical_margin);
        RequestParams rp = new RequestParams(Api.URL_N);
        rp.addBodyParameter("key",Api.KEY);
        x.http().post(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject result1 = json.getJSONObject("result");
                    JSONArray data = result1.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject o = (JSONObject) data.get(i);
                        list.add(new Bean(o.optString("title"),o.optString("thumbnail_pic_s")));
                    }
                    Myadapter my = new Myadapter();
                    xlv.setAdapter(my);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if(position%2==0){
                return a;
            }else {
                return b;
            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder v = null;
            ViewHolder2 v2 = null;
            DisplayImageOptions options = new DisplayImageOptions.Builder().build();
            ImageLoaderConfiguration con = new ImageLoaderConfiguration.Builder(MainActivity.this)
                    .defaultDisplayImageOptions(options)
                    .build();
            ImageLoader.getInstance().init(con);
            int type = getItemViewType(position);

            if(convertView==null){

                switch(type){
                    case a:
                        v = new ViewHolder();
                      convertView = View.inflate(MainActivity.this,R.layout.item1,null);
                        v.iv = (ImageView) convertView.findViewById(R.id.iv);
                        v.tv= (TextView) convertView.findViewById(R.id.tv);
                        v.tv.setText(list.get(position).getTitle());
                      ImageLoader.getInstance().displayImage(list.get(position).getImg(),v.iv);
                        convertView.setTag(v);
                    break;
                    case b:
                        v2 = new ViewHolder2();
                        convertView = View.inflate(MainActivity.this,R.layout.item2,null);
                        v2.iv = (ImageView) convertView.findViewById(R.id.iv);
                        v2.tv= (TextView) convertView.findViewById(R.id.tv);
                        v2.tv.setText(list.get(position).getTitle());
                        ImageLoader.getInstance().displayImage(list.get(position).getImg(),v2.iv);
                        convertView.setTag(v2);
                        break;
                }

            }else{
                switch (type) {
                    case a:
                        v = (ViewHolder) convertView.getTag();
                        v.iv = (ImageView) convertView.findViewById(R.id.iv);
                        v.tv= (TextView) convertView.findViewById(R.id.tv);
                        v.tv.setText(list.get(position).getTitle());
                        ImageLoader.getInstance().displayImage(list.get(position).getImg(),v.iv);
                        break;
                    case b:
                        v2 = (ViewHolder2) convertView.getTag();
                        v2.iv = (ImageView) convertView.findViewById(R.id.iv);
                        v2.tv= (TextView) convertView.findViewById(R.id.tv);
                        v2.tv.setText(list.get(position).getTitle());
                        ImageLoader.getInstance().displayImage(list.get(position).getImg(),v2.iv);
                        break;
                }
            }
            return convertView;
        }
    }
    public class ViewHolder{
        public TextView tv;
        public ImageView iv;
    }
    public class ViewHolder2{
        public TextView tv;
        public ImageView iv;
    }
}
