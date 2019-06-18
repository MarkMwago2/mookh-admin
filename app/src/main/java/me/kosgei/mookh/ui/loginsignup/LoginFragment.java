package me.kosgei.mookh.ui.loginsignup;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.Constants;
import me.kosgei.mookh.ui.CustomProgress;
import me.kosgei.mookh.ui.HomeActivity;
import me.kosgei.mookh.ui.model.User;
import me.kosgei.mookh.ui.services.MookhService;
import me.kosgei.mookh.utility.SaveSharedPreference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;

    ArrayList<User> users = new ArrayList<>();

    private static final String TAG = "LoginFragment";

    CustomProgress customProgress;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        btnLogin.setOnClickListener(this);

        customProgress = CustomProgress.getInstance();

        return view;
    }

    private boolean isValid() {
        boolean valid = false;


        if (password.getText().toString().trim().isEmpty()) {
            password.setError("Please a password");
        } else if (username.getText().toString().trim().isEmpty()) {
            username.setError("Please enter your username");
        } else {
            valid = true;
        }


        return valid;
    }


    @Override
    public void onClick(View v) {
        if (v == btnLogin)
        {
            if (isValid())
            {
                loginUser();
            }

        }

    }

    private void loginUser() {

        customProgress.showProgress(getActivity(), "Logging you in", false);

        //convert object to json
        Gson gson = new GsonBuilder().create();

        Map<String,String> user = new HashMap<>();
        user.put("username",username.getText().toString().trim());
        user.put("password",password.getText().toString().trim());

//        User user = new User(username.getText().toString().trim(), password.getText().toString().trim());


        String json = gson.toJson(user);

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();

        String url = urlBuilder.build().toString();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE, json);


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,  IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        customProgress.hideProgress();

                    }
                });
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                customProgress.hideProgress();
                if (response.isSuccessful())
                {
                    if (response.code() == 200)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String json = response.body().string();
                                    JSONObject jsonObject = new JSONObject(json);
                                    Constants.Token = jsonObject.getString("access");


                                    Intent intent = new Intent(getActivity() , HomeActivity.class);
                                    SaveSharedPreference.setLoggedIn(getActivity(), true);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                else
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String json = response.body().string();
                                JSONObject jsonObject = new JSONObject(json);

                                Toast.makeText(getActivity(), jsonObject.getString("detail"), Toast.LENGTH_SHORT).show();


                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });




    }
}
