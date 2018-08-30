package com.comment.tek.activity.login;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.comment.tek.activity.R;
import com.comment.tek.base.BaseActivity;
import com.comment.tek.bean.LoginBean;
import com.comment.tek.interfaces.ILoginListener;
import com.comment.tek.manage.LoginManage;
import com.comment.tek.util.Lg;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录页面
 * Created by huanghongfa on 2018/8/23.
 */

public class LoginActivity extends BaseActivity implements ILoginListener {


    private EditText mEtPwd;
    private ImageView mIvDelete, mIvClear;
    private AutoCompleteTextView mEtAccount;
    private boolean isSelect = false;
    private List<String> listAccount = new ArrayList<>();
    private LoginManage mLoginManage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initObj() {
        mLoginManage = new LoginManage();
    }

    @Override
    protected void initView() {
        mEtPwd = findView(R.id.etPwd);
        mIvDelete = findView(R.id.delete_icon);
        findView(R.id.btnLogin);
        findView(R.id.psw_control);
        findView(R.id.tvRegisterNow);
        mIvClear = findView(R.id.ivClear);
        setOnClickListener(this, R.id.tvRegisterNow, R.id.delete_icon, R.id.btnLogin, R.id.ivClear, R.id.psw_control);
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
                    mIvDelete.setVisibility(View.INVISIBLE);
                else
                    mIvDelete.setVisibility(View.VISIBLE);
            }
        });

        mEtAccount = findView(R.id.etAccount);
        mEtAccount.addTextChangedListener(new TextWatcher() {
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
        //设置AutoCompleteTextView的Adapter
        // etAccount.setAdapter(new AutoCompleteAdapter(listAccount));
        mEtAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {// 设置焦点改变事件
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                if (hasFocus && listAccount.size() > 0) {
                    view.showDropDown();// 让下拉框弹出来
                }
            }
        });

        if (listAccount.size() > 0) {
            mEtAccount.setText(listAccount.get(0));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                onLoginClick();
                break;
            case R.id.tvRegisterNow:
                startActivity(RegisterActivity.class);
                break;
            case R.id.delete_icon:
                mEtPwd.setText("");
                mIvDelete.setVisibility(View.INVISIBLE);
                break;
            case R.id.ivClear:
                mEtAccount.setText("");
                mIvClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.psw_control:
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

    private void onLoginClick() {
        String account = mEtAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            toast(getString(R.string.login_account_input_please));
            mEtAccount.requestFocus();
            return;
        }
        String pwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            toast(getString(R.string.login_pwd_hint1));
            mEtPwd.requestFocus();
            return;
        }
        mLoginManage.doLogin(mContext, account, pwd, this);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        Lg.d("登录回调" + loginBean.toString());
    }

    @Override
    public void onFailure(Throwable throwable) {
        Lg.d("登录失败");
    }
}
