package com.base.littleapp.wrapper;

import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;

import com.base.common.util.VerifyUtil;
import com.base.littleapp.model.WxRequireLoginResultDTO;
import com.base.littleapp.common.Constant.ErrorCode;
import com.base.user.model.UserDTO;
import net.iharder.Base64;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author:小M
 * @date:2019/1/13 11:04 PM
 */
@Component
public class WxWapper {

    private final static Logger logger = LoggerFactory.getLogger(WxWapper.class);

    /**
     * 微信小程序appId
     */
    private final static String WEIXIN_APP_ID = "wx37ebc9339ff09baa";

    /**
     * 微信小程序密钥（私钥）
     */
    private final static String WEIXIN_APP_SECRET = "716d82ccbec1b3d82a74c5f94af53770";

    /**
     * 微信登录
     */
    private final static String WEXIN_AUTHORIZATION_CODE = "authorization_code";

    /**
     * 请求URL
     */
    private final static String REQUIRED_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    /**
     * 根据code，获取openId，session
     * @param code
     * @return
     * @throws Exception
     */
    public WxRequireLoginResultDTO requireWxForLogin(String code) throws Exception{
        Request request = Request
            .Post(REQUIRED_URL)
            .bodyForm(
                Form.form().add("appid", WEIXIN_APP_ID).
                    add("secret", WEIXIN_APP_SECRET).
                    add("js_code", code).
                    add("grant_type", WEXIN_AUTHORIZATION_CODE).
                    build() ,
                Charset.forName("UTF-8")
            );
        String postResult = null;
        for(int i = 0 ; i < 2 ; i++) {
            try {
                postResult = request
                    .execute()
                    .returnContent()
                    .asString();
                if(StringUtils.isEmpty(postResult)) {
                    continue;
                }
                break;
            }catch (Exception e) {
                if(i == 1) {
                    VerifyUtil.isTrue(false , ErrorCode.LOGIN_REQUIRED_LOGIN_ERROR.getCode() , code);
                }
            }
        }

        String strResult = new String(postResult);
        WxRequireLoginResultDTO wxLoginResultDTO = JSON.parseObject(strResult , WxRequireLoginResultDTO.class);
        VerifyUtil.isNotEmpty(wxLoginResultDTO.getSession_key() , ErrorCode.LOGIN_REQUIRED_LOGIN_SESSION_NULL.getCode() , code);
        return wxLoginResultDTO;
    }

    /**
     * 解密
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     * @throws Exception
     */
    public UserDTO decodeUserInfo(String encryptedData, String sessionKey, String iv) throws Exception{
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, "UTF-8");
            return JSON.parseObject(result , UserDTO.class);
        }
        return null;
    }
}
