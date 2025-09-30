package com.lootnotifier;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook
{
    public static void send(String webhookUrl, String content, int value) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            return;
        }
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String safeContent = escape(content);

            String json = "{"
                    + "\"embeds\":[{"
                    + "\"title\":\"💰 Loot Drop!\","
                    + "\"description\":\"" + safeContent + "\","
                    + "\"color\":" + pickColor(value)
                    + "}]"
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
            conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String escape(String content)
    {
        return content.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    private static int pickColor(int value)
    {
        if (value >= 1_000_000) {
            return 0xFFD700; // Gold
        } else if (value >= 100_000) {
            return 0xC0C0C0; // Silver
        } else {
            return 0xCD7F32; // Bronze
        }
    }
}
