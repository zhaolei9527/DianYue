package com.dianyue.Activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dianyue.Fragment.GaoNewsListFragment;
import com.dianyue.Fragment.MEFragment;
import com.dianyue.Fragment.MyNewsFragment;
import com.dianyue.Fragment.ShouTuFragment;
import com.dianyue.Fragment.TiXianFragment;
import com.dianyue.R;
import com.dianyue.View.CustomViewPager;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.ArrayList;
import java.util.List;

import sakura.bottomtabbar.BottomTabBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.CAMERA)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        try {
                            // 从API11开始android推荐使用android.content.ClipboardManager
                            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText("VavJvm63sZ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(MainActivity.this, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });

        final ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new MyNewsFragment());
        fragments.add(new GaoNewsListFragment());
        fragments.add(new ShouTuFragment());
        fragments.add(new TiXianFragment());
        fragments.add(new MEFragment());

        CustomViewPager viewpager = (CustomViewPager) findViewById(R.id.fl_content);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });
        ((BottomTabBar) findViewById(R.id.BottomTabBar)).setPadding(0, 0, 0, 0);
        ((BottomTabBar) findViewById(R.id.BottomTabBar))
                .initFragmentorViewPager(viewpager)
                .setImgSize(getResources().getDimension(R.dimen.x19), getResources().getDimension(R.dimen.y16))
                .setChangeColor(getResources().getColor(R.color.bgtitle), getResources().getColor(R.color.text666))
                .setDividerHeight(3)
                .isShowDivider(true)
                .setFontSize(12)
                .setDividerColor(getResources().getColor(R.color.bgea))
                .addTabItem("主页", getResources().getDrawable(R.mipmap.tab0_active), getResources().getDrawable(R.mipmap.tab0_grey))
                .addTabItem("高佣金", getResources().getDrawable(R.mipmap.tab1_active), getResources().getDrawable(R.mipmap.tab1_grey))
                .addTabItem("收徒", getResources().getDrawable(R.mipmap.tab2_active), getResources().getDrawable(R.mipmap.tab2_grey))
                .addTabItem("提现", getResources().getDrawable(R.mipmap.tab3_active), getResources().getDrawable(R.mipmap.tab3_gray))
                .addTabItem("我的", getResources().getDrawable(R.mipmap.tab4_active), getResources().getDrawable(R.mipmap.tab4_gray))
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, View view) {
                    }
                })
                .commit();

    }
}
