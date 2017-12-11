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
import java.util.Collections;
import java.util.List;

import butterknife.OnClick;
import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Booklist;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {

    private List<Booklist> booklistList;
    private List<String> booklistKeys;
    private Context context;
    private String userId;
    private int lastPosition = -1;
    private DatabaseReference booklistRef;

    public ListsAdapter(Context context, String userId) {
        this.context = context;
        this.userId = userId;

        booklistList = new ArrayList<Booklist>();
        booklistKeys = new ArrayList<String>();

        booklistRef = FirebaseDatabase.getInstance().getReference();
    }

    public ListsAdapter(Context context) {
        this.context = context;

        booklistList = new ArrayList<Booklist>();
        booklistKeys = new ArrayList<String>();

        booklistRef = FirebaseDatabase.getInstance().getReference();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvListName;
        public Button btnDelete;
        public Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvListName = (TextView) itemView.findViewById(R.id.tvListName);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
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
        Booklist booklist = booklistList.get(position);
        viewHolder.tvListName.setText(booklist.getListName());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBooklist(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //change to tv OnClickListener TODO
                /*
                ((MainActivity) context).showEditBooklistActivity(
                        booklistList.get(viewHolder.getAdapterPosition()).getPlaceID(),
                        viewHolder.getAdapterPosition());
                        */
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

        booklistRef.child("booklists").child(booklistKeys.get(index)).removeValue();
        booklistKeys.remove(index);
        booklistList.remove(index);
        notifyItemRemoved(index);

    }
    public void removeBooklistByKey(String key) {
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