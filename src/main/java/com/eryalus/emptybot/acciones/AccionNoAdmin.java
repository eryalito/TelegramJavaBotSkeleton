/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.acciones;

import com.eryalus.emptybot.principal.BotTelegram;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.estados.Estado;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.persistence.repositories.RepositoryManager;

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
public class AccionNoAdmin implements Action {

    private final Update UPDATE;
    private final BotTelegram PARENT;
    private static final Long VIDEO_NOTE_PATH = 4L;
    private static final Long STICKERS_PATH = 2L;
    private static final Long VOICE_NOTE_PATH = 3L;

    public AccionNoAdmin(Update UPDATE, BotTelegram PARENT) {
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
            if (mensaje.hasDocument()) {
            } else if (mensaje.hasPhoto()) {
            } else if (mensaje.getAudio() != null) {
            } else if (mensaje.getVoice() != null) {
            } else if (mensaje.hasVideo()) {
            } else if (mensaje.hasVideoNote()) {
            } else if (mensaje.hasSticker()) {
            } else if (mensaje.hasText()) {
                switch (person.getState()) {
                    case Estado.ESTADO_GENERAL:
                        ms = new com.eryalus.emptybot.estados.EstadoGeneral(chat, mensaje, PARENT).response(ms);
                        break;
                    default:
                        break;                }
            }
            if (ms.isEmpty()) {
                
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
