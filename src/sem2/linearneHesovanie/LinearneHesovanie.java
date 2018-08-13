/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.linearneHesovanie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidecek
 */
public class LinearneHesovanie {

    private int pocetSkupinBlokov;
    private double dMin;
    private double dMax;
    private int uroven;
    private int pocetZaznamov;
    private int splitPointer;
    private Zapisovac zapisovac;
    private int pocetAlokovanychMiest;
    private int pocetObsadenychMiest;
    private Zaznam zaznam;
    private int pocetZaznamovVPreplnovacomBloku;
    private int aktualnyPocetSkupin;
    private LinkedList<Long> prazdnePreplnovacieBloky;

    public LinearneHesovanie(int pocetSkupinBlokov, double dMin, double dMax, int pocetZaznamov, Zaznam zaznam, int pocetZaznamovVPreplnovacomBloku) {
        this.pocetSkupinBlokov = pocetSkupinBlokov;
        this.aktualnyPocetSkupin = pocetSkupinBlokov;
        this.dMin = dMin;
        this.dMax = dMax;
        this.pocetZaznamov = pocetZaznamov;
        this.uroven = 0;
        this.splitPointer = 0;
        this.pocetZaznamovVPreplnovacomBloku = pocetZaznamovVPreplnovacomBloku;
        zapisovac = new Zapisovac(zaznam.getClass().getSimpleName());
        for (long i = 0; i < pocetSkupinBlokov; i++) {
            Blok blok = new Blok(pocetZaznamov, zaznam);
            zapisovac.ulozBlok(blok, i, zaznam.dajVelkostZaznamu(), false);
        }
        pocetAlokovanychMiest = 2 * pocetZaznamov;
        pocetObsadenychMiest = 0;
        this.zaznam = zaznam;
        this.prazdnePreplnovacieBloky = new LinkedList<>();
    }

    public LinearneHesovanie(int pocetSkupinBlokov, double dMin, double dMax, int uroven, int pocetZaznamov, int splitPointer, int pocetAlokovanychMiest, int pocetObsadenychMiest, int pocetZaznamovVPreplnovacomBloku,
        int aktualnyPocetSkupin, LinkedList<Long> prazdnePreplnovacieBloky, Zaznam zaznam) {
        this.pocetSkupinBlokov = pocetSkupinBlokov;
        this.dMin = dMin;
        this.dMax = dMax;
        this.uroven = uroven;
        this.pocetZaznamov = pocetZaznamov;
        this.splitPointer = splitPointer;
        this.pocetAlokovanychMiest = pocetAlokovanychMiest;
        this.pocetObsadenychMiest = pocetObsadenychMiest;
        this.pocetZaznamovVPreplnovacomBloku = pocetZaznamovVPreplnovacomBloku;
        this.aktualnyPocetSkupin = aktualnyPocetSkupin;
        this.prazdnePreplnovacieBloky = prazdnePreplnovacieBloky; 
        this.zapisovac = new Zapisovac(zaznam.getClass().getSimpleName(), true);      
        this.zaznam = zaznam;
    }

    public LinearneHesovanie() {
    }

    public Zapisovac getZapisovac() {
        return zapisovac;
    }

    public int getPocetSkupinBlokov() {
        return pocetSkupinBlokov;
    }

    public void setPocetSkupinBlokov(int pocetSkupinBlokov) {
        this.pocetSkupinBlokov = pocetSkupinBlokov;
    }

    public double getdMin() {
        return dMin;
    }

    public void setdMin(double dMin) {
        this.dMin = dMin;
    }

    public double getdMax() {
        return dMax;
    }

    public void setdMax(double dMax) {
        this.dMax = dMax;
    }

    public int getUroven() {
        return uroven;
    }

    public void setUroven(int uroven) {
        this.uroven = uroven;
    }

    public int getPocetZaznamov() {
        return pocetZaznamov;
    }

    public void setPocetZaznamov(int pocetZaznamov) {
        this.pocetZaznamov = pocetZaznamov;
    }

    public int getSplitPointer() {
        return splitPointer;
    }

    public void setSplitPointer(int splitPointer) {
        this.splitPointer = splitPointer;
    }

    public int getPocetAlokovanychMiest() {
        return pocetAlokovanychMiest;
    }

    public void setPocetAlokovanychMiest(int pocetAlokovanychMiest) {
        this.pocetAlokovanychMiest = pocetAlokovanychMiest;
    }

