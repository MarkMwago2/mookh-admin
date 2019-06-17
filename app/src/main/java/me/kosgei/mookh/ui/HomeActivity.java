package me.kosgei.mookh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.adapters.GroupListAdapter;
import me.kosgei.mookh.ui.model.Group;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.groups_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fabAddGroup)
    FloatingActionButton addGroup;

    private GroupListAdapter groupListAdapter;
    List<Group> groupList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        groupList.add(new Group("Marketers",1));
        groupList.add(new Group("Dev",1));
        groupList.add(new Group("Sales",1));
        groupList.add(new Group("Creative",1));
        groupList.add(new Group("Managers",1));


        addGroup.setOnClickListener(this);



        getGroups();
    }

    private void getGroups() {
        groupListAdapter = new GroupListAdapter(groupList,getApplicationContext());
        recyclerView.setAdapter(groupListAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }


    public void showAddGroupFragment()
    {
        FragmentManager fm = this.getSupportFragmentManager();
        AddGroupDialogFragment addGroupDialogFragment = new AddGroupDialogFragment();
        addGroupDialogFragment.show(fm,"");

    }

    @Override
    public void onClick(View v) {

        if (v ==addGroup )
        {
            showAddGroupFragment();
        }

    }

    public void addGroup(String name)
    {
        groupList.add(new Group(name,1));
        groupListAdapter.notifyDataSetChanged();
    }
}
