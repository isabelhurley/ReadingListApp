package hu.ait.android.readinglistapp.ListsPackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Book;
import hu.ait.android.readinglistapp.data.Booklist;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    public static final String BOOKLISTS = "booklists";
    public static final String BOOKS = "books";
    public static final String USERS = "users";

    private List<Book> bookList;
    private List<String> bookKeys;
    private Context context;
    private String currUserId;
    private String currListId;
    private int lastPosition = -1;
    private DatabaseReference booklistRef;

    public BooksAdapter(Context context, String userId, String listId) {
        this.context = context;
        this.currUserId = userId;
        this.currListId = listId;

        bookList = new ArrayList<Book>();
        bookKeys = new ArrayList<String>();

        booklistRef = FirebaseDatabase.getInstance()
                .getReference(USERS)
                .child(currUserId)
                .child(BOOKLISTS)
                .child(currListId)
                .child(BOOKS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
        public TextView tvDesc;
        public Button btnDelBook;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            btnDelBook = itemView.findViewById(R.id.btnDelBook);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_display_book, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Book book = bookList.get(position);
        viewHolder.tvTitle.setText(book.getTitle());
        viewHolder.tvAuthor.setText(book.getAuthor());
        viewHolder.tvDesc.setText(book.getDesc());

        viewHolder.btnDelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeBook(viewHolder.getAdapterPosition());
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

    public void removeBook(int index) {
        booklistRef.child(bookKeys.get(index)).removeValue();
        bookList.remove(index);
        notifyItemRemoved(index);
    }

    public void removeBookByKey(String key) {
        int index = bookKeys.indexOf(key);
        if (index != -1) {
            bookKeys.remove(index);
            bookList.remove(index);
            notifyItemRemoved(index);
        }
    }
}
