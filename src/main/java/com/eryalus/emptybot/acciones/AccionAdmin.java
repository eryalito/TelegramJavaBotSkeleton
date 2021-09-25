/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.acciones;

import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.estados.Estado;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.persistence.repositories.RepositoryManager;
import com.eryalus.emptybot.principal.BotTelegram;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author eryalus
 */
public class AccionAdmin implements Action {

    private final Update UPDATE;
    private final BotTelegram PARENT;

    public AccionAdmin(Update UPDATE, BotTelegram PARENT) {
        this.UPDATE = UPDATE;
        this.PARENT = PARENT;
    }

    @Override
    public boolean action() {
        if (UPDATE.getMessage().getChat().isUserChat()) {
            Message mensaje = UPDATE.getMessage();
            Chat chat = mensaje.getChat();
            Person person = RepositoryManager.getPersonRepository().addIfNotExists(new Person(chat));
            ArrayList<Send> ms = new ArrayList<>();
            if (mensaje.hasText()) {
                switch (person.getState()) {
                    case Estado.ESTADO_GENERAL_ADMIN:
                        ms = new com.eryalus.emptybot.estados.EstadoGeneralAdmin(chat,  mensaje, PARENT).response(ms);
                        break;
                    default:
                        return false;

                }
            }
            if (ms.isEmpty()) {
                return false;
            }
            for (Send m : ms) {
                try {
                    m.send(chat.getId(), PARENT);
                } catch (TelegramApiException ex) {
                    Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        return true;
    }

}
