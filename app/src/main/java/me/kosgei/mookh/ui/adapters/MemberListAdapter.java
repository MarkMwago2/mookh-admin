package me.kosgei.mookh.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.AddMembersFragment;
import me.kosgei.mookh.R;
import me.kosgei.mookh.SendMessageDialogFragment;
import me.kosgei.mookh.ui.model.User;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

private List<User> mMembers = new ArrayList<>();
private Context mContext;

public MemberListAdapter (List<User> mMembers, Context mContext) {
        this.mMembers = mMembers;
        this.mContext = mContext;
        }

@NonNull
@Override
public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member, parent, false);
        return new MemberViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.bindGroup(mMembers.get(position));
    }

@Override
public int getItemCount() {
        return mMembers.size();
        }



public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.names)
    TextView names;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.message)
    ImageView messageImageView;
    @BindView(R.id.emailIV)
            ImageView emailImageView;

    User user;


    private Context mContext;

    public MemberViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();

        messageImageView.setOnClickListener(this);
        emailImageView.setOnClickListener(this);


    }

    public void bindGroup(User user) {
        this.user = user;
       names.setText("Name: " +user.getFirstName() +" " +user.getLastName());
       email.setText("Email: " +user.getEmail());
       phone.setText("Phone: " +user.getPhone());
    }

    @Override
    public void onClick(View v) {
//        int itemPosition = getLayoutPosition();
//        mContext.startActivity(new Intent(mContext, GroupMembersActivity.class));

        if (v == messageImageView)
        {
            FragmentManager manager= ((AppCompatActivity)mContext).getSupportFragmentManager();
            SendMessageDialogFragment sendMessageDialogFragment = new SendMessageDialogFragment();
            sendMessageDialogFragment.show(manager, "send message");
        }
        if ( v == emailImageView)
        {
            Toast.makeText(mContext, "Email " +user.getEmail(), Toast.LENGTH_SHORT).show();
        }

    }
}
}
