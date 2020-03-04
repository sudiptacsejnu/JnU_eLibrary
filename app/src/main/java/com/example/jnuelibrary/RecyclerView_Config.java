package com.example.jnuelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<BookInformation> bookInformations, List<String> keys){
        mContext = context;
        mBooksAdapter = new BooksAdapter(bookInformations, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView titleTV;
        private TextView writterTV;
        private TextView catagoryTV;
        private TextView descriptionTV;

        private String key;

        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.book_list_item, parent, false));

            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
            writterTV = (TextView) itemView.findViewById(R.id.writerTV);
            catagoryTV = (TextView) itemView.findViewById(R.id.catagoryTV);
            descriptionTV = (TextView) itemView.findViewById(R.id.descriptionTV);

        }
        public void bind(BookInformation bookInformation, String key){
            titleTV.setText(bookInformation.getBname());
            writterTV.setText(bookInformation.getBwritter());
            catagoryTV.setText(bookInformation.getBcatagory());
            descriptionTV.setText(bookInformation.getBdescription());
        }
    }
    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<BookInformation> mBookList;
        private List<String> mKeys;

        public BooksAdapter(List<BookInformation> mBookList, List<String> mKeys) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}
