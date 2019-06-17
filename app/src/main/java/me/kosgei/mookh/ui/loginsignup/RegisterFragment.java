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


import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.names) EditText names;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirm_password) EditText confirm_password;
    @BindView(R.id.sign_up)
    Button signUp;



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


            }
        }

    }
}
