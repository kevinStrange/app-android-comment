package com.comment.tek.activity.login;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.comment.tek.activity.R;
import com.comment.tek.base.BaseActivity;

/**
 * 用户注册界面
 * Created by huanghongfa on 2018/8/23.
 */

@SuppressLint("Registered")
public class RegisterActivity extends BaseActivity {

    private EditText mEtPwd;
    private ImageView mIvClear;
    private boolean isSelect = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initObj() {

    }

    @Override
    protected void initView() {
        findView(R.id.etRegisterPhoneNumber);
        mEtPwd = findView(R.id.etRegisterPwd);
        mIvClear = findView(R.id.register_clear);
        findView(R.id.register_psw_control);
        setOnClickListener(this, R.id.register_clear, R.id.register_psw_control, R.id.btnRegister);
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    mIvClear.setVisibility(View.INVISIBLE);
                else
                    mIvClear.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                toast("注册成功");
                break;
            case R.id.register_clear:
                mEtPwd.setText("");
                mIvClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.register_psw_control:
                ImageView views = (ImageView) view;
                if (!isSelect) {
                    isSelect = true;
                    views.setSelected(true);
                    mEtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isSelect = false;
                    views.setSelected(false);
                    mEtPwd.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }
}
