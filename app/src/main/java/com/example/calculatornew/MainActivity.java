package com.example.calculatornew;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * <p>主界面，主要提供计算功能的实现，历史记录查看和界面跳转
 * 其核心计算逻辑通过{@link PostfixExpression}类实现
 * 其中语音播放通过{@link TextToSpeech}包实现
 * 历史记录的查看主要是应用开源数据库框架{@link LitePal}实现
 * </p>
 *
 * @author 张正午
 * @author 康林
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private TextView textview;
    //实例化一个TextToSpeech进行语音播报功能
    /**
     * 实例化一个TextToSpeech进行语音播报功能
     */

    /**
     * 实例化一个不可编辑的文本框
     */
    private EditText edit_text;
    /**
     * 实例化一个可编辑的文本框
     */
    private EditText editText;
    /**
     * 可变字符串，存放算式字符串组合
     */
    private StringBuilder sb = new StringBuilder();
    /**
     * 暂存计算式的字符串
     */
    private String tem_str = new String();
    /**
     *
     */
    private ListView listView;

    //新增*****************************************************************************
    private MediaPlayer mediaPlayer;
    private int sound_tag_shou;
    private int sound_tag_fa;
    private int moudle_tag_shou;
    private int moudle_tag_fa;
    private int moudleTag;



    /**
     * <p>启动活动
     * 初始化界面</p>
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //新增*******************************************************************************

        // 音效信号接收
        sound_tag_shou=getIntent().getIntExtra("voice_main",0);
        sound_tag_fa=sound_tag_shou;

        // 初始化标记数据表
        ThemeTag themetag = new ThemeTag();
        themetag.setMoudleTag(1);
        themetag.setSelectTag(1);
        themetag.save();

        // 获取表中第一行数据，只对第一行进行标记
        ThemeTag first_theme_tag = DataSupport.findFirst(ThemeTag.class);
        moudleTag=first_theme_tag.getMoudleTag();

        // 选择主题，要在设置布局前
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

        //设置布局
        setContentView(R.layout.activity_main);


        //定义控件
        Button button0 = findViewById(R.id.btn_0);
        Button button1 = findViewById(R.id.btn_1);
        Button button2 = findViewById(R.id.btn_2);
        Button button3 = findViewById(R.id.btn_3);
        Button button4 = findViewById(R.id.btn_4);
        Button button5 = findViewById(R.id.btn_5);
        Button button6 = findViewById(R.id.btn_6);
        Button button7 = findViewById(R.id.btn_7);
        Button button8 = findViewById(R.id.btn_8);
        Button button9 = findViewById(R.id.btn_9);
        Button buttonRes = findViewById(R.id.btn_reset);
        ImageButton buttonDel = findViewById(R.id.btn_del);
        Button buttonAdd = findViewById(R.id.btn_add);
        Button buttonSub = findViewById(R.id.btn_subtract);
        Button buttonMul = findViewById(R.id.btn_multiply);
        Button buttonDiv = findViewById(R.id.btn_divide);
        Button buttonLef = findViewById(R.id.btn_left);
        Button buttonRig = findViewById(R.id.btn_right);
        Button buttonPoi = findViewById(R.id.btn_point);
        Button buttonPer = findViewById(R.id.btn_percent);
        Button buttonGen = findViewById(R.id.btn_gen);
        Button buttonFen = findViewById(R.id.btn_fen);
        Button buttonEqu = findViewById(R.id.btn_equal);
        ImageButton buttonFun = findViewById(R.id.btn_fun);

        ImageButton buttonHis = findViewById(R.id.btn_history);
        editText = findViewById(R.id.edit_text);
        edit_text = findViewById(R.id.tv_equation);


        //edit_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        // hidden editText SoftBoard
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }

       // buttonHis.setAlpha(80); //设置历史记录按钮透明度

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonRes.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonLef.setOnClickListener(this);
        buttonRig.setOnClickListener(this);
        buttonPoi.setOnClickListener(this);
        buttonPer.setOnClickListener(this);
        buttonGen.setOnClickListener(this);
        buttonFen.setOnClickListener(this);
        buttonEqu.setOnClickListener(this);
        buttonFun.setOnClickListener(this);

        buttonHis.setOnClickListener(this);
        editText.setOnClickListener(this);


        buttonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                equal = false;
                editText.setText("");
                return true;
            }
        });


    }

    /**
     * 判断是否输出结果的标志
     */
    private boolean equal = false;
    /**
     * 判断输出结果是否有误的标志
     */
    private boolean cle = false;
    /**
     * 左括号的数量，必须与右括号的数量相等才能输出结果
     */
    private int count_bracket_left = 0;
    /**
     * 右括号的数量，必须与左括号的数量相等才能输出结果
     */
    private int count_bracket_right = 0;


    /**
     * <p>设置点击事件
     * 点击按钮的功能逻辑
     * answer按钮调用{@link #getResult()}得出计算结果</p>
     *
     * @param view 传入视图
     */
    @Override
    public void onClick(View view) {
        int index = editText.getSelectionStart();//获取光标所在位置
        Editable edit = editText.getEditableText();//获取EditText的文字
        String text;


        switch (view.getId()) {
            case R.id.edit_text:

                if (equal) {
                    if (edit.charAt(0) == '-') {
                        edit.insert(0, "(");
                        edit.append(")");
                    }
                    equal = false;
                }
                break;
            case R.id.btn_0:

            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                //新增*************************************************************************
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }

                if (equal) {
                    edit_text.setText("");
                    equal = false;
                    edit.clear();
                    index = 0;
                }

                if (index == 0 || index >= edit.length()) {
                    if (index >= edit.length() && index > 1 && (edit.charAt(index - 1) == ')' || edit.charAt(index - 1) == '%')) {
                        text = "×" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    } else if (index == 0 && edit.length() != 0 && (edit.charAt(index) == '(' || edit.charAt(index) == '√')) {
                        text = ((Button) view).getText().toString() + "×";
                        edit.insert(index, text);
                    } else {
                        text = ((Button) view).getText().toString();
                        edit.insert(index, text);
                    }
                } else {
                    if (edit.charAt(index - 1) == ')' || edit.charAt(index - 1) == '%') {
                        text = "×" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    } else if (edit.charAt(index) == '(' || edit.charAt(index) == '√') {
                        text = ((Button) view).getText().toString() + "×";
                        edit.insert(index, text);
                    } else {
                        text = ((Button) view).getText().toString();
                        edit.insert(index, text);
                    }
                }
                break;

            case R.id.btn_reset:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                equal = false;
                edit.clear();
                edit_text.setText("");
                break;

            case R.id.btn_del:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    equal = false;
                    edit_text.setText("");
                    edit.clear();
                    index = 0;
                }
                if (edit.length() != 0 && index > 0) {
                    edit.delete(index - 1, index); // backspace
                }
                break;

            case R.id.btn_point:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    edit_text.setText("");
                    equal = false;
                    edit.clear();
                    index = 0;
                }
                if (edit.length() != 0) {
                    if (index != 0) {
                        int count_dot = 0;
                        for (int i = index - 1; i >= 0; i--) {
                            if (edit.charAt(i) == '.') {
                                count_dot++;
                            }
                            if (!(edit.charAt(i) >= '0' && edit.charAt(i) <= '9')) {
                                break;
                            }
                        }
                        for (int i = index; i <= edit.length() - 1; i++) {
                            if (edit.charAt(i) == '.') {
                                count_dot++;
                            }
                            if (!(edit.charAt(i) >= '0' && edit.charAt(i) <= '9')) {
                                break;
                            }
                        }
                        if (count_dot == 0) {
                            if (edit.charAt(index - 1) == '×' || edit.charAt(index - 1) == '/' || edit.charAt(index - 1) == '+' || edit.charAt(index - 1) == '-'
                                    || edit.charAt(index - 1) == '(' || edit.charAt(index - 1) == '√') {
                                // 如果最后一位是符号时，直接输小数点会自动补'0'，形成'0.'
                                text = "0" + ((Button) view).getText().toString();
                                edit.insert(index, text);
                            } else if (edit.charAt(index - 1) == '%' || edit.charAt(index - 1) == ')') {
                                //最后一位是百分号或者左括号补充乘号
                                text = "×0" + ((Button) view).getText().toString();
                                edit.insert(index, text);

                            } else {
                                text = ((Button) view).getText().toString();
                                edit.insert(index, text);
                            }
                        }
                    } else {
                        int count_dot = 0;
                        for (int i = 0; i <= edit.length() - 1; i++) {
                            if (edit.charAt(i) == '.') {
                                count_dot++;
                            }
                            if (!(edit.charAt(i) >= '0' && edit.charAt(i) <= '9')) {
                                break;
                            }
                        }
                        if (count_dot == 0) {
                            text = "0" + ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                    }
                } else {
                    text = "0" + ((Button) view).getText().toString();
                    edit.insert(index, text);
                }

                break;

            case R.id.btn_left:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    edit_text.setText("");
                    equal = false;
                    edit.clear();
                    index = 0;
                }
                if (index != 0) {
                    if (edit.length() > 0 && (edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9' || edit.charAt(index - 1) == '%' || edit.charAt(index - 1) == ')')) {
                        text = "×" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    } else {
                        text = ((Button) view).getText().toString();
                        edit.insert(index, text);
                    }
                } else {
                    text = ((Button) view).getText().toString();
                    edit.insert(index, text);
                }
                break;

            case R.id.btn_right:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    equal = false;
                    index = 0;
                }
                int count_num = 0; //数字的数目
                count_bracket_left = count_bracket_right = 0;
                if (edit.length() != 0 && index != 0) {
                    for (int i = index - 1; i >= 0; i--) {
                        //对字符串进行遍历，如果存在左括号且括号中有数字，标记转为真,
                        if (count_bracket_left == 0 && (edit.charAt(i) >= '0' && edit.charAt(i) <= '9')) {
                            count_num++;
                        }
                        if (edit.charAt(i) == '(') {
                            count_bracket_left++;
                        }
                        if (edit.charAt(i) == ')') {
                            count_bracket_right++;
                        }
                    }
                    if ((count_bracket_left > count_bracket_right) && count_num > 0) {
                        //当标记均为真，也就是存在左括号时且在左括号前面有数字，才让添加括号
                        if (index < edit.length()) {
                            if (edit.charAt(index) >= '0' && edit.charAt(index) <= '9') {
                                text = ((Button) view).getText().toString() + "×";
                                edit.insert(index, text);
                            } else {
                                text = ((Button) view).getText().toString();
                                edit.insert(index, text);
                            }
                        }
                        if (index == edit.length()) {
                            text = ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                    }
                }
                break;
//************************************************************************************************************
            case R.id.btn_add:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    if (cle) {
                        equal = false;
                        cle = false;
                        edit.clear();
                        edit_text.setText("");
                        index = 0;
                        text = "0" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    } else {
                        edit_text.setText("");
                        if (edit.charAt(0) == '-') {
                            edit.insert(0, "(");
                            edit.append(")");
                        }
                        text = ((Button) view).getText().toString();
                        edit.append(text);
                        equal = false;
                        editText.setSelection(editText.length());//将光标移至文字末尾
                    }

                } else {
                    if (edit.length() != 0 && index > 0) {
                        if (edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9' || edit.charAt(index - 1) == '%' || edit.charAt(index - 1) == ')') {
                            //如果前一位是数字或者%或者），就直接添加
                            text = ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                        if (edit.charAt(index - 1) == '.') {//如果前一位是'.',就先为前一位数字补0
                            text = "0" + ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                        if (index > 1 && index <= edit.length()) {
                            if ((edit.charAt(index - 1) == '+' || edit.charAt(index - 1) == '-' || edit.charAt(index - 1) == '×' || edit.charAt(index - 1) == '/')
                                    && (edit.charAt(index - 2) != '(')) {
                                text = ((Button) view).getText().toString();
                                edit.replace(index - 1, index, text);
                            }
                            if (edit.charAt(index - 1) == '-' && edit.charAt(index - 2) == '(') {
                                edit.delete(index - 1, index);
                            }
                        }
                    } else {
                        text = "0" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    }
                }
                break;

            case R.id.btn_subtract:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    if (cle) {
                        equal = false;
                        cle = false;
                        edit.clear();
                        edit_text.setText("");
                        index = 0;
                        text = "(" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    } else {
                        edit_text.setText("");
                        if (edit.charAt(0) == '-') {
                            edit.insert(0, "(");
                            edit.append(")");
                        }
                        text = ((Button) view).getText().toString();
                        edit.append(text);
                        equal = false;
                        editText.setSelection(editText.length());//将光标移至文字末尾
                    }

                } else {
                    if (edit.length() != 0 && index > 0) {
                        if (edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9' || edit.charAt(index - 1) == '%'
                                || edit.charAt(index - 1) == ')' || edit.charAt(index - 1) == '(') {
                            //如果前一位是数字或者%或者），就直接添加
                            text = ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                        if (edit.charAt(index - 1) == '.') {//如果前一位是'.',就先为前一位数字补0
                            text = "0" + ((Button) view).getText().toString();
                            edit.insert(index, text);
                        }
                        if (index >= 1 && index <= edit.length()) {
                            if (edit.charAt(index - 1) == '+' || edit.charAt(index - 1) == '-' || edit.charAt(index - 1) == '×' || edit.charAt(index - 1) == '/') {
                                text = ((Button) view).getText().toString();
                                edit.replace(index - 1, index, text);
                            }
                        }
                    } else {//无输入负号处理
                        text = "(" + ((Button) view).getText().toString();
                        edit.insert(index, text);
                    }
                }
                break;

            case R.id.btn_percent:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    if (cle == false) {
                        edit_text.setText("");
                        if (edit.charAt(0) == '-') {
                            edit.insert(0, "(");
                            edit.append(")");
                        }
                        text = ((Button) view).getText().toString();
                        edit.append(text);
                        equal = false;
                        editText.setSelection(editText.length());//将光标移至文字末尾
                    } else {
                        cle = false;
                        edit.clear();
                        equal = false;
                        edit_text.setText("");
                    }
                } else {
                    if (edit.length() != 0 && index > 0) {
                        if ((edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9') || edit.charAt(index - 1) == '.' || edit.charAt(index - 1) == ')') {
                            if ((edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9') || edit.charAt(index - 1) == ')') { //如果前一位是数字，就直接添加
                                text = ((Button) view).getText().toString();
                                edit.insert(index, text);
                            } else { //如果前一位是'.',就先为前一位数字补0
                                text = ((Button) view).getText().toString();
                                edit.insert(index, text);
                            }
                        }

                    }
                }
                break;

            case R.id.btn_fen:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    edit_text.setText("");
                    equal = false;
                    edit.clear();
                    index = 0;
                }
                if (index > 0 && (edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9' || edit.charAt(index - 1) == '.' ||
                        edit.charAt(index - 1) == '%' || edit.charAt(index - 1) == ')')) {
                    if (edit.charAt(index - 1) == '.') {
                        text = "0×1/";
                        edit.insert(index, text);
                    } else {
                        text = "×1/";
                        edit.insert(index, text);
                    }
                } else {
                    text = "1/";
                    edit.insert(index, text);
                }
                break;


            case R.id.btn_gen:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    edit_text.setText("");
                    equal = false;
                    edit.clear();
                    index = 0;
                }
                if (index > 0 && (edit.charAt(index - 1) >= '0' && edit.charAt(index - 1) <= '9' || edit.charAt(index - 1) == '.' ||
                        edit.charAt(index - 1) == '%' || edit.charAt(index - 1) == ')')) {
                    if (edit.charAt(index - 1) == '.') {
                        text = "0×" + ((Button) view).getText().toString() + "(";
                        edit.insert(index, text);
                    } else {
                        text = "×" + ((Button) view).getText().toString() + "(";
                        edit.insert(index, text);
                    }
                } else {
                    text = ((Button) view).getText().toString() + "(";
                    edit.insert(index, text);
                }
                break;

            case R.id.btn_equal:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                if (equal) {
                    equal = false;
                }
                count_bracket_right = count_bracket_left = 0;

                if (edit.length() != 0) {
                    for (int i = 0; i < edit.length(); i++) {
                        if (edit.charAt(i) == '(')
                            count_bracket_left++;
                        if (edit.charAt(i) == ')')
                            count_bracket_right++;
                    }
                    if (count_bracket_left != count_bracket_right) {
                        Toast.makeText(MainActivity.this, "请注意括号匹配", Toast.LENGTH_SHORT).show();
                    }
                    if (count_bracket_left == count_bracket_right &&
                            ((edit.charAt(edit.length() - 1) >= '0' && edit.charAt(edit.length() - 1) <= '9') || edit.charAt(edit.length() - 1) == ')' || edit.charAt(edit.length() - 1) == '%' || edit.charAt(edit.length() - 1) == '.')) {
                        equal = true;

                        sb = new StringBuilder(edit);
                        tem_str = "" + edit;
                        //计算结果在getResult方法中得出
                        getResult();

                        //*********************数据库-历史记录********************
                        /*
                        LitePal.getDatabase();
                        HisCal hiscal = new HisCal();
                        hiscal.setEqual(sb.toString());
                        hiscal.setEquation("=");
                        //*********************数据库-内容结束********************


                        //////////////
                        hiscal.setResult(editText.getText().toString());
                        hiscal.save();
                        /////////////

                         */

                    }
                }
                break;


            case R.id.btn_fun:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                //新增********************************************************************************************
                intent.putExtra("voice_set",sound_tag_fa);
                startActivity(intent);
                finish();
                break;
