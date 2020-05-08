package com.zplay.zplayantiaddiction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zplay.android.addiction.prevention.ZplayAddictionSDK;
import com.zplay.android.addiction.prevention.callback.ZplayCheckCallback;
import com.zplay.android.addiction.prevention.callback.ZplayLoginCallback;
import com.zplay.android.addiction.prevention.callback.ZplayWarningCallback;
import com.zplay.android.addiction.prevention.callback.ZplayLogoutCallback;
import com.zplay.android.addiction.prevention.callback.ZplayPrivacyPolicyCallback;
import com.zplay.android.addiction.prevention.callback.ZplayUserAuthVcCallback;
import com.zplay.android.addiction.prevention.utils.LogUtils;
import com.zplay.android.addiction.prevention.utils.enumbean.UserVerifiedType;


public class MainActivity extends Activity {

    protected static final String TAG = "MainActivity";
    private EditText getZplayId, getToken, getUid, getPlatformName, getUserName, getPassword, getMoney, getPaySuccessMoney;


    private ZplayLoginCallback loginCallback;
    private ZplayUserAuthVcCallback userAuthVcCallback;
    private ZplayWarningCallback warningCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getZplayId = findViewById(R.id.getZplayId);
        getToken = findViewById(R.id.getToken);
        getUid = findViewById(R.id.getUid);
        getPlatformName = findViewById(R.id.getPlatformName);
        getUserName = findViewById(R.id.getUserName);
        getPassword = findViewById(R.id.getPassword);
        getMoney = findViewById(R.id.getMoney);
        getPaySuccessMoney = findViewById(R.id.getPaySuccessMoney);


