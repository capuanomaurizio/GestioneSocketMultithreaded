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
import java.net.SocketTimeoutException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/**
 *
 * @author mauri
 */
public class Server {
    
    private int portaServer;
    private ServerSocket serverSocket;
    private int serverTimeOut;
    private Socket socket;
    private BufferedReader inDalClient;
    private BufferedWriter outVersoClient;
    private String messaggioRicevuto;
    private String messaggioDaInviare;
    
    public Server(int porta, int timeOut){
        try {
            //Apertura della porta di ascolto del server
            portaServer = porta;
            serverTimeOut = timeOut*1000;
            serverSocket = new ServerSocket(portaServer);
            serverSocket.setSoTimeout(serverTimeOut);
            System.out.println("Server avviato correttamente");
        } catch (SocketTimeoutException ex) {
            System.out.println("Il server non accetta più richieste");
            System.err.print(ex);
        } catch (ConnectException ex) {
            System.err.print(ex);
        } catch (SocketException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public void attendi(int clientTimeOut){
        try {
            socket = serverSocket.accept();
            socket.setSoTimeout(clientTimeOut*1000);
            System.out.print("Connessione stabilita: "+socket);
            System.out.println(", setSoTimeout aggiornato");
        } catch (SocketException ex) {
            System.out.println(ex);
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
