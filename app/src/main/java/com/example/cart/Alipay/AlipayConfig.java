package com.example.cart.Alipay;

public class AlipayConfig {
        public String getAppId() {
                return AppId;
        }

        public void setAppId(String appId) {
                AppId = appId;
        }

        public String getPID() {
                return PID;
        }

        public void setPID(String PID) {
                this.PID = PID;
        }

        public String getPrivateKey() {
                return PrivateKey;
        }

        public void setPrivateKey(String privateKey) {
                PrivateKey = privateKey;
        }

        public String getServerUrl() {
                return ServerUrl;
        }

        public void setServerUrl(String serverUrl) {
                ServerUrl = serverUrl;
        }

        public String getFormat() {
                return Format;
        }

        public void setFormat(String format) {
                Format = format;
        }

        public String getCharset() {
                return Charset;
        }

        public void setCharset(String charset) {
                Charset = charset;
        }

        public String getAlipayPublicKey() {
                return AlipayPublicKey;
        }

        public void setAlipayPublicKey(String alipayPublicKey) {
                AlipayPublicKey = alipayPublicKey;
        }

        public String getSignType() {
                return SignType;
        }

        public void setSignType(String setSignType) {
                this.SignType = setSignType;
        }

        /**
         * 用于支付宝支付业务的入参 app_id。
         */
        public  String AppId;
        /**
         * 用于支付宝账户登录授权业务的入参 pid。
         */
        public String PID;
        /**
         * 用于支付宝账户登录授权业务的入参 target_id。
         */
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
        public String PrivateKey;
        public String ServerUrl;
        public String Format;
        public String Charset;
        public String AlipayPublicKey;
        public String SignType;
}
