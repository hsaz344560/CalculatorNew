package com.example.calculatornew;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textview;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏通知栏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
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
        Button buttonDel = findViewById(R.id.btn_del);
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
        Button buttonFun = findViewById(R.id.btn_fun);
        Button buttonExi = findViewById(R.id.btn_exit);
        textview = findViewById(R.id.text_view);

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
        buttonExi.setOnClickListener(this);

        /*
        Button btn_fun = findViewById(R.id.btn_fun);
        //设置——点击事件
        btn_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

         */

    }

    private int count_negative = 0; //负数标记
    private boolean equals = false;
    private int count_bracket_left = 0;
    private int count_bracket_right = 0;

    //@SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view){
        switch (view.getId()){
            //当equals为true时，输入数字，清空字符串，标准转换为false
            case R.id.btn_0:
                if (equals){
                    sb = sb.delete(0,sb.length());
                    equals = false;
                }
                sb = sb.append("0");
                textview.setText(sb.toString());
                break;
            case R.id.btn_1:
                if(equals){
                    sb = sb.delete(0,sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*1");
                } else {
                    sb = sb.append("1");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_2:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*2");
                } else {
                    sb = sb.append("2");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_3:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*3");
                } else {
                    sb = sb.append("3");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_4:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*4");
                } else {
                    sb = sb.append("4");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_5:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*5");
                } else {
                    sb = sb.append("5");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_6:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*6");
                } else {
                    sb = sb.append("6");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_7:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*7");
                } else {
                    sb = sb.append("7");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_8:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*8");
                } else {
                    sb = sb.append("8");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_9:
                if (equals) {
                    sb = sb.delete(0, sb.length());
                    equals = false;
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
                    sb = sb.append("*9");
                } else {
                    sb = sb.append("9");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_reset:
                if (equals) {
                    equals = false;
                }
                sb = sb.delete(0, sb.length());
                textview.setText(sb.toString());
                break;
            case R.id.btn_del:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_left:
                if (equals) {
                    equals = false;
                }
                if (sb.length() > 0 && (sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9')) { //当前面是数字是，自动添加为'*('
                    sb = sb.append("*(");
                }
                if (sb.length() == 0) { //如果此时字符串是空的，也就是说想在式子最前面添加括号，就添加括号
                    sb = sb.append("(");
                }
                if (sb.length() > 0 && (sb.charAt(sb.length() - 1) == '*' || sb.charAt(sb.length() - 1) == '/' || sb.charAt(sb.length() - 1) == '+' || sb.charAt(sb.length() - 1) == '-')) { //如果当括号前面是符号时添加括号
                    sb = sb.append("(");
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_right:
                if (equals) {
                    equals = false;
                }
                int count_num = 0; //数字的数目
                count_bracket_left = count_bracket_right = 0;
                if (sb.length() != 0) {
                    for (int i = sb.length() - 1; i >= 0; i--) { //对字符串进行遍历，如果存在左括号且括号中有数字，标记转为真,
                        if (count_bracket_left == 0 && (sb.charAt(i) >= '0' && sb.charAt(i) <= '9')) {
                            count_num++;
                        }
                        if (sb.charAt(i) == '(') {
                            count_bracket_left++;
                        }
                        if (sb.charAt(i) == ')') {
                            count_bracket_right++;
                        }
                    }
                    Log.d("count_bracket", String.valueOf(count_bracket_left+" "+count_bracket_right));
                    if ((count_bracket_left > count_bracket_right) && count_num > 0) { //当标记均为真，也就是存在左括号时且在左括号前面有数字，才让添加括号
                        sb = sb.append(")");
                    }
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_add:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') || sb.charAt(sb.length() - 1) == '.') {
                        if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9')) {//如果前一位是数字，就直接添加
                            // if (count_negative > 0) { //如果前面是负数，就加上括号
                            //     sb = sb.append(")+");
                            //     count_negative = 0;
                            // } else {
                            sb = sb.append("+");
                            // }
                        }
                        if (sb.charAt(sb.length() - 1) == '.') {//如果前一位是'.',就先为前一位数字补0
                            sb = sb.append("0+");
                        }
                    }
                    if ((sb.charAt(sb.length() - 1) == ')')) {
                        sb = sb.append("+");
                    }
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_subtract:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') || sb.charAt(sb.length() - 1) == '.' ||
                            sb.charAt(sb.length() - 1) == '(') {
                        if (sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') {//如果前一位是数字，就直接添加
                            // if (count_negative > 0) { //如果前面是负数，就加上括号
                            //     sb = sb.append(")-");
                            //     count_negative = 0;
                            // } else {
                            sb = sb.append("-");
                            // }
                        }
                        if (sb.charAt(sb.length() - 1) == '.') {//如果前一位是'.',就先为前一位数字补0
                            sb = sb.append("0-");
                        }
                        if (sb.charAt(sb.length() - 1) == '(') {
                            sb = sb.append("-");
                            count_negative++;
                        }

                    } else if ((sb.charAt(sb.length() - 1) == ')')) {
                        sb = sb.append("-");
                    } else {
                        sb = sb.append("(-");
                    }

                } else { //负号
                    sb = sb.append("(-");
                    count_negative++;
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_multiply:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') || sb.charAt(sb.length() - 1) == '.') {
                        if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9')) {//如果前一位是数字，就直接添加
                            sb = sb.append("*");
                        }
                        if (sb.charAt(sb.length() - 1) == '.') {//如果前一位是'.',就先为前一位数字补0
                            sb = sb.append("0*");
                        }
                    }
                    if ((sb.charAt(sb.length() - 1) == ')')) {
                        sb = sb.append("*");
                    }
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_divide:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') || sb.charAt(sb.length() - 1) == '.') {
                        if ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9')) { //如果前一位是数字，就直接添加
                            // if (count_negative > 0){ //如果前面是负数，就加上括号
                            //     sb = sb.append(")/");
                            //     count_negative = 0;
                            // } else {
                            sb = sb.append("/");
                            // }
                        }
                        if (sb.charAt(sb.length() - 1) == '.') { //如果前一位是'.',就先为前一位数字补0
                            sb = sb.append("0/");
                        }
                    }
                    if ((sb.charAt(sb.length() - 1) == ')')) { //如果前一位是')'也让加上/
                        sb = sb.append("/");
                    }
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_point:
                if (equals) {
                    equals = false;
                }
                if (sb.length() != 0) {
                    int count_dot = 0;
                    for (int i = sb.length() - 1; i >= 0; i--) {
                        if (sb.charAt(i) == '.') {
                            count_dot++;
                        }
                        if (!(sb.charAt(i) >= '0' && sb.charAt(i) <= '9')) {
                            break;
                        }
                    }
                    if (count_dot == 0) {
                        if (sb.charAt(sb.length() - 1) == '*' || sb.charAt(sb.length() - 1) == '/' || sb.charAt(sb.length() - 1) == '+' || sb.charAt(sb.length() - 1) == '-') {
                            // 如果最后一位是符号时，直接输小数点会自动补'0'，形成'0.'
                            sb = sb.append("0.");
                        } else {
                            sb = sb.append(".");
                        }
                    }
                }
                textview.setText(sb.toString());
                break;
            case R.id.btn_equal:
                if (equals) {
                    equals = false;
                }
                count_bracket_right = count_bracket_left = 0;
                if (sb.length() != 0) {
                    for (int i = 0; i < sb.length(); i++) {
                        if (sb.charAt(i) == '(')
                            count_bracket_left++;
                        if (sb.charAt(i) == ')')
                            count_bracket_right++;
                    }
                    if (count_bracket_left != count_bracket_right) {
                        Toast.makeText(MainActivity.this, "请注意括号匹配", Toast.LENGTH_SHORT).show();
                    }
                    if (count_bracket_left == count_bracket_right &&
                            ((sb.charAt(sb.length() - 1) >= '0' && sb.charAt(sb.length() - 1) <= '9') || sb.charAt(sb.length() - 1) == ')')) {
                        equals = true;
                        count_negative = 0;
                        try {
                            textview.setText(InfixToSuffix.Cal(InfixToSuffix.Suffix(sb)));
                            sb = sb.delete(0, sb.length());
                            sb.append(textview.getText().toString());
                        } catch (Exception e){
                            textview.setText("Error");
                            sb = sb.delete(0, sb.length());
                        }
                    }
                }
                break;
            case R.id.btn_fun:
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            //case R.id.btn_exit:


            default:
                break;

        }
    }



}
