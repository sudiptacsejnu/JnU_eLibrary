package com.example.jnuelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<BookInformation> bookInformations;
    private OnItemClickListener listener;

    public MyAdapter(Context context, List<BookInformation> bookInformations) {
        this.context = context;
        this.bookInformations = bookInformations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookInformation bookInformation = bookInformations.get(position);
        holder.titleTV.setText(bookInformation.getBname());
        holder.writterTV.setText(bookInformation.getBwritter());
        holder.catagoryTV.setText(bookInformation.getBcatagory());
        holder.descriptionTV.setText(bookInformation.getBdescription());
    }

    @Override
    public int getItemCount() {
        return bookInformations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTV;
        private TextView writterTV;
        private TextView catagoryTV;
        private TextView descriptionTV;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            writterTV = itemView.findViewById(R.id.writerTV);
            catagoryTV = itemView.findViewById(R.id.catagoryTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null)
            {
                int position = getAdapterPosition();

                if(position!=RecyclerView.NO_POSITION)
                {
                    listener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }
}
