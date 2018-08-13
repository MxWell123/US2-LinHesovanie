/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.linearneHesovanie;

import java.io.DataInputStream;

/**
 *
 * @author davidecek
 */
public interface Zaznam {

    public byte[] ToByteArray();

    public void FromByteArray(byte[] paArray, DataInputStream hlpInStream);

    public Object dajTyp();

    public Zaznam kopiruj();

    public int dajVelkostZaznamu();
    
    public void setPlatny(boolean bool);
    
    public boolean getPlatny();

    public void vypisZaznam();
    
    public Object getKluc();

}
