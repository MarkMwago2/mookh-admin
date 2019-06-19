package me.kosgei.mookh.ui;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.kosgei.mookh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupDialogFragment extends DialogFragment {


    public AddGroupDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_group_dialog, container, false);

        Button cancelButton = view.findViewById(R.id.cancel);
        Button submitButton = view.findViewById(R.id.save);
        EditText groupName = view.findViewById(R.id.names);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groupName.getText().toString().trim().isEmpty())
                {
                    ((HomeActivity) getActivity()).addGroup(groupName.getText().toString());
                    dismiss();
                }
                else {
                   groupName.setError("Please enter the group name");
                }




            }
        });

        return view;
    }

}
