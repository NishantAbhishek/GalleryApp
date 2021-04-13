package com.ntech.galleryapp.Helper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler{
    private static String TAG = HttpHandler.class.toString();

    public String makeServerCall(String reqUrl){
        String response = null;

        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            response = convertByteToString(inputStream);
        }catch (MalformedURLException e){
            e.printStackTrace();
            return "";
        }catch (ProtocolException e){
            e.printStackTrace();
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return response.substring(response.indexOf("{"),response.lastIndexOf("}")+1);

    }

    private String convertByteToString(InputStream is){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line ;

        try{
            while ((line = bufferedReader.readLine())!=null)
            {
                Log.e(TAG,"-----"+line+"-----");
                sb.append(line);
            }
            Log.e(TAG,"Loop Finished");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
