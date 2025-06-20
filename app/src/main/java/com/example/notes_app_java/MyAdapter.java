package com.example.notes_app_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Notes> noteList;

    public MyAdapter(Context context, RealmResults<Notes> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes notes = noteList.get(position);
        holder.titleOutput.setText(notes.getTitle());
        holder.descOutput.setText(notes.getDescription());
        String formatedTime = DateFormat.getDateTimeInstance().format(notes.getCreatedTime());
        holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenu().add("DELETE");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            notes.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Notes Deleted", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descOutput;
        TextView timeOutput;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleOutput = itemView.findViewById(R.id.titleOutput);
            descOutput = itemView.findViewById(R.id.descriptionOutput);
            timeOutput = itemView.findViewById(R.id.timeOutput);
        }
    }
}
