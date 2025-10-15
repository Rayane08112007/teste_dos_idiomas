package com.example.nossotcc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nossotcc.R;
import com.example.nossotcc.model.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    List<Message> items;

    public ChatAdapter(List<Message> items) {
        this.items = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Message m = items.get(position);
        holder.tvAuthor.setText(m.author);
        holder.tvText.setText(m.text);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvText;

        Holder(View v) {
            super(v);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvText = v.findViewById(R.id.tvText);
        }
    }
}
