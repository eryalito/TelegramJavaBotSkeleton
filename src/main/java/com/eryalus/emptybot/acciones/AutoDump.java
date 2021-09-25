/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.acciones;

import com.eryalus.emptybot.principal.BotTelegram;
import com.eryalus.emptybot.utils.time.Timer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author eryalus
 */
public class AutoDump extends Timer {

    private final String pass, user, host;
    private final BotTelegram bot;
    private ArrayList<Long> people = new ArrayList<>();
    private boolean action = true;

    public AutoDump(String pass, String user, String host, BotTelegram bot, long tiempo) {
        super(tiempo);
        this.pass = pass;
        this.user = user;
        this.host = host;
        this.bot = bot;
    }

    /**
     *
     * @param people
     * @param type true for select all of the people
     */
    public void forceDump(ArrayList<Long> people, boolean type) {
        if (type) {
            action();
        } else {
            this.people = people;
            action = false;
            action();
            action = true;
        }
    }

    private void send(ArrayList<Long> people, String path) {
        SendDocument sd = new SendDocument();
        sd.setDocument(new InputFile(new File(path)));
        for (Long id : people) {
            sd.setChatId("" + id);
            try {
                bot.execute(sd);
            } catch (TelegramApiException ex) {
            }
        }
    }

    @Override
    protected void action() {
        BufferedWriter bw = null;
        try {
            String output = new com.eryalus.emptybot.dataBase.Dump().getDump(pass, user, host);
            new File("DBBackup").mkdirs();
            String path = "DBBackup/" + Instant.now().toString() + ".sql";
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(output);
            bw.close();
            ArrayList<Long> ids;
            if (action) {
                ids = new ArrayList<>();//Basics.getLog(bot.getConnection());
            } else {
                ids = people;
            }
            send(ids, path);
        } catch (IOException ex) {
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
            }
        }
    }

}
