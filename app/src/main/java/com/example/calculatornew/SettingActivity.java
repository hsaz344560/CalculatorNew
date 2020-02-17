package com.example.calculatornew;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>设置界面，主要提供主题模式的转换，音效模式的变更功能逻辑的实现
 *
 * 其中通过{@link ExpandableListView}完成折叠列表实例的创建</p>
 *
 * @author 康林
 */
public class SettingActivity extends AppCompatActivity {

    private int sound_tag_shou;
    private int sound_tag_fa;
    private int menu_tag_shou;
    private int menu_tag_fa;
    private int moudle_tag_shou;
    private int moudle_tag_fa;
    private int flag = 0;
    private MediaPlayer mediaPlayer;
    private int moudleTag;
    private int selectTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ThemeTag first_theme_tag_1 = DataSupport.findFirst(ThemeTag.class);
        moudleTag=first_theme_tag_1.getMoudleTag();
        selectTag=first_theme_tag_1.getSelectTag();

        switch (moudleTag){
            case 1:
                setTheme(R.style.MarioTheme_Day);
                break;
            case 2:
                setTheme(R.style.MarioTheme_Night);
                break;
            case 3:
                setTheme(R.style.MarioTheme_Eye);
                break;
        }

        setContentView(R.layout.activity_setting);
        //设置返回键
        ImageButton buttonBack = findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("voice_main",sound_tag_fa);
                startActivity(intent);
                finish();
            }
        });
        //声效Switch控件
        sound_tag_shou = getIntent().getIntExtra("voice_set", 0);
        final Switch soundSwitch = findViewById(R.id.sound_switch);
        if (sound_tag_shou == 1) {
            soundSwitch.setChecked(true);
            sound_tag_fa=1;
        }
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }
                if (soundSwitch.isChecked()) {
                    Toast.makeText(SettingActivity.this, "sound on", Toast.LENGTH_SHORT).show();
                    sound_tag_fa=1;
                } else {
                    Toast.makeText(SettingActivity.this, "sound off", Toast.LENGTH_SHORT).show();
                    sound_tag_fa=0;

                }
            }
        });
        //折叠菜单
        RelativeLayout rel_moudle = findViewById(R.id.lay1);
        final LinearLayout lin_item = findViewById(R.id.lin0);
        final ImageView img_right = findViewById(R.id.item_right1);
        final View down_line = findViewById(R.id.down_line);
        switch (selectTag){
            case 1:
                img_right.setImageResource(R.drawable.qiehuan);
                lin_item.setVisibility(View.GONE);
                down_line.setVisibility(View.VISIBLE);
                break;
            case 2:
                img_right.setImageResource(R.drawable.shangla);
                lin_item.setVisibility(View.VISIBLE);
                down_line.setVisibility(View.GONE);
                break;
        }
        rel_moudle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }
                if (selectTag != 1) {
                    img_right.setImageResource(R.drawable.qiehuan);
                    lin_item.setVisibility(View.GONE);
                    down_line.setVisibility(View.VISIBLE);
                    ThemeTag themetag3 = new ThemeTag();
                    themetag3.setSelectTag(1);
                    themetag3.update(1);
                } else {
                    img_right.setImageResource(R.drawable.shangla);
                    lin_item.setVisibility(View.VISIBLE);
                    down_line.setVisibility(View.GONE);
                    ThemeTag themetag4 = new ThemeTag();
                    themetag4.setSelectTag(2);
                    themetag4.update(1);
                }

            }
        });
        //模式控制switch
        final Switch dayctrl = findViewById(R.id.day_switch);
        final Switch nightctrl = findViewById(R.id.night_switch);
        final Switch eyectrl = findViewById(R.id.eye_switch);

        switch (moudleTag){
            case 1:
                dayctrl.setChecked(true);
                nightctrl.setChecked(false);
                eyectrl.setChecked(false);
                break;
            case 2:
                dayctrl.setChecked(false);
                nightctrl.setChecked(true);
                eyectrl.setChecked(false);
                break;
            case 3:
                dayctrl.setChecked(false);
                nightctrl.setChecked(false);
                eyectrl.setChecked(true);
                break;
        }
        dayctrl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }

                if (dayctrl.isChecked()) {
                    if (nightctrl.isChecked()) {
                        nightctrl.setChecked(false);
                    }
                    if (eyectrl.isChecked()) {
                        eyectrl.setChecked(false);
                    }
                    ThemeTag themetag1 = new ThemeTag();
                    themetag1.setMoudleTag(1);
                    themetag1.update(1);
                }
                if (dayctrl.isPressed()){
                    recreate();
                }
            }

        });
        nightctrl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }

                if (nightctrl.isChecked()) {
                    if (dayctrl.isChecked()) {
                        dayctrl.setChecked(false);
                    }
                    if (eyectrl.isChecked()) {
                        eyectrl.setChecked(false);
                    }
                    ThemeTag themetag2 = new ThemeTag();
                    themetag2.setMoudleTag(2);
                    themetag2.update(1);
                }
                if (nightctrl.isPressed()){
                    recreate();
                }

            }
        });
        eyectrl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound_tag_fa==1){
                    PlayMusic(R.raw.dang);
                }

                if (eyectrl.isChecked()) {
                    if (dayctrl.isChecked()) {
                        dayctrl.setChecked(false);
                    }
                    if (nightctrl.isChecked()) {
                        nightctrl.setChecked(false);
                    }
                    ThemeTag themetag5 = new ThemeTag();
                    themetag5.setMoudleTag(3);
                    themetag5.update(1);
                }
                if (eyectrl.isPressed()){
                    recreate();
                }
            }
        });


    }
    private void PlayMusic(int MusicId) {
        mediaPlayer = MediaPlayer.create(this, MusicId);
        mediaPlayer.start();
    }

}