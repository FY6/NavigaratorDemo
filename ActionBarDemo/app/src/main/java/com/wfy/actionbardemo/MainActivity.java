package com.wfy.actionbardemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wfy.actionbardemo.domain.TabData;
import com.wfy.actionbardemo.fragments.FirstFragment;
import com.wfy.actionbardemo.fragments.FiveFragment;
import com.wfy.actionbardemo.fragments.FourFragment;
import com.wfy.actionbardemo.fragments.SecondFragment;
import com.wfy.actionbardemo.fragments.ThreeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.wfy.actionbardemo.R.mipmap.ic_action_cloud;

/**
 * DrawerLayout，ActionBar以及ActionBar.Tab的Demo
 */
public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;
    private DrawerLayout drawer;
    private ListView mListView;
    private ViewPager mViewPager;
    private PagerTitleStrip pager_title_strip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        fm = getSupportFragmentManager();


//        pager_title_strip.setScrollIndicators();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
//        generateTabItem(actionBar, tabData);
        generateTabItem(actionBar);

        //抽屉
        mListView.setAdapter(new SimpleAdapter(this, data,
                R.layout.drawer_list_item, new String[]{"name", "img"}, new int[]{
                R.id.tv_navi, R.id.iv_navi
        }));

        //ViewPager
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));

        initListener();
    }

    //ViewPager适配器,每一个Fragment一离开屏幕，马上被回收
    static class TabAdapter extends FragmentStatePagerAdapter {
        static List<Fragment> fragments;

        static {
            fragments = new LinkedList<Fragment>();
            fragments.add(new FirstFragment());
            fragments.add(new SecondFragment());
            fragments.add(new ThreeFragment());
            fragments.add(new FourFragment());
            fragments.add(new FiveFragment());
        }

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(position);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "标签" + position;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    //初始化Listener
    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "导航 " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        });
    }

    //初始化View
    private void initViews() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setVerticalScrollBarEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        pager_title_strip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
    }

    //生成TabItam
    private void generateTabItem(ActionBar actionBar, List<TabData> tabData) {

        for (int i = 0; i < tabData.size(); i++) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setTabListener(new MyTabListener());

            View view = View.inflate(this, R.layout.tab1_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_text);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);

            tv.setText(tabData.get(i).tabName);

            tab.setCustomView(view);
            actionBar.addTab(tab);
        }

    }

    private List<TabData> tabData;//标签数据
    private String[] navigationNames = new String[]{
            "导航一", "导航二", "导航三", "导航四", "导航五", "导航六", "导航七", "导航八", "导航九", "导航十"
    };
    private int[] navigationIcons = new int[]{
            ic_action_cloud, R.mipmap.ic_action_collection, R.mipmap.ic_action_important,
            ic_action_cloud, R.mipmap.ic_action_collection, R.mipmap.ic_action_important
            , R.mipmap.ic_action_collection, R.mipmap.ic_action_cloud, R.mipmap.ic_action_cloud,
            R.mipmap.ic_action_collection
    };

    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    //初始化数据
    private void initData() {
        tabData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TabData td = new TabData(R.mipmap.ic_action_search, "标签" + i);
            tabData.add(td);
        }

        for (int i = 0; i < navigationNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", navigationNames[i]);
            map.put("img", navigationIcons[i]);
            data.add(map);
        }
    }

    /**
     * 生成标签项
     *
     * @param actionBar
     */
    private void generateTabItem(ActionBar actionBar) {

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setTabListener(new MyTabListener());
        tab1.setText("标签一");
        tab1.setIcon(R.mipmap.ic_action_cloud);
//        tab1.setCustomView(R.layout.tab1_layout);
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setIcon(R.mipmap.ic_action_collection);
        tab2.setText("标签二").setTabListener(new MyTabListener());
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setIcon(R.mipmap.ic_action_important);
        tab3.setText("标签三").setTabListener(new MyTabListener());
        actionBar.addTab(tab3);

        ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setIcon(R.mipmap.ic_action_important);
        tab4.setText("标签四").setTabListener(new MyTabListener());
        actionBar.addTab(tab4);

        ActionBar.Tab tab5 = actionBar.newTab();
        tab5.setIcon(R.mipmap.ic_action_collection);
        tab5.setText("标签五").setTabListener(new MyTabListener());
        actionBar.addTab(tab5);
    }

    //Tab监听器
    private class MyTabListener implements ActionBar.TabListener {

        //标签被选中
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ftt) {
            mViewPager.setCurrentItem(tab.getPosition());

           /* LinearLayout ll = (LinearLayout) tab.getCustomView();
            if (ll != null) {
                TextView tv = (TextView) ll.findViewById(R.id.tv_text);
                Toast.makeText(MainActivity.this, tv.getText() + "---选中",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, tab.getText() + "---选中",
                        Toast.LENGTH_SHORT).show();
            }*/
           /* ft = fm.beginTransaction();
            switch (tab.getPosition()) {
                case 0:
                    ft.replace(R.id.fl_content_main, new FirstFragment()).commit();
                    break;
                case 1:
                    ft.replace(R.id.fl_content_main, new SecondFragment()).commit();
                    break;
                case 2:
                    ft.replace(R.id.fl_content_main, new ThreeFragment()).commit();
                    break;
                case 3:
                    ft.replace(R.id.fl_content_main, new FourFragment()).commit();
                    break;
                case 4:
                    ft.replace(R.id.fl_content_main, new FiveFragment()).commit();
                    break;
            }*/
        }

        //标签没有被选中
        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        //标签被重新选中
        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(this, "牛逼", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