/*
            case R.id.btn_exit:
                finish();
                break;

 */

            case R.id.btn_history:
                if (sound_tag_shou==1){
                    PlayMusic(R.raw.dang);
                }
                showPopupWindow();
                /*
                Intent intentHis = new Intent(MainActivity.this, CalculatorHistory.class);
                startActivity(intentHis);

                 */
                break;

            default:
                break;


        }
    }

    /**
     * <p>计算结果
     * 计算过程存入数据库</p>
     */
    private void getResult() {

        PostfixExpression temp = new PostfixExpression(sb);

        //*********************数据库-历史记录********************
/*
        LitePal.getDatabase();
        HisCal hiscal = new HisCal();
        hiscal.setEqual(sb.toString());
        hiscal.setEquation("=");

 */


        try {

            String str = Double.toString(temp.calculate());     //计算text_input中字符串的结果

            if (str.indexOf(".") > 0) {
                //去掉多余的0
                str = str.replaceAll("0+?$", "");
                //去掉整数后面的小数点及0
                str = str.replaceAll("[.]$", "");
            }

            edit_text.setText(tem_str);
            editText.setText(str);

            //*********************数据库-历史记录********************
            LitePal.getDatabase();
            HisCal hiscal = new HisCal();
            hiscal.setEqual(sb.toString());
            hiscal.setEquation("=");
            hiscal.setResult(editText.getText().toString());
            hiscal.save();
            //*********************数据库-内容结束********************

        } catch (Exception e) {
            //editText.setText("错误");
            cle = true;
            Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_SHORT).show();

        }


        //////////////
        /*
        hiscal.setResult(editText.getText().toString());
        hiscal.save();

         */
        /////////////


    }
    //新增**********************************************************************************
    private void PlayMusic(int MusicId) {
        mediaPlayer = MediaPlayer.create(this, MusicId);
        mediaPlayer.start();
    }


    //*************************************************************************************************
    private void showPopupWindow() {
        //设置contentView


        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.history, null);

        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int height = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow.setHeight(height * 11 / 31);
        //popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.history, null));

        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);


        listView = contentView.findViewById(R.id.cal_his);

        ImageButton btn_hisback = contentView.findViewById(R.id.his_back);
        btn_hisback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        ////////////////////////////////////////////


        //List<HisCal> hisCals = DataSupport.findAll(HisCal.class);

        List<HashMap<String, Object>> list1 = new ArrayList<HashMap<String, Object>>();

        List<HisCal> hisCals = DataSupport.order("id desc").limit(10).find(HisCal.class);

        for (HisCal hisCal : hisCals) {

            HashMap<String, Object> map = new HashMap<String, Object>();

            //数据放入map
            String equation = hisCal.getEquation();
            String equal = hisCal.getEqual();
            String result = hisCal.getResult();
            String suanshu =  equation +result;
            map.put("suanshi", equal);
            map.put("jieguo", suanshu);
            list1.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                //容器
                this,
                //数据
                list1,
                //xml文件位置
                R.layout.calc_history_item,
                //字段名
                new String[]{"suanshi","jieguo"},
                //xml文件中的字段名
                new int[]{R.id.tv1,R.id.tv2});

        listView.setAdapter(adapter);


        ///////////////////////////////////////////
        //显示PopupWindow
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);


    }
}
