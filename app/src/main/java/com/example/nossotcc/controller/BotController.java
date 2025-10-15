package com.example.nossotcc.controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BotController {

    private Context ctx;
    private HashMap<String, QAPair> qaMap;
    private List<String> chatHistory;


    private final String QA_FILE = "qa_data.txt";
    private final String HISTORY_FILE = "chat_history.txt";

    public BotController(Context ctx) {
        this.ctx = ctx;
        qaMap = new HashMap<>();
        chatHistory = new ArrayList<>();
        loadQA();
        loadHistory();
        teachInternal(); // Ensina internamente ao iniciar
    }

    // --- ENSINO INTERNO SOMENTE ADMIN ---
    private void teachInternal() {
        teach("O que é uma conta corrente?", "Uma conta corrente é uma conta bancária que permite depósitos, saques e pagamentos. É essencial para gerenciar seu dinheiro no país.",
                List.of("conta corrente", "banco", "gerenciar dinheiro"));

        teach("Como posso enviar dinheiro para meu país de origem?", "Você pode usar serviços de transferência internacional como Wise, Western Union ou bancos tradicionais.",
                List.of("enviar dinheiro", "transferência", "país de origem"));

        teach("Como faço para economizar dinheiro?", "Crie um orçamento mensal, registre seus gastos e priorize despesas essenciais. Isso ajuda a economizar mesmo em um novo país.",
                List.of("economizar", "dinheiro", "orçamento"));

        teach("O que é um cartão de crédito e como usar?", "Um cartão de crédito permite compras parceladas. Use com cuidado, pagando a fatura integralmente para evitar juros altos.",
                List.of("cartão de crédito", "juros", "compras"));

        teach("Como evitar dívidas?", "Evite gastar mais do que ganha, acompanhe seus gastos e use crédito somente quando necessário.",
                List.of("dívidas", "gastos", "crédito"));

        teach("O que meu aplicativo faz?", "Este aplicativo ajuda imigrantes a gerenciar suas finanças, controlar gastos e planejar o orçamento mensal.",
                List.of("aplicativo", "gestão financeira", "imigrantes"));
    }

    // Ensinar internamente (somente admin)
    private void teach(String question, String answer, List<String> keywords) {
        String norm = normalize(question);
        qaMap.put(norm, new QAPair(answer, keywords));
        saveQA();
    }

    // Responder mensagens
    public String getResponse(String inputRaw) {
        String input = normalize(inputRaw);
        String bestAnswer = null;
        double bestScore = 0.0;

        for (String qNorm : qaMap.keySet()) {
            QAPair pair = qaMap.get(qNorm);

            double score = similarity(input, qNorm);

            int matchedKeywords = 0;
            for (String kw : pair.keywords) {
                if (input.contains(kw.toLowerCase(Locale.getDefault()))) {
                    matchedKeywords++;
                }
            }
            double keywordBoost = (double) matchedKeywords / Math.max(1, pair.keywords.size());
            score = Math.max(score, keywordBoost);

            if (score > bestScore) {
                bestScore = score;
                bestAnswer = pair.answer;
            }
        }

        if (bestAnswer != null) {
            addChatHistory("Bot: " + bestAnswer);
            return bestAnswer;
        }

        String unknown = "Ainda não sei responder isso.";
        addChatHistory("Bot: " + unknown);
        return unknown;
    }

    // Histórico
    private void addChatHistory(String text) {
        chatHistory.add(text);
        try (FileOutputStream fos = ctx.openFileOutput(HISTORY_FILE, Context.MODE_APPEND);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write(text);
            bw.newLine();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    private void loadHistory() {
        chatHistory.clear();
        try (FileInputStream fis = ctx.openFileInput(HISTORY_FILE);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = br.readLine()) != null) {
                chatHistory.add(line);
            }
        } catch (Exception e) {
            // arquivo ainda não existe
        }
    }

    // QA persistente
    private void saveQA() {
        try (FileOutputStream fos = ctx.openFileOutput(QA_FILE, Context.MODE_PRIVATE);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            for (String q : qaMap.keySet()) {
                QAPair p = qaMap.get(q);
                StringBuilder sb = new StringBuilder();
                sb.append(q).append("||");
                sb.append(p.answer).append("||");
                for (String kw : p.keywords) sb.append(kw).append(",");
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadQA() {
        qaMap.clear();
        try (FileInputStream fis = ctx.openFileInput(QA_FILE);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|\\|");
                if (parts.length >= 2) {
                    String q = parts[0];
                    String ans = parts[1];
                    List<String> kws = new ArrayList<>();
                    if (parts.length > 2 && !parts[2].isEmpty()) {
                        for (String k : parts[2].split(",")) kws.add(k);
                    }
                    qaMap.put(q, new QAPair(ans, kws));
                }
            }
        } catch (Exception e) {}
    }

    // Normalização e similaridade
    private String normalize(String s) {
        return s.toLowerCase(Locale.getDefault())
                .replaceAll("[^a-z0-9áàâãéèêíïóôõöúçñ ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private double similarity(String a, String b) {
        if (a.length() == 0 && b.length() == 0) return 1.0;
        int dist = levenshtein(a, b);
        int max = Math.max(a.length(), b.length());
        if (max == 0) return 0;
        return 1.0 - ((double) dist / max);
    }

    private int levenshtein(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        int[] prev = new int[len2 + 1];
        int[] cur = new int[len2 + 1];
        for (int j = 0; j <= len2; j++) prev[j] = j;
        for (int i = 1; i <= len1; i++) {
            cur[0] = i;
            for (int j = 1; j <= len2; j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                cur[j] = Math.min(Math.min(cur[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            System.arraycopy(cur, 0, prev, 0, cur.length);
        }
        return prev[len2];
    }

    private static class QAPair {
        String answer;
        List<String> keywords;
        QAPair(String a, List<String> k) { answer = a; keywords = k; }
    }
}
