package hu.ait.android.readinglistapp.ListsPackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import hu.ait.android.readinglistapp.LoadBooksActivity;
import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Book;

public class LoadBooksAdapter extends RecyclerView.Adapter<LoadBooksAdapter.ViewHolder> {

    private Context context;
    private String userId;
    private int lastPosition = -1;
    private List<Book> bookList;
    private List<String> bookKeys;
    private BooksAdapter booksAdapter;


    public LoadBooksAdapter(Context context, List<Book> bookList) { //} BooksAdapter booksAdapter) {
        this.context = context;

        this.bookList = bookList;
        // bookKeys = new ArrayList<String>();

        //this.booksAdapter = booksAdapter;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_book, parent, false);
        LoadBooksAdapter.ViewHolder vh = new LoadBooksAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvDesc.setText(book.getDesc());

        if (book.getUrl().equals("")) {
            holder.ivCover.setImageResource(R.drawable.book);
        } else {
            Glide.with(context).load(book.getUrl()).into(holder.ivCover);
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getDesc(), book.getUrl());
                ((LoadBooksActivity)context).addBookToFirebase(newBook);

            }
        });

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
        public Button btnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivCover = itemView.findViewById(R.id.ivCover);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

}
