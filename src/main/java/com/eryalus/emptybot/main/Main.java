/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.main;

import com.eryalus.emptybot.acciones.AutoDump;
import com.eryalus.emptybot.data.BotParameters;
import com.eryalus.emptybot.persistence.util.HibernateUtil;
import com.eryalus.emptybot.principal.BotTelegram;
import com.eryalus.emptybot.principal.CLI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


/**
 *
 * @author eryalus
 */
public class Main {

    private static final long TIEMPO_GENERACION_COPIA_SEGURIDAD = 86400000L; //un día
    private static final String PARAMETERS_FILE_PATH = "parameters.json";
    public static BotParameters PARAMETERS;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PARAMETERS = objectMapper.readValue(new File(PARAMETERS_FILE_PATH), BotParameters.class);
        } catch (FileNotFoundException ex) {
            try {
                objectMapper.writeValue(new File(PARAMETERS_FILE_PATH), new BotParameters());
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
            System.err.println("No se ha encontrado el fichero de configuración \"" + PARAMETERS_FILE_PATH + "\". Se ha creado uno de ejemplo.");
            return;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        System.out.println("Inicializando hibernate");
        HibernateUtil.init(PARAMETERS.getDB_user(), PARAMETERS.getDB_password(), ""+PARAMETERS.getDB_port(), PARAMETERS.getDB_database(), PARAMETERS.getDB_ip());
        System.out.println("Hibernate inicializado");

        BotTelegram bot = iniciarBot();
        AutoDump dump = new AutoDump(PARAMETERS.getDB_password(), PARAMETERS.getDB_user(), "127.0.0.1", bot, TIEMPO_GENERACION_COPIA_SEGURIDAD);
        dump.start();
        bot.setAutodump(dump);
        new CLI(bot).menu();
    }

    private static BotTelegram iniciarBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotTelegram bot = new BotTelegram();
            bot.setTOKEN(Main.PARAMETERS.getBot_token());
            bot.setUSERNAME(Main.PARAMETERS.getBot_username());
            botsApi.registerBot(bot);
            System.out.println("Bot iniciado" + EmojiParser.parseToUnicode(" :smile: :alien:"));
            return bot;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
