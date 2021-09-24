/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.acciones;

import com.eryalus.emptybot.principal.BotTelegram;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.estados.Estado;
import com.eryalus.emptybot.estados.EstadoCallback;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author eryalus
 */
public class AccionNoAdminCallBack implements Action {

    private final CallbackQuery UPDATE;
    private final BotTelegram PARENT;
    private static final Long VIDEO_NOTE_PATH = 4L;
    private static final Long STICKERS_PATH = 2L;
    private static final Long VOICE_NOTE_PATH = 3L;

    public AccionNoAdminCallBack(CallbackQuery UPDATE, BotTelegram PARENT) {
        this.UPDATE = UPDATE;
        this.PARENT = PARENT;
    }

    @Override
    public boolean action() {
        if (UPDATE.getMessage().getChat().isUserChat()) {
            Message mensaje = UPDATE.getMessage();
            Chat chat = mensaje.getChat();
            com.eryalus.emptybot.dataBase.People.addPerson(chat, PARENT.getConnection());
            ArrayList<Send> ms = new ArrayList<>();
            Boolean delete = false;
            Integer estado = com.eryalus.emptybot.dataBase.People.getEstado(chat, PARENT.getConnection());
            switch (estado) {
                case Estado.ESTADO_GENERAL:
                    EstadoCallback estado_act = new com.eryalus.emptybot.estados.EstadoCallback(chat, UPDATE, PARENT);
                    ms = estado_act.response(ms);
                    delete = estado_act.getDelete();
                    break;
                default:
                    break;
            }

            if (delete) {
                DeleteMessage dm = new DeleteMessage("" + mensaje.getChatId(), UPDATE.getMessage().getMessageId());
                Send s = new Send();
                s.setDeleteMessage(dm);
                ms.add(s);
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
