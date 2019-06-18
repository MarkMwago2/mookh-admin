package me.kosgei.mookh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.adapters.GroupListAdapter;
import me.kosgei.mookh.ui.adapters.MemberListAdapter;
import me.kosgei.mookh.ui.model.User;

public class GroupMembersActivity extends AppCompatActivity {
    @BindView(R.id.member_list)
    RecyclerView recyclerView;

    private MemberListAdapter memberListAdapter;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        ButterKnife.bind(this);



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
}
