package team.yqby.platform.common.util;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import team.yqby.platform.config.PublicConfig;


public class QiNiuYunUtil {

    public static Response upload(String localFilePath,String fileName) throws IOException{
        Auth auth = Auth.create(PublicConfig.ACCESS_KEY, PublicConfig.SECRET_KEY);
        UploadManager uploadManager = new UploadManager();
        Response res = null;
        try {
            res = uploadManager.put(localFilePath, fileName, auth.uploadToken(PublicConfig.BUCKET_NAME));
        } catch (QiniuException e) {
            res = e.response;
        }
        return res;
    }
}
