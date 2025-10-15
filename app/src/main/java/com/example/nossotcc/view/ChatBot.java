package com.example.nossotcc.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.BotController;
import com.example.nossotcc.adapter.ChatAdapter;
import com.example.nossotcc.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatBot extends AppCompatActivity {

    private RecyclerView rv;
    private ChatAdapter adapter;
    private List<Message> items = new ArrayList<>();
    private EditText et;
    private ImageButton btnSend;
    private BotController bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // Inicializa o bot com respostas internas já configuradas
        bot = new BotController(this);

        rv = findViewById(R.id.rvMessages);
        et = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Configura RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(items);
        rv.setAdapter(adapter);

        // Botão enviar mensagem
        btnSend.setOnClickListener(v -> {
            String text = et.getText().toString().trim();
            if (text.isEmpty()) return;

            addMessage("Você", text);
            et.setText("");

            // Bot responde
            String resp = bot.getResponse(text);
            addMessage("Bot", resp);
        });

        // Carrega histórico do chat
        for (String line : bot.getChatHistory()) {
            addMessage("Histórico", line);
        }
    }

    // Adiciona mensagem na lista e atualiza RecyclerView
    private void addMessage(String author, String text) {
        items.add(new Message(author, text));
        adapter.notifyItemInserted(items.size() - 1);
        rv.scrollToPosition(items.size() - 1);
    }
}