        loginCallback = new ZplayLoginCallback() {
            @Override
            public void loginSuccess(String uid, String token, String userName, String loginType) {

                Toast.makeText(MainActivity.this, "uid:" + uid + "token:" + token + ",userName:" + userName + ",loginType:" + loginType, Toast.LENGTH_SHORT)
                        .show();
                LogUtils.i(TAG, "uid:" + uid);
                LogUtils.i(TAG, "token:" + token);

            }

            @Override
            public void loginHasBeenShown() {
                Toast.makeText(MainActivity.this,
                        "登录界面展示", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void loginCancel() {
                // TODO Auto-generated method stub
                Log.d(TAG, "loginCancel: ");
                Toast.makeText(MainActivity.this,
                        "取消登录", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void loginFail() {
                Toast.makeText(MainActivity.this,
                        "登录失败", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        userAuthVcCallback = new ZplayUserAuthVcCallback() {
            @Override
            public void userAuthVcHasBeenShown() {
                Toast.makeText(MainActivity.this,
                        "实名认证窗口显示", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userAuthSuccess() {
                Toast.makeText(MainActivity.this,
                        "实名认证通过", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        warningCallback = new ZplayWarningCallback() {

            @Override
            public void warningHasBeenShown() {
                Toast.makeText(MainActivity.this,
                        "展示", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userClickLoginButton() {
                //游客 状态用户游戏时间结束，用户点击了登录按钮
                Toast.makeText(MainActivity.this,
                        "登录", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userClickQuitButton() {
                //游客 状态用户游戏时间结束，用户点击了退出游戏按钮
                Toast.makeText(MainActivity.this,
                        "退出游戏", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userClickConfirmButton() {
                //用户点击了计费提示弹窗上面的确定接口
                Toast.makeText(MainActivity.this,
                        "确定", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        ZplayAddictionSDK.init(this, loginCallback, userAuthVcCallback, warningCallback);

        ZplayAddictionSDK.showPrivacyPolicy(MainActivity.this, new ZplayPrivacyPolicyCallback() {
            @Override
            public void privacyPolicyShown() {
                Toast.makeText(MainActivity.this,
                        "用户隐私协议页面展示", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userAgreesToPrivacyPolicy() {
                Toast.makeText(MainActivity.this,
                        "用户同意隐私协议", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void userDisagreesWithPrivacyPolicy() {
                Toast.makeText(MainActivity.this,
                        "用户拒绝隐私协议", Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }


    public void doLogin(View view) {
        ZplayAddictionSDK.login(MainActivity.this);
    }

    //展示对zplayId进行的实名认证界面
    public void showLoginWithZplayId(View view) {
        String zplayId = getZplayId.getText().toString();
        if (zplayId.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入ZplayId", Toast.LENGTH_SHORT)
                    .show();
        }

        ZplayAddictionSDK.loginWithZplayID(MainActivity.this, zplayId);
    }

    //展示游客账号的实名认证界面
    public void showAuthentication(View view) {
        ZplayAddictionSDK.showVisitorUserAuthentication(MainActivity.this);
    }



    //获取当前账号的实名认证状态
    public void getIdCardType(View view) {
        UserVerifiedType userAuthenticationIdentity =  ZplayAddictionSDK.getUserVerified(MainActivity.this);
        if(userAuthenticationIdentity == UserVerifiedType.UserAgeUnknow){
            Toast.makeText(MainActivity.this,
                    "当前账号未实名认证", Toast.LENGTH_SHORT)
                    .show();
        }else if(userAuthenticationIdentity == UserVerifiedType.UserAdult){
            Toast.makeText(MainActivity.this,
                    "当前账号实名认证为成年人", Toast.LENGTH_SHORT)
                    .show();
        }else{
            Toast.makeText(MainActivity.this,
                    "当前账号实名认证为未成年人", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //三方平台登录实名认证接口
    public void loginWithPlatform(View view) {
        String token = getToken.getText().toString();
        if (token.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入token", Toast.LENGTH_SHORT)
                    .show();
        }

        String uid = getUid.getText().toString();
        if (token.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入uid", Toast.LENGTH_SHORT)
                    .show();
        }

        String platformName = getPlatformName.getText().toString();
        if (token.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入三方平台名称", Toast.LENGTH_SHORT)
                    .show();
        }


        ZplayAddictionSDK.loginWithPlatformToken(this, token, uid, platformName);
    }

    /**
     * 通过用户名和密码进行登录
     */
    public void loginWithUserName(View view) {
        String userName = getUserName.getText().toString();
        if (userName.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入账号", Toast.LENGTH_SHORT)
                    .show();
        }

        String password = getPassword.getText().toString();
        if (password.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入密码", Toast.LENGTH_SHORT)
                    .show();
        }


        ZplayAddictionSDK.loginWithUserName(this, userName, password);
    }


    /**
     * 注销登录接口
     */
    public void logOut(View view) {
        ZplayAddictionSDK.logout(this, new ZplayLogoutCallback() {
            @Override
            public void logout(Activity activity) {
                Toast.makeText(MainActivity.this,
                        "注销账号成功", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    /**
     * 检测本次支付是否超过限额
     */
    public void checkPay(View view) {
        String money = getMoney.getText().toString();
        if (money.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入本次支付的金额", Toast.LENGTH_SHORT)
                    .show();
        }

        ZplayAddictionSDK.checkNumberLimitBeforePayment(this, money, new ZplayCheckCallback() {
            @Override
            public void onCanPay() {
                Toast.makeText(MainActivity.this,
                        "本次支付可以进行", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onProhibitPay(String errorMsg) {
                Toast.makeText(MainActivity.this,
                        "本次支付不允许支付: " + errorMsg, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * 支付成功后进行支付成功上报
     */
    public void reportMoney(View view) {
        String money = getPaySuccessMoney.getText().toString();
        if (money.equals("")) {
            Toast.makeText(MainActivity.this,
                    "请先输入本次上报的支付金额", Toast.LENGTH_SHORT)
                    .show();
        }

        ZplayAddictionSDK.reportNumberAfterPayment(MainActivity.this, money);
    }

    /**
     * 获取登录类型
     *
     * @param view
     */
    public void getLoginType(View view) {
        Toast.makeText(getApplicationContext(),
                ZplayAddictionSDK.getLoginType(MainActivity.this), Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        ZplayAddictionSDK.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZplayAddictionSDK.onResume();
    }


}
