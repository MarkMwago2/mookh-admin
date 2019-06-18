package me.kosgei.mookh.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.mookh.R;
import me.kosgei.mookh.ui.GroupMembersActivity;
import me.kosgei.mookh.ui.model.Group;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private List<Group> mGroups = new ArrayList<>();
    private Context mContext;

    public GroupListAdapter(List<Group> mGroups, Context mContext) {
        this.mGroups = mGroups;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bindGroup(mGroups.get(position));

    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }



    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.group_name)
        TextView groupName;


        private Context mContext;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindGroup(Group group) {
           groupName.setText(group.getName());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mContext.startActivity(new Intent(mContext, GroupMembersActivity.class));

        }
    }
}
