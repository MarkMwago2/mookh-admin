package me.kosgei.mookh.ui.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.kosgei.mookh.ui.Constants;
import me.kosgei.mookh.ui.model.User;
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
}
