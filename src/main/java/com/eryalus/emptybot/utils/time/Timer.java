/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.utils.time;

/**
 * Timer que se ejecutará cada un tiempo determinado. Contiene métodos para
 * pausar/reaudar, terminar y cambiar el tiempo de espera en la ejecución.
 *
 * @author eryalus
 */
public abstract class Timer extends Thread {

    private Thread hilo = null;
    private boolean bool = true, term = true;
    protected long tiempo = 0;

    /**
     * Crea un contador
     *
     * @param tiempo intervalo de espera entre ejecución y ejecución en
     * milisegundos
     */
    public Timer(long tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Retorna el tiempo de refresco actual en milis
     *
     * @return
     */
    public long getTimeInMillis() {
        return tiempo;
    }

    /**
     * Cambia el tiempo de espera entre ejecución y ejecución
     *
     * @param milisegundos Milisegundos
     */
    public void setTimeMillis(long milisegundos) {
        this.tiempo = milisegundos;
    }

    /**
     * Cambia el tiempo de espera entre ejecución y ejecución
     *
     * @param segundos Segundos
     */
    public void setTimeSeconds(long segundos) {
        setTimeMillis(segundos * 1000);
    }

    /**
     * Cambia el tiempo de espera entre ejecución y ejecución
     *
     * @param minutos Minutos
     */
    public void setTimeMinutes(int minutos) {
        setTimeSeconds(minutos * 60);
    }

    /**
     * Cambia el tiempo de espera entre ejecución y ejecución
     *
     * @param horas Horas
     */
    public void setTimeHours(int horas) {
        setTimeMinutes(horas * 60);
    }

    @Override
    public void start() {
        if (hilo == null) {
            hilo = new Thread(this, "Timer");
            hilo.start();
        }
    }

    /**
     * Termina la ejecución del timer
     */
    public void terminate() {
        term = false;
        bool = false;
    }

    /**
     * Indica si el timer está terminado o no
     *
     * @return true terminado, false no terminado
     */
    public boolean isTerminated() {
        return !term;
    }

    /**
     * Indica si está parado o no
     *
     * @return true parado, false ejecutándose
     */
    public boolean isPaused() {
        return !bool;
    }

    /**
     * Cambia el estado de pausado a ejecutándose y viceversa
     */
    public void play() {
        bool = !bool;
    }

    /**
     * Override para poner la acción que se quiera realizar
     */
    protected abstract void action() ;

    /**
     * Fuerza la ejecución de la accion del temporizador
     */
    public void execute() {
        action();
    }

    @Override
    public synchronized void run() {
        while (term) {
            while (bool) {
                try {
                    action();
                    wait(tiempo);
                } catch (Exception ex) {
                }
            }
            try {
                wait(tiempo);
            } catch (Exception ex) {
            }
        }
    }
}
