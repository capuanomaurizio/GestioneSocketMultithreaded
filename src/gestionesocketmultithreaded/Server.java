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
/**
 *
 * @author mauri
 */
public class Server {
    
    int portaServer;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader inDalClient;
    BufferedWriter outVersoClient;
    String messaggioRicevuto;
    String messaggioDaInviare;
    
    public Server(int porta){
        try {
            //Apertura della porta di ascolto del server
            portaServer = porta;
            serverSocket = new ServerSocket(portaServer);
            System.out.println("Server avviato correttamente");
        } catch (ConnectException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public void attenti(){
        try {
            socket = serverSocket.accept();
            System.out.println("Connessione stabilita");
            System.out.println("Socket creato: "+socket);
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
    
    public void scrivi(String messaggioDaInviare){
        try {
            //Inizializzazione degli oggetti di comunicazione lato server
            outVersoClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
            inDalClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            socket.close();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
}
