# 1. 概述

## 1.1 面向人群

当前文档面向需要在 Android 产品中接入防沉迷 SDK 的开发人员。
   
## 1.2 开发环境

OS: Windows, Mac, Linux <br/>
Android SDK: > 4.4(API level 19)<br/>
IDE: Eclipse with ADT (ADT version 23.0.4) OR Android-Studio<br/>
Java: > JDK 7
  

# 2. 开发环境配置

## 2.1 Android-studio 接入

在 app 下的 build.gradle 中添加依赖相关依赖

```groovy
dependencies {
    // AntiAddictionSDK
    implementation "com.zplay.sdk:antiaddiction:1.0.0"
｝
```

## 2.2 Eclipse 接入

**下载并添加防沉迷SDK：**

>[SDK 下载](https://github.com/yumimobi/AntiAddictionSystemDemo-Android/blob/master/eclipseSDK/AntiAddictionSDK.zip)

 
# 3. 配置防沉迷SDK需要的参数

## 3.1 下载ZplayConfig.xml文件,并添加到工程的assets目录下

<img src="resources\antiAddiction-ZplayConfig.png" alt="antiAddiction-ZplayConfig">

>[ZplayConfig.xml 下载](https://github.com/yumimobi/AntiAddictionSystemDemo-Android/blob/master/app/src/main/assets/ZplayConfig.xml)

## 3.2 修改ZplayConfig.xml文件中的配置
<img src="resources\antiAddiction-ZplayConfig1.png" alt="antiAddiction-ZplayConfig1">

<div style="background-color:rgb(228,244,253);padding:10px;">
<span style="color:rgb(62,113,167);">ZplayConfig.xml文件中的GameID, ChannelID, Zpay_SDK_KEY参数，请联系掌游产品获取</span></div>
<br/>


# 4.快速接入 
## 4.1初始化

```java
loginCallback = new ZplayLoginCallback() {
    @Override
    public void loginSuccess(String uid, String token, String userName, String loginType) {
        //用户登录成功回调
    }

    @Override
    public void loginHasBeenShown() {
        //登录界面展示
    }

    @Override
    public void loginCancel() {
        // TODO Auto-generated method stub
        //取消登录
    }

    @Override
    public void loginFail() {
        //登录失败
    }
};

userAuthVcCallback = new ZplayUserAuthVcCallback() {
    @Override
    public void userAuthVcHasBeenShown() {
         //实名认证窗口显示
    }

    @Override
    public void userAuthSuccess() {
         //实名认证通过
    }
};

warningCallback = new ZplayWarningCallback() {

    @Override
    public void warningHasBeenShown() {
        //展示提示用户弹窗
    }

    @Override
    public void userClickLoginButtonInPayment() {
        //当用户在游客模式点击支付，防沉迷SDK判断当前为游客模式时会弹出提示让用户选择是否登录，当用户点击登陆按钮后会触发此回调，游戏收到回调后需引导用户进行登录，用户不登录也可以继续游戏
    }

    @Override
    public void userClickLoginButtonInNoTimeLeft() {
        //当用户以游客身份在游戏中时长已到即接收到此回调，防沉迷SDK会弹出弹窗让用户登录后继续游戏或退出游戏，游戏需要引导用户进行登录，并且处理用户不登录则不可继续游戏的逻辑。
    }


    @Override
    public void userClickQuitButton() {
        //游客 状态用户游戏时间结束，用户点击了退出游戏按钮
    }

    @Override
    public void userClickConfirmButton() {
        //用户点击了计费提示弹窗上面的确定接口
    }
};

ZplayAddictionSDK.init(Activity, loginCallback, userAuthVcCallback, warningCallback);
```  

## 4.2 展示隐私政策接口

```java
ZplayAddictionSDK.showPrivacyPolicy(Activity, new ZplayPrivacyPolicyCallback() {
    @Override
    public void privacyPolicyShown() {
        //用户隐私协议页面展示
    }

    @Override
    public void userAgreesToPrivacyPolicy() {
        //用户同意隐私协议
    }

    @Override
    public void userDisagreesWithPrivacyPolicy() {
        //用户拒绝隐私协议
    }
});
```  

## 4.3 登录相关接口
<span style="color:rgb(150,0,0);">
<b>重要提示：</b> 应用必须选择下面的一种登录方式进行登录，登录成功之后，防沉迷SDK会自动弹出用户实名认证界面
</span>

### 4.3.1 防沉迷SDK登录接口
`注：若您的APP无登录功能、无登录界面，使用防沉迷SDK的登录功能可调用此接口。`
```java
ZplayAddictionSDK.login(Activity);
```

### 4.3.2 账号密码登录接口
`注：若您的APP有登陆页面，只需使用我方登录接口，可调用此接口`
游戏如果使用游戏自己设计的登录界面，可以将登录界面中用户输入的账号&密码传给防沉迷SDK，使用下面的接口进行登录
```java
//userName：账号
//password：密码
ZplayAddictionSDK.loginWithUserName(Activity, userName, password);
```

### 4.3.3 三方登录接口
`注：若您的APP已经接入微信登录、QQ登录等非ZPLAY登录，需调用此接口`
游戏如果接入了三方(微信，QQ等)平台的登录，可以将三方平台返回的用户唯一标识通过以下的接口进行登录
```c#
//token：三方平台返回的唯一标识
//uid：三方平台返回的用户Id，没有的话传token
//platformName：平台名称，请联系掌游产品获取
ZplayAddictionSDK.loginWithPlatformToken(this, token, uid, platformName);
```

### 4.3.4 Zplay封装的三方登录SDK接口
若您使用Zplay封装的三方登录SDK，在获取到了登录成功之后的zplayId，请使用下面的接口进行登录
```java
//zplayID：Zplay封装的登录SDK返回的zplayID
ZplayAddictionSDK.loginWithZplayID(Activity, zplayId);
```

## 4.4 游客实名认证接口
用户进入游戏且未登录时会默认进入游客模式，若用户在游客模式中，您需要用户进行实名认证时请调用以下接口
```java
ZplayAddictionSDK.showVisitorUserAuthentication(Activity);
```

## 4.5 注销登录接口
若您的APP在用户登录后提供退出登录的功能，在用户退出登录时需调用此接口
```java
ZplayAddictionSDK.logout(this, new ZplayLogoutCallback() {
    @Override
    public void logout(Activity activity) {
        //注销账号成功
    }
});
```


## 4.6 支付相关接口
 根据国家规定，对未成年人在游戏中的单笔付费金额和每月累计付费金额都有限制，因此在用户发起支付时需要调用`检测本次用户购买是否可以支付`接口，用户购买成功后需要调用`用户支付成功上报`接口。

### 4.6.1 检测本次用户购买是否可以支付
用户购买之前，调用此接口检测，返回可支付后才可发起支付。
```java
//money：用户本次购买的金额，单位分
ZplayAddictionSDK.checkNumberLimitBeforePayment(Activity, money, new ZplayCheckCallback() {
    @Override
    public void onCanPay() {
        //本次支付可以进行
    }

    @Override
    public void onProhibitPay(String errorMsg) {
        //不允许支付
    }
});
```

### 4.6.2 用户支付成功上报
用户支付成功后，请将用户的支付金额上报给防沉迷系统。
```java  
//money：用户本次购买成功的金额，单位分
ZplayAddictionSDK.reportNumberAfterPayment(Activity, money);
```

## 4.7 其他接口
### 4.7.1 获取当前登录状态接口
请使用下面的接口获取当前用户的登录状态
```java
//获取当前用户登录状态
//notlogin: 未登录
//visitor: 游客
//user: 正式用户
String loginStatus = ZplayAddictionSDK.getLoginType(Activity);
```

### 4.7.2 获取用户的认证身份
获取当前用户的实名认证身份
```java
UserVerifiedType userAuthenticationIdentity =  ZplayAddictionSDK.getUserVerified(MainActivity.this);

if(userAuthenticationIdentity == UserVerifiedType.UserAgeUnknow){
    //当前账号实名认证状态未知
}else if(userAuthenticationIdentity == UserVerifiedType.UserAdult){
    //当前账号实名认证为成年人
}else{
    //当前账号实名认证为未成年人
}
```

### 4.7.3 游戏退到后台接口(必选)
当用户按home键，将游戏退出到后台时，请调用下面的接口

<span style="color:rgb(255,0,0);">
<b>重要提示：</b> 游戏退到后台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
</span>

```java
ZplayAddictionSDK.onPause();
```

### 4.7.4 游戏恢复前台接口(必选)
当用户将游戏恢复到前台时，请调用下面的接口

<span style="color:rgb(255,0,0);">
<b>重要提示：</b> 游戏恢复前台接口必须调用，不调用会导致防沉迷SDK计算游戏时长错误
</span>

```java
ZplayAddictionSDK.onResume();
```

