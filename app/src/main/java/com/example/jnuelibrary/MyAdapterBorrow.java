package com.example.jnuelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterBorrow extends RecyclerView.Adapter<MyAdapterBorrow.MyViewHolder> {

    private Context context;
    private List<BorrowInformation> borrowInformations;
    private MyAdapterBorrow.OnItemClickListener listener;

    public MyAdapterBorrow(Context context, List<BorrowInformation> borrowInformations) {
        this.context = context;
        this.borrowInformations = borrowInformations;
    }

    @NonNull
    @Override
    public MyAdapterBorrow.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.borrow_list_item, parent, false);
        return new MyAdapterBorrow.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterBorrow.MyViewHolder holder, int position) {
        BorrowInformation borrowInformation = borrowInformations.get(position);
        holder.seeBorrowBookIDTV.setText(borrowInformation.getBookID());
        holder.seeBorrowBookUserNameTV.setText(borrowInformation.getUserName());

    }

    @Override
    public int getItemCount() {
        return borrowInformations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView seeBorrowBookIDTV;
        private TextView seeBorrowBookUserNameTV;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            seeBorrowBookIDTV = itemView.findViewById(R.id.seeBorrowBookIDTV);
            seeBorrowBookUserNameTV = itemView.findViewById(R.id.seeBorrowBookUserNameTV);

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

    public void setOnItemClickListener(MyAdapterBorrow.OnItemClickListener listener) {

        this.listener = listener;

    }
}
