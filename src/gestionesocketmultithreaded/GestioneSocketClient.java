/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionesocketmultithreaded;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author mauri
 */
public class GestioneSocketClient {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 4444);
            //client.creaTimer();
            System.out.print(client.leggi());
        } catch (UnknownHostException ex) {
            System.err.print(ex);
        }
    }
    
}
