package hu.ait.android.readinglistapp.ListsPackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.readinglistapp.MenuActivity;
import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Booklist;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {

    public static final String CURR_USER_ID = "currUserId";
    public static final String BOOKLISTS = "booklists";
    public static final String USERS = "users";

    private List<Booklist> booklistList;
    private List<String> booklistKeys;
    private Context context;
    private String currUserId;
    private int lastPosition = -1;
    private DatabaseReference booklistRef;

    public ListsAdapter(Context context, String userId) {
        this.context = context;
        this.currUserId = userId;

        booklistList = new ArrayList<Booklist>();
        booklistKeys = new ArrayList<String>();

        booklistRef = FirebaseDatabase.getInstance()
                .getReference(USERS)
                .child(currUserId)
                .child(BOOKLISTS);
    }

    public ListsAdapter(Context context) {
        this.context = context;

        booklistList = new ArrayList<Booklist>();
        booklistKeys = new ArrayList<String>();

        booklistRef = FirebaseDatabase.getInstance().getReference();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvListName;
        public ImageView ivEditBooklist;

        public ViewHolder(View itemView) {
            super(itemView);
            tvListName = (TextView) itemView.findViewById(R.id.tvListName);
            ivEditBooklist = itemView.findViewById(R.id.ivEditBooklist);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Booklist booklist = booklistList.get(position);
        viewHolder.tvListName.setText(booklist.getListName());

        viewHolder.ivEditBooklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String booklistKey = booklistKeys.get(position);
                ((MenuActivity) context).startEditBooklistActivity(booklistKey, booklist.getListName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return booklistList.size();
    }

    public void addBooklist(Booklist booklist, String key) {
        booklistList.add(booklist);
        booklistKeys.add(key);
        notifyDataSetChanged();
    }

    public void updateBooklist(int index, Booklist booklist) {
        booklistList.set(index, booklist);
        notifyItemChanged(index);
    }

    public void removeBooklist(int index) {
        booklistRef.child(booklistKeys.get(index)).removeValue();
        booklistKeys.remove(index);
        booklistList.remove(index);
        notifyItemRemoved(index);

    }
    public void removeBooklistByKey(String key) {
        booklistRef.child(key).removeValue();
        int index = booklistKeys.indexOf(key);
        if (index != -1) {
            booklistKeys.remove(index);
            booklistList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public void swapBooklists(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(booklistList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(booklistList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Booklist getBooklist(int i) {
        return booklistList.get(i);
    }

}