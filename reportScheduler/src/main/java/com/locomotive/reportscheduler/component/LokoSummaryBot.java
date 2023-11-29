package com.locomotive.reportscheduler.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LokoSummaryBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "6980637379:AAGMJSskh5cqelOpbRfS4XdksFiMLk4ZdWo";
    private final String CHAT_ID = "1345918218";

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void sendSummaryReport(String summary) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(summary);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
