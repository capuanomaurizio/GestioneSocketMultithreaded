/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionesocketmultithreaded;

/**
 *
 * @author mauri
 */
public class TimerThread extends Thread{
    
    private int tempo;
    
    public TimerThread(int t){
        tempo = t;
    }
    
    @Override
    public void run(){
        for(int i=0;i<tempo;i++){
            System.out.println(tempo+" secondi rimanenti...");
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.out.print(ex);
            }
        }
    }
}
