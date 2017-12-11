package hu.ait.android.readinglistapp.ListsPackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Book;
import hu.ait.android.readinglistapp.data.Booklist;

public class LoadBooksAdapter extends RecyclerView.Adapter<LoadBooksAdapter.ViewHolder> {

    private Context context;
    private String userId;
    private int lastPosition = -1;
    private List<Book> bookList;
    private List<String> bookKeys;


    public LoadBooksAdapter(Context context, List<Book> bookList) {
        this.context = context;

        this.bookList = bookList;
        // bookKeys = new ArrayList<String>();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_book, parent, false);
        LoadBooksAdapter.ViewHolder vh = new LoadBooksAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvDesc.setText(book.getDesc());

        // set picture!! TODO
        holder.ivCover.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public void addBook(Book book, String key) {
        bookList.add(book);
        bookKeys.add(key);
        notifyDataSetChanged();
    }

    public void updateBooklist(int index, Book book) {
        bookList.set(index, book);
        notifyItemChanged(index);
    }

    public void removeBooklist(int index) {
        bookKeys.remove(index);
        bookList.remove(index);
        notifyItemRemoved(index);

        /*
        booklistRef.child("booklists").child(booklistKeys.get(index)).removeValue();
        booklistKeys.remove(index);
        booklistList.remove(index);
        notifyItemRemoved(index);
        */
    }

    public void swapBooks(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(bookList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(bookList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Book getBook(int i) {
        return bookList.get(i);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvAuthor;
        public TextView tvDesc;
        public ImageView ivCover;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivCover = itemView.findViewById(R.id.ivCover);

        }
    }

}
