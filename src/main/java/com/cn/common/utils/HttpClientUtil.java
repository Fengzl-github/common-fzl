package com.cn.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @Author fengzhilong
 * @Date 2020/10/27 10:20
 * @Desc //TODO 跨域请求
 **/
public class HttpClientUtil {

    public HttpClientUtil() {
    }

    /*----------------------------------------------------------------- get ------------------------------------------------------*/
    public static String doGet(String url, Map<String, Object> param, String charset){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null){
                Iterator iterator = param.keySet().iterator();
                while (iterator.hasNext()){
                    String key = (String) iterator.next();
                    builder.addParameter(key, (String) param.get(key));
                }
            }

            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                resultString = EntityUtils.toString(response.getEntity(), charset);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                if (response != null){
                    response.close();
                }
                httpClient.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String, Object> param){
        return doGet(url, param, "utf-8");
    }

    public static String doGet(String url){
        return doGet(url, "utf-8");
    }

    public static String doGet(String url, String charset){
        return doGet(url, null, charset);
    }




    /*----------------------------------------------------------------- post ------------------------------------------------------*/
    public static String doPost(String url, Map<String, Object> param){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        try {

            HttpPost httpPost = new HttpPost(url);
            if (param != null){
                List<NameValuePair> paramList = new ArrayList<>();
                Iterator<String> iterator = param.keySet().iterator();

                while (iterator.hasNext()){
                    String key = iterator.next();
                    paramList.add(new BasicNameValuePair(key, (String) param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                response.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }


    public static String doPostJson(String url, String json){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                response.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPostXML(String url, String xml){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(xml, ContentType.APPLICATION_XML);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                response.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultString;
    }


    /*----------------------------------------------------------------- url ------------------------------------------------------*/
    public static String urlEncodeUTF8(String source){
        String result = source;

        try {
            result = URLEncoder.encode(source, "utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return result;
    }


    public static String urlDecodeUTF8(String source){
        String result = source;

        try {
            result = URLDecoder.decode(source, "utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return result;
    }
}
