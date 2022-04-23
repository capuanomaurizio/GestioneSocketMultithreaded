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
public class GestioneSocketServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int clientTimeOut = 10;
        Server server = new Server(4444, 20);
        while(true){
            server.attendi(clientTimeOut);
            server.scrivi(String.valueOf(clientTimeOut));
        }
    }
    
}
