package me.kosgei.mookh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.AddMembersFragment;
import me.kosgei.mookh.R;
import me.kosgei.mookh.SendMailToAllFragment;
import me.kosgei.mookh.SendMessageDialogFragment;
import me.kosgei.mookh.SendSmsToAllFragment;
import me.kosgei.mookh.ui.adapters.GroupListAdapter;
import me.kosgei.mookh.ui.adapters.MemberListAdapter;
import me.kosgei.mookh.ui.model.User;

public class GroupMembersActivity extends AppCompatActivity  implements View.OnClickListener{
    @BindView(R.id.member_list)
    RecyclerView recyclerView;

    @BindView(R.id.add_member)
    Button addMember;

    @BindView(R.id.email_all)
    Button emailAll;

    @BindView(R.id.text_all)
    Button textAll;

    private MemberListAdapter memberListAdapter;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        ButterKnife.bind(this);


        addMember.setOnClickListener(this);
        emailAll.setOnClickListener(this);
        textAll.setOnClickListener(this);


        userList.add(new User("john","doe","johndoe@gmail.com","0733332630"));
        userList.add(new User("jane","doe","johndoe@gmail.com","0733332630"));
        userList.add(new User("jack","doe","johndoe@gmail.com","0733332630"));
        userList.add(new User("peter","doe","johndoe@gmail.com","0733332630"));

        getMembers();
    }

    private void getMembers() {
        memberListAdapter = new MemberListAdapter(userList,getApplicationContext());
        recyclerView.setAdapter(memberListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        if (v == addMember)
        {
            showAddGroupFragment();
        }

        if (v == emailAll)
        {
            FragmentManager fm = this.getSupportFragmentManager();
            SendMailToAllFragment sendMailToAllFragment = new SendMailToAllFragment();
            sendMailToAllFragment.show(fm,"");

        }
        if (v == textAll)
        {
            FragmentManager fm = this.getSupportFragmentManager();
            SendSmsToAllFragment sendSmsToAllFragment = new SendSmsToAllFragment();
            sendSmsToAllFragment.show(fm,"");

        }


    }

    public void showAddGroupFragment()
    {
        FragmentManager fm = this.getSupportFragmentManager();
        AddMembersFragment addMembersFragment = new AddMembersFragment();
        addMembersFragment.show(fm,"");

    }
}
