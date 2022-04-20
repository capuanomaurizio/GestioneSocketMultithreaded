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
        Server server = new Server(4444);
        server.scrivi("Buongiorno!");
        server.inviaTimeStamp();
        server.chiudi();
    }
    
}
