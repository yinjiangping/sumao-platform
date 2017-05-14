package team.yqby.platform.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;

import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.util.List;

/**
 * web请求调用方法
 *
 * @author jumping
 * @version 1.0.0
 * @time 2015/6/29
 */
@Slf4j
public class WebCall {

    public static String xmlSyncSend(String wsAddress, String xml) throws AutoPlatformException {
        String resXml = "";
        try {
            log.info("webService Address requestUrl:{}", wsAddress);
            log.info("webService Address requestXml:{}", xml);
            //服务的地址
            URL wsUrl = new URL(wsAddress);
            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(xml.getBytes());
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                String ss = new String(b, 0, len, "UTF-8");
                resXml += ss;
            }
            is.close();
            os.close();
            conn.disconnect();
            log.info("webService Address responseXml:{}", resXml);
        } catch (Exception e) {
            log.error("send XML request  Exception:{}", e);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_F99999.getResCode(), e.getMessage());
        }
        return resXml;
    }

    /**
     * HTTP同步POST请求
     *
     * @param url        请求地址
     * @param formParams 请求参数
     * @return
     */
    public static String closeableHttpClientPost(String url, List<org.apache.http.NameValuePair> formParams) {
        String resStr = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(3000).build();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        try {
            log.info("closeableHttpClientPost requestUrl:{},requestParam:{}", url, formParams);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, PublicConfig.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                resStr = EntityUtils.toString(httpEntity, "UTF-8");
            }
            closeableHttpClient.close();
        } catch (Exception e) {
            log.error("closeableHttpClient requestUrl:{},exception,{}", url, e.getMessage());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_F777777.getResCode(), ServiceErrorCode.ERROR_CODE_F777777.getResDesc());
        }
        log.info("closeableHttpClientPost responseContent:{}", resStr);
        return resStr;
    }


    /**
     * HTTP同步POST请求
     *
     * @param url        请求地址
     * @return
     */
    public static String closeableHttpClientGet(String url) {
        String resStr = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(3000).build();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        try {
            log.info("closeableHttpClientPost requestUrl:{}", url);
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                resStr = EntityUtils.toString(httpEntity, "UTF-8");
            }
            closeableHttpClient.close();
        } catch (Exception e) {
            log.error("closeableHttpClient requestUrl:{},exception,{}", url, e.getMessage());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_F777777.getResCode(), ServiceErrorCode.ERROR_CODE_F777777.getResDesc());
        }
        log.info("closeableHttpClientPost responseContent:{}", resStr);
        return resStr;
    }
}
