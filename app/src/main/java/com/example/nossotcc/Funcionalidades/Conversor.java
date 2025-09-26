package com.example.nossotcc.Funcionalidades;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Conversor {

    private OkHttpClient client;

    public Conversor() {
        client = new OkHttpClient();
    }

    public interface ConversaoCallback {
        void onResultado(double valorConvertido);
        void onErro(String mensagem);
    }

    public void converterParaReal(String moedaOrigem, double valor, ConversaoCallback callback) {
        new Thread(() -> {
            try {
                String url = "https://api.exchangerate.host/convert?from=" + moedaOrigem + "&to=BRL&amount=" + valor;
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject obj = new JSONObject(json);
                    double resultado = obj.getDouble("result");
                    callback.onResultado(resultado);
                } else {
                    callback.onErro("Erro ao converter");
                }
            } catch (IOException e) {
                callback.onErro("Erro de conexão");
            } catch (Exception e) {
                callback.onErro("Erro inesperado");
            }
        }).start();
    }
}

