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
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author mauri
 */
public class ClientHandler implements Runnable{
    
    private final Socket clientSocket;
    private BufferedReader inDalClient;
    private BufferedWriter outVersoClient;
    private String messaggioRicevuto;
    private String messaggioDaInviare;
    
    public ClientHandler(Socket cs){
        clientSocket = cs;
    }
    
    @Override
    public void run(){
        scrivi("Benvenuto client!");
        chiudi();
    }    
    
    public void scrivi(String messaggioDaInviare){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            outVersoClient = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //Invio del messaggio
            outVersoClient.write(messaggioDaInviare+"\n");
            outVersoClient.flush();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public String leggi(){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            inDalClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //Lettura del messaggio
            messaggioRicevuto = inDalClient.readLine();
            return messaggioRicevuto;
        } catch (IOException ex) {
            System.err.print(ex);
            return "-1";
        }
    }
    
    public void inviaTimeStamp(){
        messaggioRicevuto = leggi();
        switch(messaggioRicevuto){
            case "data": messaggioDaInviare = Long.toString(System.currentTimeMillis());
                         System.out.println("Invio timestamp");
                         break;
            default: messaggioDaInviare = "invalid"; break;
        }            
        scrivi(messaggioDaInviare);
    }
    
    public void chiudi(){
        try {
            //Chiusura della connessione
            clientSocket.close();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
}
