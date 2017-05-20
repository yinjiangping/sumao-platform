package team.yqby.platform.common.util;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;


public class QiNiuYunUtil {

    public static final String ACCESS_KEY = "WVDvbwpHUuosKYdpqzkQUpelbMRyoi37j4I2taVq";
    public static final String SECRET_KEY = "WamqNPWQBYAyDg4cXyZ00KAm0V22hqpqQ9s0qqG3";
    public static final String BUCKET_NAME = "sumao-upload";

    public static Response upload(String localFilePath,String fileName) throws IOException{
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        UploadManager uploadManager = new UploadManager();
        Response res = null;
        try {
            res = uploadManager.put(localFilePath, fileName, auth.uploadToken(BUCKET_NAME));
        } catch (QiniuException e) {
            res = e.response;
        }
        return res;
    }
}
