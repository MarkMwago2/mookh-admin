package me.kosgei.mookh.ui.loginsignup;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.CustomProgress;
import me.kosgei.mookh.ui.model.User;
import me.kosgei.mookh.ui.services.MookhService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.names) EditText names;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirm_password) EditText confirm_password;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.sign_up)
    Button signUp;

    CustomProgress customProgress;



    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        customProgress = CustomProgress.getInstance();


        ButterKnife.bind(this,view);
        signUp.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    public boolean isValid()
    {
        boolean valid = false;

        if (names.getText().toString().trim().isEmpty())
        {
            names.setError("Please enter your name");
        }
        else if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Please enter your email");
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()))
        {
            email.setError("Please enter a valid email");
        }
        else if(password.getText().toString().trim().isEmpty())
        {
            password.setError("Please enter a password");
        }
        else if(username.getText().toString().trim().isEmpty())
        {
            username.setError("Please enter a username");
        }
        else if(confirm_password.getText().toString().trim().isEmpty())
        {
            confirm_password.setError("Please enter a password");
        }
        else if(!(confirm_password.getText().toString().trim().equals(password.getText().toString().trim())))
        {
            confirm_password.setError("Password doesn't match");
        }

        else {
            valid = true;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        if (v == signUp)
        {
            if (isValid())
            {
                Map<String,String> user = new HashMap<>();
                user.put("username",username.getText().toString().trim());
                user.put("password",password.getText().toString().trim());
                user.put("email",email.getText().toString().trim());
                user.put("first_name",names.getText().toString().split(" ")[0]);
                user.put("last_name",names.getText().toString().split(" ")[1]);


                //convert object to json
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(user);

                userRegistration(json);
            }
        }

    }

    private void userRegistration(String json) {
        customProgress.showProgress(getActivity(), "Registering you ..", false);
        MookhService mookhService = new MookhService();
        mookhService.registerUser(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                customProgress.hideProgress();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if (response.isSuccessful())
                {
                    if (response.code() == 201)
                    {
                        customProgress.hideProgress();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getActivity(), " Welcome "+ names.getText().toString() + ", you can now log in.", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                password.setText("");
                                confirm_password.setText("");
                                names.setText("");
                                email.setText("");
                                confirm_password.setText("");

                            }
                        });

                    }
                }
            }
        },json);

    }
}
