/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.linearneHesovanie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidecek
 */
public class Blok {

    private Zaznam[] zaznamy;
    private int pocetZaznamov;
    private int pocetPlatnych;
    private Zaznam Izaznam;
    private long adresaPreplnovacieho;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";

    public Blok(int pocetZaznamov, Zaznam Izaznam) {
        this.pocetZaznamov = pocetZaznamov;
        this.pocetPlatnych = 0;
        this.zaznamy = new Zaznam[pocetZaznamov];
        this.Izaznam = Izaznam;
        this.adresaPreplnovacieho = -1;
        for (int i = 0; i < pocetZaznamov; i++) {
            zaznamy[i] = Izaznam.kopiruj();
        }
    }

    public int getPocetZaznamov() {
        return pocetZaznamov;
    }

    public void setPocetZaznamov(int pocetZaznamov) {
        this.pocetZaznamov = pocetZaznamov;
    }

    public void setAdresaPreplnovacieho(long adresaPreplnovacieho) {
        this.adresaPreplnovacieho = adresaPreplnovacieho;
    }

    public int getPocetPlatnych() {
        return pocetPlatnych;
    }

    public void setPocetPlatnych(int pocetPlatnych) {
        this.pocetPlatnych = pocetPlatnych;
    }

//    public boolean vlozZaznam(Zaznam Izaznam, long skupina, boolean preplnovaci, LinkedList<Long> volneBloky) {
//        boolean pom = true;
//        Zapisovac zapisovac = new Zapisovac();
//        if (pocetPlatnych < pocetZaznamov) {
//            Izaznam.setPlatny(true);
//            zaznamy[pocetPlatnych] = Izaznam;
//            pocetPlatnych++;
//            zapisovac.ulozBlok(this, skupina, pocetZaznamov, Izaznam.dajVelkostZaznamu(), Izaznam.getClass().getSimpleName(), preplnovaci);
//        } else {
//            if (adresaPreplnovacieho < 0) {
//                long pomocna = 0;
//                if (volneBloky.isEmpty()) {
//                    pomocna = (zapisovac.dajVelkostSuboru(Izaznam.getClass().getSimpleName())) / velkostZaznamu;
//                } else {
//                    pomocna = volneBloky.poll();
//                }
//                this.setAdresaPreplnovacieho(pomocna);
//                zapisovac.ulozBlok(this, skupina, pocetZaznamov, velkostZaznamu, Izaznam.getClass().getSimpleName(), false);
//                Blok blok = new Blok(pocetZaznamovVPreplnovacomBloku, Izaznam.kopiruj(), pocetZaznamovVPreplnovacomBloku);
//                blok.vlozZaznam(Izaznam, adresaPreplnovacieho, true, volneBloky);
//                pom = false;
//            } else {
//                long adresaPreplnovaciehoPom = adresaPreplnovacieho;
//                long pomocnaAdresa = 0;
//                Blok blok = null;
//                do {
//                    blok = new Blok(pocetZaznamovVPreplnovacomBloku, Izaznam.kopiruj(), pocetZaznamovVPreplnovacomBloku);
//                    blok.FromByteArray(zapisovac.nacitajPreplnovaciBlok(pocetZaznamovVPreplnovacomBloku, Izaznam.dajVelkostZaznamu(), adresaPreplnovaciehoPom, Izaznam.getClass().getSimpleName()));
//                    if (blok.getPocetPlatnych() < blok.getPocetZaznamov()) {
//                        Izaznam.setPlatny(true);
//                        blok.vlozIZaznam(Izaznam);                        
//                        zapisovac.ulozBlok(blok, skupina, pocetZaznamovVPreplnovacomBloku, Izaznam.dajVelkostZaznamu(), Izaznam.getClass().getSimpleName(), true);
//                        break;
//                    }
//                    pomocnaAdresa = adresaPreplnovaciehoPom;
//                    adresaPreplnovaciehoPom = blok.getAdresaPreplnovacieho();
//                } while (blok.getAdresaPreplnovacieho() >= 0);
//                long pomocna = 0;
//                if (volneBloky.isEmpty()) {
//                    pomocna = (zapisovac.dajVelkostSuboru(Izaznam.getClass().getSimpleName())) / velkostZaznamu;
//                } else {
//                    pomocna = volneBloky.poll();
//                }
//                blok.setAdresaPreplnovacieho(pomocna);
//                zapisovac.ulozBlok(blok, pomocnaAdresa, pocetZaznamovVPreplnovacomBloku, velkostZaznamu, Izaznam.getClass().getSimpleName(), true);
//                blok = new Blok(pocetZaznamovVPreplnovacomBloku, Izaznam.kopiruj(), pocetZaznamovVPreplnovacomBloku);
//                Izaznam.setPlatny(true);
//                blok.vlozIZaznam(Izaznam);
//                zapisovac.ulozBlok(blok, pomocna, pocetZaznamovVPreplnovacomBloku, Izaznam.dajVelkostZaznamu(), Izaznam.getClass().getSimpleName(), true);
//                pom = false;
//            }
//        }
//        return pom;
//    }
    public boolean vlozZaznam(Zaznam Izaznam) {
        if (this.pocetPlatnych < this.pocetZaznamov) {
            vlozIZaznam(Izaznam);
            return true;
        }
        return false;
    }

    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            hlpOutStream.writeInt(pocetPlatnych);
            for (int i = 0; i < pocetZaznamov; i++) {
                hlpOutStream.write(zaznamy[i].ToByteArray());
            }
            hlpOutStream.writeLong(adresaPreplnovacieho);
            return hlpByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }

    public void FromByteArray(byte[] paArray) {
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray);
        DataInputStream hlpInStream = new DataInputStream(hlpByteArrayInputStream);
        try {
            this.pocetPlatnych = hlpInStream.readInt();
            for (int i = 0; i < pocetZaznamov; i++) {
                Izaznam.FromByteArray(paArray, hlpInStream);
                Zaznam z = Izaznam.kopiruj();
                zaznamy[i] = z;
            }
            this.adresaPreplnovacieho = hlpInStream.readLong();

        } catch (IOException ex) {
            Logger.getLogger(Blok.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public StringBuilder vypisBlok(StringBuilder sb) {
        for (Zaznam zaznam : zaznamy) {
            if (zaznam.getPlatny()) {
                sb.append(zaznam.toString());
            } 
        }
        return sb;
    }

    public Zaznam najdiZaznam(Zaznam zaznam) {
        for (int i = 0; i < pocetZaznamov; i++) {
            if (zaznamy[i].getPlatny() != false) {
                int pom1 = (int) zaznamy[i].getKluc();
                int pom2 = (int) zaznam.getKluc();
                if (pom1 == pom2) {
                    return zaznamy[i];
                }
            }
        }
        return null;
    }

    public Zaznam vratZaznamNaAdrese(int adresa) {
        return zaznamy[adresa].kopiruj();
    }

    public void nastavZaznamNaNeplatny(int adresa) {
        zaznamy[adresa].setPlatny(false);
        pocetPlatnych--;
    }

    public long getAdresaPreplnovacieho() {
        return adresaPreplnovacieho;
    }

    public void reorganizuj() {
        Zaznam[] pom = new Zaznam[pocetZaznamov];
        Zaznam[] pom2 = new Zaznam[pocetZaznamov];
        int pozicia = 0;
        int pozicia1 = 0;
        for (int i = 0; i < pocetZaznamov; i++) {
            if (zaznamy[i].getPlatny()) {
                pom[pozicia] = zaznamy[i].kopiruj();
                pozicia++;
            } else {
                pom2[pozicia1] = zaznamy[i].kopiruj();
                pozicia1++;
            }
        }
        for (int i = 0; i < pozicia; i++) {
            zaznamy[i] = pom[i];
        }
        for (int i = 0; i < pozicia1; i++) {
            zaznamy[pozicia + i] = pom2[i];
        }
    }

    public void vlozIZaznam(Zaznam Izaznam) {
        Izaznam.setPlatny(true);
        zaznamy[pocetPlatnych] = Izaznam;
        pocetPlatnych++;
    }

    public boolean vymazZaznam(Zaznam zaznam) {
        Zaznam zaznamPom = this.najdiZaznam(zaznam);
        if (zaznamPom != null) {
            zaznamPom.setPlatny(false);
            pocetPlatnych--;
            reorganizuj();
            return true;
        }
        return false;
    }

    public void setZaznamNaPozicii(int i, boolean b) {
        zaznamy[i].setPlatny(b);
    }

}
