package com.example.jnuelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterUser extends RecyclerView.Adapter<MyAdapterUser.MyViewHolder> {

    private Context context;
    private List<User> users;
    private MyAdapterUser.OnItemClickListener listener;

    public MyAdapterUser(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyAdapterUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_list_item, parent, false);
        return new MyAdapterUser.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterUser.MyViewHolder holder, int position) {
        User user = users.get(position);
        holder.seeUserNameTV.setText(user.getName());
        holder.seeUserIDTV.setText(user.getStudentID());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView seeUserNameTV;
        private TextView seeUserIDTV;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            seeUserNameTV = itemView.findViewById(R.id.seeUserNameTV);
            seeUserIDTV = itemView.findViewById(R.id.seeUserIDTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MyAdapterUser.OnItemClickListener listener) {

        this.listener = listener;

    }
}
