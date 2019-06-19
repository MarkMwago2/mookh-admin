package me.kosgei.mookh.ui.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.kosgei.mookh.ui.Constants;
import me.kosgei.mookh.ui.model.Group;
import me.kosgei.mookh.ui.model.User;
import me.kosgei.mookh.utility.SaveSharedPreference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MookhService {



    public static void loginUser(Callback callback)
    {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

//    public ArrayList<User> processGetUserResults(Response response)
//    {
//        ArrayList<User> users = new ArrayList<>();
//        try {
//            String jsonData = response.body().string();
//            JSONArray jsonArray = new JSONArray(jsonData);
//            if (response.isSuccessful())
//            {
//                for (int i =0; i<jsonArray.length(); i++)
//                {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                    int id = jsonObject.getInt("id");
//                    String name = jsonObject.getString("name");
//                    String phone = jsonObject.getString("phone");
//                    String email = jsonObject.getString("email");
//                    String password = jsonObject.getString("password");
//
//                    User user = new User(name,phone,email,password);
//                    users.add(user);
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return users;
//
//    }

    public static void registerUser(Callback callback,String userInfo)
    {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.REGISTRATION_URL).newBuilder();
        String url = urlBuilder.build().toString();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE, userInfo);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    //get groups
    public static void getGroups(Callback callback, Context context)
    {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GET_GROUPS).newBuilder();

        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",  SaveSharedPreference.getAccessToken(context))
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public List<Group> processGroupResult(Response response)
    {
        List<Group> groups = new ArrayList<>();
        if (response.isSuccessful()) {
            try {
                String jsonData = response.body().string();
                JSONArray meals = new JSONArray(jsonData);

                for (int i = 0; i < meals.length(); i++) {
                    JSONObject jsonObject = meals.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String id = jsonObject.getString("id");

                    Group group = new Group(name, Integer.parseInt(id));
                    groups.add(group);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return groups;

    }

    //add groups
    public static void addGroup(Callback callback,String name,Context context)
    {
        Map<String,String> group = new HashMap<>();
        group.put("name",name);

        //convert object to json
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(group);

        Log.d("heyyy", json);


        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GET_GROUPS).newBuilder();
        String url = urlBuilder.build().toString();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE, json);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",  SaveSharedPreference.getAccessToken(context))
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