    public int getPocetObsadenychMiest() {
        return pocetObsadenychMiest;
    }

    public void setPocetObsadenychMiest(int pocetObsadenychMiest) {
        this.pocetObsadenychMiest = pocetObsadenychMiest;
    }

    public Zaznam getZaznam() {
        return zaznam;
    }

    public void setZaznam(Zaznam zaznam) {
        this.zaznam = zaznam;
    }

    public int getPocetZaznamovVPreplnovacomBloku() {
        return pocetZaznamovVPreplnovacomBloku;
    }

    public void setPocetZaznamovVPreplnovacomBloku(int pocetZaznamovVPreplnovacomBloku) {
        this.pocetZaznamovVPreplnovacomBloku = pocetZaznamovVPreplnovacomBloku;
    }

    public int getAktualnyPocetSkupin() {
        return aktualnyPocetSkupin;
    }

    public void setAktualnyPocetSkupin(int aktualnyPocetSkupin) {
        this.aktualnyPocetSkupin = aktualnyPocetSkupin;
    }

    public String getPrazdnePreplnovacieBloky() {
        String vysledok = "";
        for (Long long1 : prazdnePreplnovacieBloky) {
            vysledok += long1 + ",";
        }
        return vysledok;
    }

    public void setPrazdnePreplnovacieBloky(LinkedList<Long> prazdnePreplnovacieBloky) {
        this.prazdnePreplnovacieBloky = prazdnePreplnovacieBloky;
    }

    private int hashuj(int uroven, Zaznam zaznam) {
        int mod = this.pocetSkupinBlokov * (int) Math.pow(2, this.uroven);
        return ((int) zaznam.getKluc() % mod);
    }

    private int hashuj1(int uroven, Zaznam zaznam) {
        int mod = this.pocetSkupinBlokov * (int) Math.pow(2, this.uroven + 1);
        return ((int) zaznam.getKluc() % mod);
    }

