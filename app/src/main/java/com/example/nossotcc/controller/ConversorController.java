package com.example.nossotcc.controller;

import android.util.Log;
import com.example.nossotcc.model.Conversao;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConversorController {

    private OkHttpClient client;

    public ConversorController() {
        client = new OkHttpClient();
    }

    public interface ConversaoListener {
        void onSucesso(Conversao conversao);
        void onErro(String mensagem);
    }

    public void converterParaReal(Conversao conversao, ConversaoListener listener) {
        new Thread(() -> {
            try {
                String url = "https://api.exchangerate.host/convert?from="
                        + conversao.getMoedaOrigem() + "&to=BRL&amount=" + conversao.getValorOriginal();

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Log.d("Conversor", "JSON recebido: " + json);

                    JSONObject obj = new JSONObject(json);
                    if (obj.has("result") && !obj.isNull("result")) {
                        double valorConvertido = obj.getDouble("result");
                        conversao.setValorConvertido(valorConvertido);
                        listener.onSucesso(conversao);
                    } else {
                        listener.onErro("Erro ao processar a resposta da API");
                    }
                } else {
                    listener.onErro("Erro HTTP: " + response.code());
                }

            } catch (IOException e) {
                listener.onErro("Erro de conexão: " + e.getMessage());
            } catch (Exception e) {
                listener.onErro("Erro inesperado: " + e.getMessage());
            }
        }).start();
    }
}

