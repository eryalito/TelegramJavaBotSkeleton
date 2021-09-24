/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.data;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author eryalus
 */
public class Send {

    private SendAudio sendAudio;
    private SendAnimation sendAnimation;
    private SendChatAction sendChatAction;
    private SendContact sendContact;
    private SendDocument sendDocument;
    private SendGame sendGame;
    private SendInvoice sendInvoice;
    private SendLocation sendLocation;
    private SendMediaGroup sendMediaGroup;
    private SendMessage sendMessage;
    private SendPhoto sendPhoto;
    private SendSticker sendSticker;
    private SendVenue sendVenue;
    private SendVideo sendVideo;
    private SendVideoNote sendVideoNote;
    private SendVoice sendVoice;
    private DeleteMessage deleteMessage;

    public SendSticker getSendSticker() {
        return sendSticker;
    }

    public void setSendSticker(SendSticker sendSticker) {
        this.sendSticker = sendSticker;
    }

    public DeleteMessage getDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public SendAnimation getSendAninamtio() {
        return sendAnimation;
    }

    public void setSendAninamtio(SendAnimation sendAninamtio) {
        this.sendAnimation = sendAninamtio;
    }

    public SendInvoice getSendInvoice() {
        return sendInvoice;
    }

    public void setSendInvoice(SendInvoice sendInvoice) {
        this.sendInvoice = sendInvoice;
    }

    public SendMediaGroup getSendMediaGroup() {
        return sendMediaGroup;
    }

    public void setSendMediaGroup(SendMediaGroup sendMediaGroup) {
        this.sendMediaGroup = sendMediaGroup;
    }

    public SendVideoNote getSendVideoNote() {
        return sendVideoNote;
    }

    public void setSendVideoNote(SendVideoNote sendVideoNote) {
        this.sendVideoNote = sendVideoNote;
    }

    public void send(Long chat, TelegramLongPollingBot bot) throws TelegramApiException {
        if (sendAudio != null) {
            sendAudio.setChatId("" + chat);
            bot.execute(sendAudio);
        } else if (sendChatAction != null) {
            sendChatAction.setChatId("" + chat);
            bot.execute(sendChatAction);
        } else if (sendContact != null) {
            sendContact.setChatId("" + chat);
            bot.execute(sendContact);
        } else if (sendDocument != null) {
            sendDocument.setChatId("" + chat);
            bot.execute(sendDocument);
        } else if (sendGame != null) {
            sendGame.setChatId("" + chat);
            bot.execute(sendGame);
        } else if (sendLocation != null) {
            sendLocation.setChatId("" + chat);
            bot.execute(sendLocation);
        } else if (sendMessage != null) {
            sendMessage.setChatId("" + chat);
            try {
                sendMessage.setParseMode("html");
                bot.execute(sendMessage);
            } catch (Exception ex) {
                sendMessage.setParseMode("");
                bot.execute(sendMessage);
            }
        } else if (sendPhoto != null) {
            sendPhoto.setChatId("" + chat);
            bot.execute(sendPhoto);
        } else if (sendSticker != null) {
            sendSticker.setChatId("" + chat);
            bot.execute(sendSticker);
        } else if (sendVenue != null) {
            sendVenue.setChatId("" + chat);
            bot.execute(sendVenue);
        } else if (sendVideo != null) {
            sendVideo.setChatId("" + chat);
            bot.execute(sendVideo);
        } else if (sendVoice != null) {
            sendVoice.setChatId("" + chat);
            bot.execute(sendVoice);
        } else if (sendVideoNote != null) {
            sendVideoNote.setChatId("" + chat);
            bot.execute(sendVideoNote);
        } else if (deleteMessage != null) {
            deleteMessage.setChatId("" + chat);
            bot.execute(deleteMessage);
        }
    }

    public void send(TelegramLongPollingBot bot) throws TelegramApiException {
        if (sendAudio != null) {
            bot.execute(sendAudio);
        } else if (sendChatAction != null) {
            bot.execute(sendChatAction);
        } else if (sendContact != null) {
            bot.execute(sendContact);
        } else if (sendDocument != null) {
            bot.execute(sendDocument);
        } else if (sendGame != null) {
            bot.execute(sendGame);
        } else if (sendLocation != null) {
            bot.execute(sendLocation);
        } else if (sendMessage != null) {
            try {
                sendMessage.setParseMode("html");
            } catch (Exception ex) {

            }
            bot.execute(sendMessage);
        } else if (sendPhoto != null) {
            bot.execute(sendPhoto);
        } else if (sendSticker != null) {
            bot.execute(sendSticker);
        } else if (sendVenue != null) {
            bot.execute(sendVenue);
        } else if (sendVideo != null) {
            bot.execute(sendVideo);
        } else if (sendVoice != null) {
            bot.execute(sendVoice);
        } else if (sendVideoNote != null) {
            bot.execute(sendVideoNote);
        } else if (deleteMessage != null) {
            bot.execute(deleteMessage);
        }
    }

    public SendAudio getSendAudio() {
        return sendAudio;
    }

    public void setSendAudio(SendAudio sendAudio) {
        this.sendAudio = sendAudio;
    }

    public SendChatAction getSendChatAction() {
        return sendChatAction;
    }

    public void setSendChatAction(SendChatAction sendChatAction) {
        this.sendChatAction = sendChatAction;
    }

    public SendContact getSendContact() {
        return sendContact;
    }

    public void setSendContact(SendContact sendContact) {
        this.sendContact = sendContact;
    }

    public SendDocument getSendDocument() {
        return sendDocument;
    }

    public void setSendDocument(SendDocument sendDocument) {
        this.sendDocument = sendDocument;
    }

    public SendGame getSendGame() {
        return sendGame;
    }

    public void setSendGame(SendGame sendGame) {
        this.sendGame = sendGame;
    }

    public SendLocation getSendLocation() {
        return sendLocation;
    }

    public void setSendLocation(SendLocation sendLocation) {
        this.sendLocation = sendLocation;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(SendPhoto sendPhoto) {
        this.sendPhoto = sendPhoto;
    }

    public SendSticker getSendSicker() {
        return sendSticker;
    }

    public void setSendSicker(SendSticker sendSicker) {
        this.sendSticker = sendSicker;
    }

    public SendVenue getSendVenue() {
        return sendVenue;
    }

    public void setSendVenue(SendVenue sendVenue) {
        this.sendVenue = sendVenue;
    }

    public SendVideo getSendVideo() {
        return sendVideo;
    }

    public void setSendVideo(SendVideo sendVideo) {
        this.sendVideo = sendVideo;
    }

    public SendVoice getSendVoice() {
        return sendVoice;
    }

    public void setSendVoice(SendVoice sendVoice) {
        this.sendVoice = sendVoice;
    }
}
