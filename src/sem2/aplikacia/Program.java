/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.aplikacia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import sem2.linearneHesovanie.LinearneHesovanie;
import sem2.linearneHesovanie.LinearneHesovanie.NajdenyBlok;
import sem2.linearneHesovanie.Zapisovac;
import sem2.linearneHesovanie.Zaznam;
import sem2.zdravotnaKarta.Hospitalizacia;
import sem2.zdravotnaKarta.Pacient;

/**
 *
 * @author davidecek
 */
public class Program {

    LinearneHesovanie lin = new LinearneHesovanie();

    public Program() {

    }

    public Zaznam vyhladajPacienta(int cisloPreukazu) {
        Pacient pacient = new Pacient(cisloPreukazu);
        LinkedList<NajdenyBlok> najdeneBloky = lin.najdiPreVymaz(pacient, -1);
        NajdenyBlok vysledny = lin.najdiVymazavany(najdeneBloky);
        if (vysledny == null) {
            return null;
        }
        Pacient vysledok = (Pacient) vysledny.getBlok().najdiZaznam(pacient);
        return vysledok;
    }

    public void vytvorLinearnehesovanie(int pocetSkupin, double dMin, double dMax, int pocetZaznamov, Pacient pacient, int pocetZaznamovVPrepl) {
        lin = new LinearneHesovanie(pocetSkupin, dMin, dMax, pocetZaznamov, pacient, pocetZaznamovVPrepl);
    }

    public void vytvorLinearnehesovanie(int pocetSkupin, double dMin, double dMax, int uroven, int pocetZaznamov, int splitPointer, int pocetAlokovanychMiest, int pocetObsadenychMiest, int pocetZaznamovVPrepl,
            int aktualnyPocetSkupin, LinkedList<Long> prazdneBloky, Zaznam zaznam) {
        lin = new LinearneHesovanie(pocetSkupin, dMin, dMax, uroven, pocetZaznamov, splitPointer, pocetAlokovanychMiest, pocetObsadenychMiest, pocetZaznamovVPrepl, aktualnyPocetSkupin, prazdneBloky, zaznam);
    }

    public void pridajPacienta(String meno, String priezvisko, int cisloPreukazu, LocalDate datumNarodenia) {
        Pacient pacient = new Pacient(meno, priezvisko, cisloPreukazu, datumNarodenia);
        lin.vlozZaznam(pacient);
    }

    public void zacniPacientoviHospitalizaciu(int cisloPreukazu, LocalDate datumZaciatku, String diagnoza) {
        Pacient pacient = new Pacient(cisloPreukazu);
        LinkedList<NajdenyBlok> najdeneBloky = lin.najdiPreVymaz(pacient, -1);
        NajdenyBlok vysledny = lin.najdiVymazavany(najdeneBloky);
        Pacient vysledok = (Pacient) vysledny.getBlok().najdiZaznam(pacient);
        vysledok.pridajHospitalizaciu(datumZaciatku, diagnoza);
        lin.ulozBlok(vysledny.getBlok(), vysledny.getAdresa(), pacient.dajVelkostZaznamu(), vysledny.isJeZPreplnovacieho());
    }

    public void ukonciPacientoviHospitalizaciu(int cisloPreukazu, LocalDate datumKonca) {
        Pacient pacient = new Pacient(cisloPreukazu);
        LinkedList<NajdenyBlok> najdeneBloky = lin.najdiPreVymaz(pacient, -1);
        NajdenyBlok vysledny = lin.najdiVymazavany(najdeneBloky);
        Pacient vysledok = (Pacient) vysledny.getBlok().najdiZaznam(pacient);
        vysledok.ukonciHospitalizaciu(datumKonca);
        lin.ulozBlok(vysledny.getBlok(), vysledny.getAdresa(), pacient.dajVelkostZaznamu(), vysledny.isJeZPreplnovacieho());
    }

    public void editujPacienta(int cisloPreukazu, int noveCisloPreukazu, String noveMeno, String priezvisko, LocalDate novyDatumNar) {
        Pacient pacient = new Pacient(cisloPreukazu);
        LinkedList<NajdenyBlok> najdeneBloky = lin.najdiPreVymaz(pacient, -1);
        NajdenyBlok vysledny = lin.najdiVymazavany(najdeneBloky);
        Pacient vysledok = (Pacient) vysledny.getBlok().najdiZaznam(pacient);
        Pacient novyPacient = vysledok.kopiruj();
        lin.vymazZaznam(vysledok);
        if (!priezvisko.equals("")) {
            novyPacient.setPriezvisko(priezvisko);
        }
        if (!noveMeno.equals("")) {
            novyPacient.setKrstneMeno(noveMeno);
        }
        if (noveCisloPreukazu >= 0) {
            novyPacient.setCisloPreukazu(noveCisloPreukazu);
        }
        if (novyDatumNar != null) {
            novyPacient.setDatum_narodenia(novyDatumNar);
        }
        lin.vlozZaznam(novyPacient);

    }

    public void vymazZaznam(int cisloPreukazu) {
        Pacient pacient = new Pacient(cisloPreukazu);
        lin.vymazZaznam(pacient);
    }

    public String sekvencnyVypis() {
        return lin.sekvencnyVypis();
    }

    public String vypisHlavnySubor() {
        return lin.vypisSubor();
    }

    public String vypisPreplnovaciSubor() {
        return lin.vypisPreplnovaciSubor();
    }

    public void ulozLinearneHesovanie() {
        lin.ulozHesovanie();
    }

    public void nacitajHesovanie() {
        LinkedList<Long> prazdneBloky = new LinkedList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("LinHesovanie/LinHesovanie.txt"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
                        String strLine = br.readLine();
                        String[] split;
                        split = strLine.split(",");
                        int pocetSkupBlokov = Integer.parseInt(split[0]);
                        double dMin = Double.parseDouble(split[1]);
                        double dMax = Double.parseDouble(split[2]);
                        int uroven = Integer.parseInt(split[3]);
                        int pocetZaznamov = Integer.parseInt(split[4]);
                        int splitPointer = Integer.parseInt(split[5]);
                        int pocetAlokovanychMiest = Integer.parseInt(split[6]);
                        int pocetObsadenychMiest = Integer.parseInt(split[7]);
                        int pocetZaznamovVPrepl = Integer.parseInt(split[8]);
                        int aktPocetSkup = Integer.parseInt(split[9]);
                        for (int i = 10; i < split.length; i++) {
                            prazdneBloky.add(Long.parseLong(split[i]));
                        }
                        br.close();
                        Pacient pacient = new Pacient();
                        this.vytvorLinearnehesovanie(pocetSkupBlokov, dMin, dMax, uroven, pocetZaznamov, splitPointer, pocetAlokovanychMiest, pocetObsadenychMiest, pocetZaznamovVPrepl, aktPocetSkup, prazdneBloky, pacient);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Zapisovac.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(Zapisovac.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
