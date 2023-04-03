package com.example.cart.Alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.cart.Bean.CartBean;
import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.fragment.ShopCartFragment;

import java.util.List;
import java.util.Map;

public class PayEasy{
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000122622212";
    private Context ctx;
    private Activity act;
    public PayEasy(Context context, Activity activity) {
        this.ctx=context;
        this.act=activity;
    }
    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088621995328001";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCEewgKKO4KQi4USzDed0vRIDQmlPCfm8YAko7rDrNvHjfCJdJver3VGUmhaTPf9ZnVm7UFgkpLgpm7GUUViKAMWWYbNc0BSleas9DkVCxa7CEhtqOpgNlIGelUqS3ml9WiJMxMi5rRgCVFGxQHBA/kXCyQDk8KNAM/SvyaFYcme/Qlrz+4wmZAxUieAr0l3KAvGMb999kSg2m2jzTJwJEy8czkXzHzhLqZ3OJAF6Ggo4OGc9AfeBXoIgoItzqA1q2eYldgGixOZtq1EMqt8mldsqpJUa1OK9KvNqfQQDkkQYWI91kvzTAMs5cieXuFBBfMgpyWa7KqkqNRkoxrg7B5AgMBAAECggEAA4iUilDVKIjjWb83qndcGlPVkEHguSEaguFl1ncp6gjQS+leV4LalVh7UkbQD2UpmTzx8xeqi6EMIKVn5k8ilbG8u3UP5rzo4UNyiuSD2lj5+UA51UeOhBHM22k3xDLAi/aXTGNc2cGlPNagDh/ZnKmotp7rFMaWX5XMbv+r/ya9P8YdJY4PTakzzyUpoe9DTGbndJXkoLOZ5+29NC3b6nbKnM+5qzWAC7ID53yrBod/dGXDJPy4gKCv3xyOFmFyTzCXFGyeUc+1ghiEg38HfU5J7/XvJpekaswDAklNWuq/dsY0yTabD9JgU+DPhw8f1McuwxcWjvqYNyGvufLTQQKBgQDa4ojsFrfB/eiLWjcwm1ehCITSqJ5+tTC4L2+qzKS4GwqQa3XdebUWCsDz0LIGWEv+owbMBdN4vxzpjQaGfYsN1w0ECnG4pu2xoOF0wzrq3kM/eVt+YuSh8PXB6Iipduhyrgg8cNDgSEAw/FdHbn+HeHEstb1FBexBii4zq1lMZQKBgQCa8dCpy8AeJqtQ1rES0wdzxsSZBXsnBNTynzGIiUFTexhJaxhq5Iz4J7RNHeKaOn+mS4Wv7SQaU5wxgFB0ULDeXgxaNz1unKGuYTcnorqSXciSUXHreTiwrcm2tB63evP3GV8RHWP1yFdCFZBsWFOEoF4+7CBrlT/7mD+bljMAhQKBgGNzhETIuatRw2P1lJfoLPOE3FyesozdpIo7O2B+ZOUlnDIsMTx0/RtLvYnKK+kIG1dSGVd/2irFosxXAD0BM7oRGN/aIvuEFQGdGHMl9VIbtLBVYoXVRw5xXihBkD53QXxBqIpVtAIDse7P6JQTF6ykw79Nv/Zpj4Pj1cQyOXulAoGAM/pE1oEMXND6DW9IDZmPWW5nizHEg21ihlWTB0uMB5Y2qoJruYgcJi8jk+8fkj9njQCuwzqZLt+3eP+jfbGH88C3Ahif2hhfQKFKIFi9n9YoL7V0stqCgvp5vXmlCEYNy2nOc25Zlxzy8K9NOJtUwRwPIaqSoaR6x0rmo6n6SAUCgYB8oskuedVeAgpEowSpZhFH/F4w/3UyhrpMYnwgjT0tSwqX0iBE9zLhyQlTlXbZ4Jzj35wJ0A90hKsoTkoyRzsL38K3eYch2Sj/g5H2MQczmY8VIh2ccNz6WadaBNo6zcqlX/jSbLUu2/zprEDiVcO6xL1uwdtC0j92Ya7f9pAIsw==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public Thread payThread;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(ctx, String.valueOf(R.string.pay_success) + payResult);
                        ShopCartFragment.Refresh(true);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ShopCartFragment.Refresh(false);
                        showAlert(ctx, String.valueOf(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(ctx, String.valueOf(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(ctx, String.valueOf(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    public void setList(List<CartBean> cartlist){


    }

    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(ctx, String.valueOf(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(act);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务示例
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(ctx, String.valueOf(R.string.error_auth_missing_partner_appid_rsa_private_target_id));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(act);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 获取支付宝 SDK 版本号。
     */
    public void showSdkVersion(View v) {
        PayTask payTask = new PayTask(act);
        String version = payTask.getVersion();
        showAlert(ctx, String.valueOf(R.string.alipay_sdk_version_is) + version);
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key: bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
        }
        return sb.toString();
    }
}
