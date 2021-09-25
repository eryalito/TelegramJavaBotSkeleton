/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.principal;

import com.eryalus.emptybot.acciones.AutoDump;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author ad_ri
 */
public class BotTelegram extends TelegramLongPollingBot {

    private String USERNAME, TOKEN;
    protected AutoDump autodump;
    private ArrayList<RespuestaConsulta> consultas = new ArrayList<>();
    private boolean finish = false;

    public synchronized void remove(RespuestaConsulta consulta) {
        consultas.remove(consulta);
        if (consultas.isEmpty() && finish) {
            System.exit(0);
        }
    }

    public AutoDump getAutodump() {
        return autodump;
    }

    public AutoDump setAutodump(AutoDump autodump) {
        this.autodump = autodump;
        return autodump;
    }

    /**
     * true para hacer log de todo y false para hacer log solo de los errores
     */
    private boolean flag_log = false;

    public boolean isFullLog() {
        return flag_log;
    }

    public boolean setFlagLog(boolean flag) {
        boolean temp = flag_log;
        flag_log = flag;
        return temp;
    }

    /**
     *
     * @return
     */
    @Override
    public String getBotToken() {
        return TOKEN;
    }

    public void stop() {
        this.finish = true;
        if (consultas.isEmpty()) {
            System.exit(0);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (finish && update.hasMessage()) {
            SendMessage m = new SendMessage();
            m.setText("Try later.");
            m.setChatId("" + update.getMessage().getChatId());
            try {
                this.execute(m);
            } catch (TelegramApiException ex) {
                Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            RespuestaConsulta hilo = new RespuestaConsulta(update, this);
            consultas.add(hilo);
            hilo.start();
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> list) {
        for (Update u : list) {
            this.onUpdateReceived(u);
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

}
