package com.liye.mycontacts.myContacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liye.mycontacts.R;

public class CallPhoneActivity extends Activity {
    Button mBtn,mReturn,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnstar,btnjing,btncha;
    EditText number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);

        mBtn = (Button) this.findViewById(R.id.btn);
        mReturn=(Button) this.findViewById(R.id.btn_return_call);
        mReturn.setOnClickListener(new MyListener() );
        // 设置一个点击事件
        mBtn.setOnClickListener(new MyListener());
        number = (EditText) CallPhoneActivity.this.findViewById(R.id.edt_number);
        btn1 = (Button)this.findViewById(R.id.button1);
        btn2 = (Button)this.findViewById(R.id.button2);
        btn3 = (Button)this.findViewById(R.id.button3);
        btn4 = (Button)this.findViewById(R.id.button4);
        btn5 = (Button)this.findViewById(R.id.button5);
        btn6 = (Button)this.findViewById(R.id.button6);
        btn7 = (Button)this.findViewById(R.id.button7);
        btn8 = (Button)this.findViewById(R.id.button8);
        btn9 = (Button)this.findViewById(R.id.button9);
        btn0 = (Button)this.findViewById(R.id.button0);
        btnjing = (Button)this.findViewById(R.id.buttonjing);
        btnstar = (Button)this.findViewById(R.id.buttonstar);
        btncha = (Button)this.findViewById(R.id.chacha);
        if(number.getText().length() == 0){
            btncha.setVisibility(View.INVISIBLE);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"1");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"2");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"3");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"4");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"5");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"6");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"7");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"8");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"9");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"0");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btnstar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cursor = number.getSelectionStart();
                        number.getText().insert(cursor,"*");
                        btncha.setVisibility(View.VISIBLE);
                    }
                });
        btnjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().insert(cursor,"#");
                btncha.setVisibility(View.VISIBLE);
            }
        });
        btncha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = number.getSelectionStart();
                number.getText().delete(cursor-1, cursor);
                if(number.getText().length() == 0){
                    btncha.setVisibility(View.INVISIBLE);
                }
            }
        });
        number.setInputType(InputType.TYPE_NULL);
    }

    class MyListener implements View.OnClickListener {
        /**
         * 当按钮被点击的时候调用onClick()
         *
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn:
                    EditText number = (EditText) CallPhoneActivity.this
                            .findViewById(R.id.edt_number);
                    // 得到电话号码
                    String callNumber = number.getText().toString();
                    // 打电话的意图
                    Intent intent = new Intent();
                    // Intent.ACTION_CALL打电话的动作
                    intent.setAction(Intent.ACTION_CALL);
                    // uri统一资源标示符
                    intent.setData(Uri.parse("tel:" + callNumber));
                    // 开启一个新的界面
                    startActivity(intent);
                    break;
                case R.id.btn_return_call:
//                    Intent intent1=new Intent(CallPhoneActivity.this, TelephoneActivity.class);
//                    startActivity(intent1);
                    finish();
                    break;
            }
        }

    }
}
