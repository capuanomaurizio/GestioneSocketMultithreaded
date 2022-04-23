/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionesocketmultithreaded;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;

/**
 *
 * @author mauri
 */
public class Client {
    
    private InetAddress indirizzoServer;
    private int portaServer;
    private Socket socket;
    private BufferedReader tastiera;
    private BufferedReader inDalServer;
    private BufferedWriter outVersoServer;
    private String messaggioRicevuto;
    private String messaggioDaInviare;
    
    public Client(InetAddress indirizzo, int porta){
        try {
            //Richiesta di connessione al server
            indirizzoServer = indirizzo;
            portaServer = porta;
            socket = new Socket(indirizzoServer, portaServer);
        } catch (UnknownHostException ex) {
            System.out.println("Errore, host inesistente");
            System.err.print(ex);
        } catch (IOException ex) {
            System.out.println("Errore nella connessione al server");
            System.err.print(ex);
        }
    }
    
    public void creaTimer(){
        TimerThread timer = new TimerThread(Integer.parseInt(leggi()));
        timer.start();
    }
    
    public void scrivi(){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            outVersoServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Input da tastiera
            messaggioDaInviare = tastiera.readLine();
            //Invio del messaggio
            outVersoServer.write(messaggioDaInviare+"\n");
            outVersoServer.flush();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public void scrivi(String messaggioDaInviare){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            outVersoServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Invio del messaggio
            outVersoServer.write(messaggioDaInviare+"\n");
            outVersoServer.flush();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public String leggi(){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Lettura del messaggio
            messaggioRicevuto = inDalServer.readLine();
            return messaggioRicevuto;
        } catch (IOException ex) {
            System.err.print(ex);
            return "-1";
        }
    }
    
    public void chiediTimeStamp(){
        try {
            //Invio messaggio di richiesta timestamp
            scrivi("data");
            
            //Interpretazione del responso
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            messaggioRicevuto = inDalServer.readLine();
            switch(messaggioRicevuto){
                case "invalid": System.out.println("Richiesta impartita non valida"); break;
                default: Timestamp serverDate = new Timestamp(Long.parseLong(messaggioRicevuto));
                         System.out.println("Data del server: "+serverDate);
                         break;
            }
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public void chiudi(){
        try {
            //Chiusura della connessione
            socket.close();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
}