    public LinkedList<NajdenyBlok> najdi(Zaznam zaznam, long adresa) {
        LinkedList<NajdenyBlok> najdeneBloky = new LinkedList<>();
        long index = hashuj(uroven, zaznam);
        if (index < splitPointer) {
            index = hashuj1(uroven, zaznam);
        }
        if (adresa >= 0) {
            index = adresa;
        }
        boolean jeZpreplnovacieho = false;
        Blok blok = new Blok(pocetZaznamov, zaznam.kopiruj());
        blok.FromByteArray(zapisovac.nacitajBlok(pocetZaznamov, zaznam.dajVelkostZaznamu(), index));
        Zaznam pom = blok.najdiZaznam(zaznam);
        if (pom == null || adresa >= 0) {
            najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, false));
        } else {
            return null;
        }
        if (pom == null && blok.getAdresaPreplnovacieho() >= 0) {
            do {
                index = blok.getAdresaPreplnovacieho();
                jeZpreplnovacieho = true;
                blok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam.kopiruj());
                blok.FromByteArray(zapisovac.nacitajPreplnovaciBlok(pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu(), index));
                pom = blok.najdiZaznam(zaznam);
                if (pom == null || adresa >= 0) {
                    najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, false));
                } else {
                    return null;
                }
            } while (blok.getAdresaPreplnovacieho() >= 0);
        }
        for (NajdenyBlok najdenyBlok : najdeneBloky) {
            if ((najdenyBlok.jeZPreplnovacieho == false && najdenyBlok.getBlok().getPocetPlatnych() < pocetZaznamov)
                    || najdenyBlok.jeZPreplnovacieho == true && najdenyBlok.getBlok().getPocetPlatnych() < pocetZaznamovVPreplnovacomBloku) {
                najdenyBlok.setVymazavany(true);
            }
        }
        return najdeneBloky;
    }

    public LinkedList<NajdenyBlok> najdiPreVymaz(Zaznam zaznam, long adresa) {
        LinkedList<NajdenyBlok> najdeneBloky = new LinkedList<>();
        long index = hashuj(uroven, zaznam);
        if (index < splitPointer) {
            index = hashuj1(uroven, zaznam);
        }
        if (adresa >= 0) {
            index = adresa;
        }
        boolean jeZpreplnovacieho = false;
        Blok blok = new Blok(pocetZaznamov, zaznam.kopiruj());
        blok.FromByteArray(zapisovac.nacitajBlok(pocetZaznamov, zaznam.dajVelkostZaznamu(), index));
        Zaznam pom = blok.najdiZaznam(zaznam);
        if (pom != null) {
            najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, true));
        } else {
            najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, false));
        }
        if (blok.getAdresaPreplnovacieho() >= 0) {
            do {
                index = blok.getAdresaPreplnovacieho();
                jeZpreplnovacieho = true;
                blok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam.kopiruj());
                blok.FromByteArray(zapisovac.nacitajPreplnovaciBlok(pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu(), index));
                pom = blok.najdiZaznam(zaznam);
                if (pom != null) {
                    najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, true));
                } else {
                    najdeneBloky.add(new NajdenyBlok(index, blok, jeZpreplnovacieho, false));
                }
            } while (blok.getAdresaPreplnovacieho() >= 0);
        }
        return najdeneBloky;
    }

    public boolean vlozZaznam(Zaznam zaznam) {
        double hustota = 0.0;
        LinkedList<NajdenyBlok> najdeneBloky = najdi(zaznam, -1);
        if (najdeneBloky == null) {
            return false;
        }
        NajdenyBlok vymazavanyBlok = this.najdiVymazavany(najdeneBloky);
        if (vymazavanyBlok != null) {
            vymazavanyBlok.getBlok().vlozZaznam(zaznam);
            zapisovac.ulozBlok(vymazavanyBlok.getBlok(), vymazavanyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), vymazavanyBlok.jeZPreplnovacieho);
        } else {
            Blok blok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam);
            blok.vlozZaznam(zaznam);
            vymazavanyBlok = najdeneBloky.getLast();
            long adresaPreplnovacieho = dajAdresuPreNovyBlok();
            vymazavanyBlok.getBlok().setAdresaPreplnovacieho(adresaPreplnovacieho);
            zapisovac.ulozBlok(vymazavanyBlok.getBlok(), vymazavanyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), vymazavanyBlok.jeZPreplnovacieho);
            zapisovac.ulozBlok(blok, adresaPreplnovacieho, zaznam.dajVelkostZaznamu(), true);
        }
        pocetObsadenychMiest++;
        hustota = (double) pocetObsadenychMiest / (double) pocetAlokovanychMiest;
        if (hustota > dMax) {
            expanzia();
        }
        zapisovac.skratPreplnovaciSubor(prazdnePreplnovacieBloky, pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu());
        return true;
    }

    private void expanzia() {
        ArrayList<Zaznam> listZaznamov = new ArrayList<>();
        Blok povodny = new Blok(pocetZaznamov, zaznam.kopiruj());
        long adresaNoveho = splitPointer + (pocetSkupinBlokov * ((int) Math.pow(2.0, uroven)));
        Blok novyBlok = new Blok(pocetZaznamov, zaznam.kopiruj());
        LinkedList<NajdenyBlok> najdeneBloky = najdi(zaznam, splitPointer);
        povodny = najdeneBloky.poll().getBlok();
        for (int i = 0; i < pocetZaznamov; i++) {
            if (povodny.vratZaznamNaAdrese(i).getPlatny() == true) {
                if (hashuj1(uroven, povodny.vratZaznamNaAdrese(i)) != splitPointer) {
                    novyBlok.vlozZaznam(povodny.vratZaznamNaAdrese(i));
                    povodny.nastavZaznamNaNeplatny(i);
                } else {
                    listZaznamov.add(povodny.vratZaznamNaAdrese(i));
                    povodny.nastavZaznamNaNeplatny(i);
                }
            }
        }
        long adresaPreplnovaciehoPom = povodny.getAdresaPreplnovacieho();
        povodny.setAdresaPreplnovacieho(-1);
        boolean jePreplnovaci = false;
        int pocetPreplnovacich = 0;
        int pozicia = 0;
        if (adresaPreplnovaciehoPom >= 0) {
            do {
                povodny = najdeneBloky.poll().getBlok();
                for (int i = 0; i < pocetZaznamovVPreplnovacomBloku; i++) {
                    if (povodny.vratZaznamNaAdrese(i).getPlatny() == true) {
                        if (hashuj1(uroven, povodny.vratZaznamNaAdrese(i)) != splitPointer) {
                            if (!novyBlok.vlozZaznam(povodny.vratZaznamNaAdrese(i))) { // novyBlok je plny
                                novyBlok.setAdresaPreplnovacieho(dajAdresuPreNovyBlok());
                                zapisovac.ulozBlok(novyBlok, adresaNoveho, zaznam.dajVelkostZaznamu(), jePreplnovaci);
                                adresaNoveho = novyBlok.getAdresaPreplnovacieho();
                                pocetPreplnovacich++;
                                jePreplnovaci = true;
                                novyBlok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam);
                                novyBlok.vlozZaznam(povodny.vratZaznamNaAdrese(i));
                            }
                        } else {
                            listZaznamov.add(povodny.vratZaznamNaAdrese(i));
                        }
                        povodny.nastavZaznamNaNeplatny(i);
                    }
                }
                pocetAlokovanychMiest -= pocetZaznamovVPreplnovacomBloku;
                prazdnePreplnovacieBloky.add(adresaPreplnovaciehoPom);
                adresaPreplnovaciehoPom = povodny.getAdresaPreplnovacieho();
                povodny.setAdresaPreplnovacieho(-1);
            } while (adresaPreplnovaciehoPom >= 0);
        }
        if (jePreplnovaci) {
            pocetAlokovanychMiest += pocetPreplnovacich * pocetZaznamovVPreplnovacomBloku;
        } else {
            pocetAlokovanychMiest += pocetZaznamov;
        }
        zapisovac.ulozBlok(novyBlok, adresaNoveho, zaznam.dajVelkostZaznamu(), jePreplnovaci);

        jePreplnovaci = false;
        povodny = new Blok(pocetZaznamov, zaznam);
        adresaNoveho = splitPointer;
        for (Zaznam zaznam : listZaznamov) {
            if (!povodny.vlozZaznam(zaznam)) {
                povodny.setAdresaPreplnovacieho(dajAdresuPreNovyBlok());
                zapisovac.ulozBlok(povodny, adresaNoveho, zaznam.dajVelkostZaznamu(), jePreplnovaci);
                adresaNoveho = povodny.getAdresaPreplnovacieho();
                jePreplnovaci = true;
                povodny = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam);
                povodny.vlozZaznam(zaznam);
            }
        }
        zapisovac.ulozBlok(povodny, adresaNoveho, zaznam.dajVelkostZaznamu(), jePreplnovaci);
        if (splitPointer + 1 >= (pocetSkupinBlokov * (int) Math.pow(2.0, uroven))) {
            splitPointer = 0;
            uroven = uroven + 1;

        } else {
            splitPointer = splitPointer + 1;
        }
        aktualnyPocetSkupin++;
        double hustota = (double) pocetObsadenychMiest / (double) pocetAlokovanychMiest;
        if (hustota > dMax) {
            expanzia();
        }

    }

    public NajdenyBlok najdiVymazavany(LinkedList<NajdenyBlok> najdeneBloky) {
        for (NajdenyBlok najdenyBlok : najdeneBloky) {
            if (najdenyBlok.isVymazavany()) {
                return najdenyBlok;
            }
        }
        return null;
    }

    public boolean vymazZaznam(Zaznam zaznam) {
        double hustota = 0.0;
        LinkedList<NajdenyBlok> najdeneBloky = new LinkedList<>();
        najdeneBloky = najdiPreVymaz(zaznam, -1);
        NajdenyBlok vymazavanyBlok = najdiVymazavany(najdeneBloky);
        if (vymazavanyBlok == null) {
            return false;
        }
        int pocetVolnychMiest = 0;

        if (vymazavanyBlok.getBlok().vymazZaznam(zaznam)) { //ak vymazanie uspesne
            for (NajdenyBlok najdenyBlok : najdeneBloky) {
                if (!najdenyBlok.isJeZPreplnovacieho()) {
                    pocetVolnychMiest += pocetZaznamov - najdenyBlok.getBlok().getPocetPlatnych();
                } else {
                    pocetVolnychMiest += pocetZaznamovVPreplnovacomBloku - najdenyBlok.getBlok().getPocetPlatnych();
                }
            }
            if (pocetVolnychMiest >= pocetZaznamovVPreplnovacomBloku && najdeneBloky.size() > 1) {
                this.preusporiadajBloky(najdeneBloky);
                return true;
            } else if (vymazavanyBlok.getBlok().getPocetPlatnych() == 0 && vymazavanyBlok.jeZPreplnovacieho) {
                NajdenyBlok predch = najdiPredchadzajuciBlok(vymazavanyBlok, najdeneBloky);
                predch.getBlok().setAdresaPreplnovacieho(-1);
                prazdnePreplnovacieBloky.add(vymazavanyBlok.getAdresa());
                pocetAlokovanychMiest -= pocetZaznamovVPreplnovacomBloku;
                zapisovac.ulozBlok(predch.getBlok(), predch.getAdresa(), zaznam.dajVelkostZaznamu(), predch.jeZPreplnovacieho);
            }
            pocetObsadenychMiest--;
            zapisovac.ulozBlok(vymazavanyBlok.getBlok(), vymazavanyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), vymazavanyBlok.jeZPreplnovacieho);
        }
        hustota = (double) pocetObsadenychMiest / (double) pocetAlokovanychMiest;
        if (hustota < dMin && aktualnyPocetSkupin > pocetSkupinBlokov) {
            spojenie();
        }
        zapisovac.skratPreplnovaciSubor(prazdnePreplnovacieBloky, pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu());
        return true;
    }

    public String sekvencnyVypis() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aktualnyPocetSkupin; i++) {

            Blok blok = new Blok(pocetZaznamov, zaznam.kopiruj());
            blok.FromByteArray(zapisovac.nacitajBlok(pocetZaznamov, zaznam.dajVelkostZaznamu(), i));
            sb.append("Cislo Bloku: " + i + "\n");
            blok.vypisBlok(sb);
            sb.append("\n");
            if (blok.getAdresaPreplnovacieho() < 0) {
            } else {
                sb.append("Cislo Preplnovacieho Bloku: " + blok.getAdresaPreplnovacieho() + "\n");
                sb.append("\t");
                long adresa = blok.getAdresaPreplnovacieho();
                do {
                    Blok blok2 = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam.kopiruj());
                    blok2.FromByteArray(zapisovac.nacitajPreplnovaciBlok(pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu(), adresa));
                    blok2.vypisBlok(sb);
                    adresa = blok2.getAdresaPreplnovacieho();
                } while (adresa > 0);
            }
        }
        return sb.toString();
    }

    public String vypisSubor() {
        StringBuilder sb = new StringBuilder();
        long velkostBloku = (pocetZaznamov * (zaznam.dajVelkostZaznamu())) + 12;
        try {
            for (long i = 0; i < (zapisovac.rdf.length()) / velkostBloku; i++) {
                Blok blok = new Blok(pocetZaznamov, zaznam.kopiruj());
                blok.FromByteArray(zapisovac.nacitajBlok(pocetZaznamov, zaznam.dajVelkostZaznamu(), i));
                sb.append("BLOK: " + i + " Pocet platnych zaznamov: " + blok.getPocetPlatnych() + "\n");
                blok.vypisBlok(sb);
                sb.append("\n");
                sb.append("PREPLNOVACI: " + blok.getAdresaPreplnovacieho() + "\n");
                sb.append("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(LinearneHesovanie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    public String vypisPreplnovaciSubor() {
        StringBuilder sb = new StringBuilder();
        long velkostBloku = (pocetZaznamovVPreplnovacomBloku * (zaznam.dajVelkostZaznamu())) + 12;
        try {
            long velkostSuboru = zapisovac.rdfPrep.length();
            for (long i = 0; i < velkostSuboru / velkostBloku; i++) {
                Blok blok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam.kopiruj());
                blok.FromByteArray(zapisovac.nacitajPreplnovaciBlok(pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu(), i));
                sb.append("BLOK: " + i + " Pocet platnych zaznamov :" + blok.getPocetPlatnych() + "\n");
                blok.vypisBlok(sb);
                sb.append("\n");
                sb.append("PREPLNOVACI: " + blok.getAdresaPreplnovacieho()+ "\n");
                sb.append("------------------------------------------------------------------------\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(LinearneHesovanie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    private long dajAdresuPreNovyBlok() {
        pocetAlokovanychMiest += pocetZaznamovVPreplnovacomBloku;
        if (prazdnePreplnovacieBloky.isEmpty()) {
            return zapisovac.dajVelkostSuboru(zaznam.getClass().getSimpleName()) / ((pocetZaznamovVPreplnovacomBloku * zaznam.dajVelkostZaznamu()) + 12);
        }
        return prazdnePreplnovacieBloky.poll();
    }

    private void preusporiadajBloky(LinkedList<NajdenyBlok> najdeneBloky) {
        LinkedList<Zaznam> pom = new LinkedList<>();
        long[] adresy = new long[najdeneBloky.size()];
        int pozicia = 1;

        for (NajdenyBlok najdenyBlok : najdeneBloky) {
            if (!najdenyBlok.isJeZPreplnovacieho()) {
                adresy[0] = najdenyBlok.getAdresa();
            } else {
                adresy[pozicia] = najdenyBlok.getAdresa();
                pozicia++;
            }
            for (int i = 0; i < najdenyBlok.getBlok().getPocetPlatnych(); i++) {
                pom.add(najdenyBlok.getBlok().vratZaznamNaAdrese(i));
            }
        }
        if (pom.size() == 0) {
            return;
        }
        Blok povodny = new Blok(pocetZaznamov, zaznam.kopiruj());
        for (int i = 0; i < pocetZaznamov; i++) { // zakladny blok
            if (pom != null) {
                povodny.vlozIZaznam(pom.poll());
            }
        }
        boolean jePreplnovaci = false;
        for (int i = 0; i < adresy.length - 1; i++) { // preplnovaci blok
            Blok blok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam.kopiruj());
            for (int j = 0; j < pocetZaznamovVPreplnovacomBloku; j++) {
                if (pom.size() != 0) {
                    blok.vlozIZaznam(pom.poll());
                }
            }
            if (blok.vratZaznamNaAdrese(0).getPlatny() == false) {
                povodny.setAdresaPreplnovacieho(-1);
                zapisovac.ulozBlok(povodny, adresy[i], zaznam.dajVelkostZaznamu(), jePreplnovaci);
            } else {
                povodny.setAdresaPreplnovacieho(adresy[i + 1]);
                zapisovac.ulozBlok(povodny, adresy[i], zaznam.dajVelkostZaznamu(), jePreplnovaci);
                povodny = blok;
            }
            jePreplnovaci = true;
        }

        prazdnePreplnovacieBloky.add(adresy[adresy.length - 1]);
        double hustota = 0.0;
        hustota = (double) pocetObsadenychMiest / (double) pocetAlokovanychMiest;
        if (hustota < dMin && aktualnyPocetSkupin > pocetSkupinBlokov) {
            spojenie();
        }
        zapisovac.skratPreplnovaciSubor(prazdnePreplnovacieBloky, pocetZaznamovVPreplnovacomBloku, zaznam.dajVelkostZaznamu());
    }

    private void spojenie() {
        if (splitPointer > 0) {
            long adresaPoslednej = splitPointer + (pocetSkupinBlokov * (int) Math.pow(2.0, uroven) - 1);
            long adresaNoveho = splitPointer - 1;
            LinkedList<NajdenyBlok> najdeneBloky = najdiPreVymaz(zaznam, adresaPoslednej);
            presunZaznamy(najdeneBloky, adresaNoveho);
            zapisovac.skratHlavnySubor(adresaPoslednej, najdeneBloky.getFirst().getBlok().getPocetZaznamov(), zaznam.dajVelkostZaznamu());
            aktualnyPocetSkupin--;
            splitPointer = (int) adresaNoveho;
        } else if (splitPointer == 0 && uroven > 0) {
            long adresaPoslednej = (pocetSkupinBlokov * (int) Math.pow(2.0, uroven)) - 1;
            long adresaNoveho = (pocetSkupinBlokov * (int) Math.pow(2.0, uroven - 1)) - 1;
            LinkedList<NajdenyBlok> najdeneBloky = najdiPreVymaz(zaznam, adresaPoslednej);
            presunZaznamy(najdeneBloky, adresaNoveho);
            zapisovac.skratHlavnySubor(adresaPoslednej, najdeneBloky.getFirst().getBlok().getPocetZaznamov(), zaznam.dajVelkostZaznamu());
            aktualnyPocetSkupin--;
            splitPointer = (int) adresaNoveho;
            uroven--;
        }
        double hustota = 0.0;
        hustota = (double) pocetObsadenychMiest / (double) pocetAlokovanychMiest;
        if (hustota < dMin && aktualnyPocetSkupin > pocetSkupinBlokov) {
            spojenie();
        }

    }

    private void presunZaznamy(LinkedList<NajdenyBlok> najdeneBloky, long adresa) {
        LinkedList<NajdenyBlok> najdeneBlokyPovodne = najdiPreVymaz(zaznam, adresa);
        LinkedList<Zaznam> zaznamy = new LinkedList<>();
        for (NajdenyBlok najdenyBlok : najdeneBloky) {
            for (int i = 0; i < najdenyBlok.getBlok().getPocetZaznamov(); i++) {
                Zaznam zaznam = najdenyBlok.getBlok().vratZaznamNaAdrese(i);
                if (zaznam.getPlatny() == true) {
                    zaznamy.add(zaznam);
                    najdenyBlok.getBlok().setZaznamNaPozicii(i, false);
                    zaznam.setPlatny(false);
                }
            }
            if (najdenyBlok.jeZPreplnovacieho) {
                pocetAlokovanychMiest = pocetAlokovanychMiest - pocetZaznamovVPreplnovacomBloku;
                zapisovac.ulozBlok(najdenyBlok.getBlok(), najdenyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), true);
                prazdnePreplnovacieBloky.add(najdenyBlok.getAdresa());
            } else {
                pocetAlokovanychMiest = pocetAlokovanychMiest - pocetZaznamov;
            }
        }
        for (NajdenyBlok najdenyBlokPovodny : najdeneBlokyPovodne) {
            int pocetVolnychVBloku = najdenyBlokPovodny.getBlok().getPocetZaznamov() - najdenyBlokPovodny.getBlok().getPocetPlatnych();
            for (int i = 0; i < pocetVolnychVBloku; i++) {
                if (!zaznamy.isEmpty()) {
                    najdenyBlokPovodny.getBlok().vlozIZaznam(zaznamy.poll());
                }
            }
            zapisovac.ulozBlok(najdenyBlokPovodny.getBlok(), najdenyBlokPovodny.getAdresa(), zaznam.dajVelkostZaznamu(), najdenyBlokPovodny.jeZPreplnovacieho);
        }
        NajdenyBlok poslednyBlok = najdeneBlokyPovodne.getLast();
        while (!zaznamy.isEmpty()) {
            Blok novyBlok = new Blok(pocetZaznamovVPreplnovacomBloku, zaznam);
            long adresaPrepl = this.dajAdresuPreNovyBlok();
            poslednyBlok.getBlok().setAdresaPreplnovacieho(adresaPrepl);
            for (int i = 0; i < pocetZaznamovVPreplnovacomBloku; i++) {
                if (!zaznamy.isEmpty()) {
                    novyBlok.vlozIZaznam(zaznamy.poll());
                }
            }
            zapisovac.ulozBlok(poslednyBlok.getBlok(), poslednyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), poslednyBlok.jeZPreplnovacieho);
            poslednyBlok = new NajdenyBlok(adresaPrepl, novyBlok, true, false);
        }
        zapisovac.ulozBlok(poslednyBlok.getBlok(), poslednyBlok.getAdresa(), zaznam.dajVelkostZaznamu(), poslednyBlok.jeZPreplnovacieho);
    }

    private NajdenyBlok najdiPredchadzajuciBlok(NajdenyBlok vymazavany, LinkedList<NajdenyBlok> najdeneBloky) {
        for (NajdenyBlok najdenyBlok : najdeneBloky) {
            if (najdenyBlok.getBlok().getAdresaPreplnovacieho() == vymazavany.getAdresa()) {
                return najdenyBlok;
            }
        }
        return null;
    }

    public void ulozBlok(Blok blok, long adresa, int dajVelkostZaznamu, boolean jeZPreplnovacieho) {
        zapisovac.ulozBlok(blok, adresa, dajVelkostZaznamu, jeZPreplnovacieho);
    }

    public void ulozHesovanie() {
        zapisovac.ulozLinHesovanie(this);
    }

    public class NajdenyBlok {

        private long adresa;
        private Blok blok;
        private boolean jeZPreplnovacieho;
        private boolean vymazavany;

        public NajdenyBlok(long adresa, Blok blok, boolean jeZPreplnovacieho, boolean vymazavany) {
            this.adresa = adresa;
            this.blok = blok;
            this.jeZPreplnovacieho = jeZPreplnovacieho;
            this.vymazavany = vymazavany;
        }

        public void setVymazavany(boolean vymazavany) {
            this.vymazavany = vymazavany;
        }

        public boolean isVymazavany() {
            return vymazavany;
        }

        public long getAdresa() {
            return adresa;
        }

        public Blok getBlok() {
            return blok;
        }

        public boolean isJeZPreplnovacieho() {
            return jeZPreplnovacieho;
        }

    }

}
