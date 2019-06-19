package me.kosgei.mookh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.adapters.GroupListAdapter;
import me.kosgei.mookh.ui.loginsignup.LoginSignUpActivity;
import me.kosgei.mookh.ui.model.Group;
import me.kosgei.mookh.ui.services.MookhService;
import me.kosgei.mookh.utility.SaveSharedPreference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.groups_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fabAddGroup)
    FloatingActionButton addGroup;

    private GroupListAdapter groupListAdapter;
    List<Group> groupList = new ArrayList<>();

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        createProgressDialog();


//        groupList.add(new Group("Marketers",1));
//        groupList.add(new Group("Devs",1));
//        groupList.add(new Group("Sales",1));
//        groupList.add(new Group("Creatives",1));
//        groupList.add(new Group("Managers",1));


        addGroup.setOnClickListener(this);



        getGroups();
    }

    private void getGroups() {
        progressDialog.show();

        MookhService mookhService = new MookhService();

        mookhService.getGroups(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                groupList = mookhService.processGroupResult(response);

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.hide();

                        groupListAdapter = new GroupListAdapter(groupList,getApplicationContext());
                        recyclerView.setAdapter(groupListAdapter);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);

                    }
                });
            }
        },getApplicationContext());
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
       progressDialog.show();
       MookhService mookhService = new MookhService();
        mookhService.addGroup(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.hide();
                        getGroups();
                        groupListAdapter.notifyDataSetChanged();
                    }
                });

            }
        },name,getApplicationContext());
    }



    // Overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SaveSharedPreference.setLoggedIn(getApplicationContext(), false);


        Intent intent = new Intent(HomeActivity.this, LoginSignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }
}
