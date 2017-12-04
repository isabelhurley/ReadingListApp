package hu.ait.android.readinglistapp.ListsPackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import hu.ait.android.readinglistapp.R;
import hu.ait.android.readinglistapp.data.Booklist;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {

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

    private List<Booklist> booklistList;
    private Context context;
    private int lastPosition = -1;

    public ListsAdapter(List<Booklist> booklistList, Context context) {
        this.booklistList = booklistList;
        this.context = context;
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
        viewHolder.tvListName.setText(booklistList.get(position).getListName());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removeBooklist(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void addBooklist(Booklist booklist) {
        booklistList.add(booklist);
        notifyDataSetChanged();
    }

    public void updateBooklist(int index, Booklist booklist) {
        booklistList.set(index, booklist);
        notifyItemChanged(index);

    }

    public void removeBooklist(int index) {
        /*
        ((MainActivity)context).deleteBooklist(booklistList.get(index));
        booklistList.remove(index);
        notifyItemRemoved(index);
        */
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