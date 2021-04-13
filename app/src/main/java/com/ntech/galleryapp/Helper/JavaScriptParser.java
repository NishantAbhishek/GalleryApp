package com.ntech.galleryapp.Helper;

import android.util.Log;

import com.ntech.galleryapp.Model.ImageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JavaScriptParser{
    private final String TAG = JavaScriptParser.class.toString();
    public ArrayList<ImageModel> parseFavourate(String urlData) throws JSONException{
        ArrayList<ImageModel> imageModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(urlData);
        JSONArray mainArray = jsonObject.getJSONArray("results");

        for(int i = 0;i<mainArray.length();i++){
            JSONObject imageObject = mainArray.getJSONObject(i);

            ImageModel imageModel = new ImageModel();
            //Code for the First Section Of the Array
            ImageModel.ImageInformation imageInformation = imageModel.new ImageInformation();
            ImageModel.PhotoGrapherImage photoGrapherImage = imageModel.new PhotoGrapherImage();
            ImageModel.PhotoGrapherInfo photoGrapherInfo = imageModel.new PhotoGrapherInfo();

            String id = imageObject.getString("id");
            String created_at = imageObject.getString("created_at");
            String updated_at = imageObject.getString("updated_at");
            String description = imageObject.getString("description");
            String blur_hash = imageObject.getString("blur_hash");
            Integer likes = imageObject.getInt("likes");
            Log.e(TAG,id);
            imageInformation.setId(id);
            imageInformation.setCreatedAt(created_at);
            imageInformation.setUpdatedAt(updated_at);
            imageInformation.setDescription(description);
            imageInformation.setBlurHash(blur_hash);
            imageInformation.setLikes(likes);
            imageModel.setImageInformation(imageInformation);

            //Code for getting the Urls Of the image
            JSONObject urlsObject = imageObject.getJSONObject("urls");
            String raw = urlsObject.getString("raw");
            String full = urlsObject.getString("full");
            String regular = urlsObject.getString("regular");
            String small = urlsObject.getString("small");
            String thumb = urlsObject.getString("thumb");
            Log.e(TAG,thumb);
            imageModel.setRaw(raw);
            imageModel.setFull(full);
            imageModel.setRegular(regular);
            imageModel.setSmall(small);
            imageModel.setThumb(thumb);

            //Code for getting the Links
            JSONObject links = imageObject.getJSONObject("links");
            String self = links.getString("self");
            String html = links.getString("html");
            String download = links.getString("download");
            String download_location = links.getString("download_location");

            //JSON object User section
            JSONObject user = imageObject.getJSONObject("user");
            String userId = user.getString("id");
            String userName = user.getString("username");
            String name = user.getString("name");
            String first_name = user.getString("first_name");
            String last_name = user.getString("last_name");
            String bio = user.getString("bio");
            Log.e(TAG,userName);
            photoGrapherInfo.setUserId(userId);
            photoGrapherInfo.setUserName(userName);
            photoGrapherInfo.setName(name);
            photoGrapherInfo.setFirstName(first_name);
            photoGrapherInfo.setLastName(last_name);
            photoGrapherInfo.setBio(bio);
            imageModel.setPhotoGrapherInfo(photoGrapherInfo);

            //Json of profile Image
            JSONObject profile_image = user.getJSONObject("profile_image");
            String prfSmall = profile_image.getString("small");
            String prfMedium = profile_image.getString("medium");
            String prfLarge = profile_image.getString("large");
            photoGrapherImage.setSmallImage(prfSmall);
            photoGrapherImage.setMediumImage(prfMedium);
            photoGrapherImage.setLargeImage(prfLarge);
            imageModel.setPhotoGrapherImage(photoGrapherImage);
            Log.e(TAG,prfMedium);

            JSONArray tags = imageObject.getJSONArray("tags");
            for (int j = 0; j < tags.length(); j++) {
//                JSONObject source = tags.getJSONObject(i);
//                String tagTitle = source.getString("title");
//                String tagSubtitle = source.getString("subtitle");
//                String tagDescription = source.getString("description");
//
//                JSONObject cover_photo = source.getJSONObject("cover_photo");
            }
            Log.e(TAG,"-----------");
            imageModels.add(imageModel);
        }
        Log.e(TAG,Integer.toString(imageModels.size()));
        return imageModels;
    }

    public ImageModel parseSingleFavourate(String url) throws JSONException{
        JSONObject imageObject = new JSONObject(url);

        ImageModel imageModel = new ImageModel();
        //Code for the First Section Of the Array
        ImageModel.ImageInformation imageInformation = imageModel.new ImageInformation();
        ImageModel.PhotoGrapherImage photoGrapherImage = imageModel.new PhotoGrapherImage();
        ImageModel.PhotoGrapherInfo photoGrapherInfo = imageModel.new PhotoGrapherInfo();

        String id = imageObject.getString("id");
        String created_at = imageObject.getString("created_at");
        String updated_at = imageObject.getString("updated_at");
        String description = imageObject.getString("description");
        String blur_hash = imageObject.getString("blur_hash");
        Integer likes = imageObject.getInt("likes");
        Log.e(TAG,id);
        imageInformation.setId(id);
        imageInformation.setCreatedAt(created_at);
        imageInformation.setUpdatedAt(updated_at);
        imageInformation.setDescription(description);
        imageInformation.setBlurHash(blur_hash);
        imageInformation.setLikes(likes);
        imageModel.setImageInformation(imageInformation);

        //Code for getting the Urls Of the image
        JSONObject urlsObject = imageObject.getJSONObject("urls");
        String raw = urlsObject.getString("raw");
        String full = urlsObject.getString("full");
        String regular = urlsObject.getString("regular");
        String small = urlsObject.getString("small");
        String thumb = urlsObject.getString("thumb");
        Log.e(TAG,thumb);
        imageModel.setRaw(raw);
        imageModel.setFull(full);
        imageModel.setRegular(regular);
        imageModel.setSmall(small);
        imageModel.setThumb(thumb);

        //Code for getting the Links
        JSONObject links = imageObject.getJSONObject("links");
        String self = links.getString("self");
        String html = links.getString("html");
        String download = links.getString("download");
        String download_location = links.getString("download_location");

        //JSON object User section
        JSONObject user = imageObject.getJSONObject("user");
        String userId = user.getString("id");
        String userName = user.getString("username");
        String name = user.getString("name");
        String first_name = user.getString("first_name");
        String last_name = user.getString("last_name");
        String bio = user.getString("bio");
        Log.e(TAG,userName);
        photoGrapherInfo.setUserId(userId);
        photoGrapherInfo.setUserName(userName);
        photoGrapherInfo.setName(name);
        photoGrapherInfo.setFirstName(first_name);
        photoGrapherInfo.setLastName(last_name);
        photoGrapherInfo.setBio(bio);
        imageModel.setPhotoGrapherInfo(photoGrapherInfo);

        //Json of profile Image
        JSONObject profile_image = user.getJSONObject("profile_image");
        String prfSmall = profile_image.getString("small");
        String prfMedium = profile_image.getString("medium");
        String prfLarge = profile_image.getString("large");
        photoGrapherImage.setSmallImage(prfSmall);
        photoGrapherImage.setMediumImage(prfMedium);
        photoGrapherImage.setLargeImage(prfLarge);
        imageModel.setPhotoGrapherImage(photoGrapherImage);
        Log.e(TAG,prfMedium);
        return imageModel;
    }


}
