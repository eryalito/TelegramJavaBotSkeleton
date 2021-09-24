/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.principal;

import java.util.Scanner;

/**
 *
 * @author eryalus
 */
public class CLI {

    private final BotTelegram BOT;
    private final Scanner IN;

    public CLI(BotTelegram BOT) {
        this.BOT = BOT;
        this.IN = new Scanner(System.in);
    }

    public void menu() {
        boolean loop = true;
        while (loop) {
            printMenuOPS();
            loop = action();
        }
    }

    private void printMenuOPS() {
        System.out.print(">");
    }

    private boolean action() {
        String op = IN.nextLine();
        switch (op) {
            case "help":
            case "ayuda":
                help();
                break;
            case "exit":
            case "quit":
            case "close":
            case "\\q":
                BOT.stop();
                break;
            case "force_exit":
                System.exit(0);
                break;
            default:
                break;
        }
        return true;
    }

    private void help() {
        System.out.println("*******************");
        System.out.println("**** HELP MENU ****");
        System.out.println("*******************");
        System.out.println("help - Help message");
        System.out.println("exit - Close bot gracefully");
        System.out.println("force_exit - Force exit");
        System.out.println();
    }
}
