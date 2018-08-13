/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.zdravotnaKarta;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import sem2.linearneHesovanie.Zapisovac;
import sem2.linearneHesovanie.Zaznam;

/**
 *
 * @author davidecek
 */
public class Pacient implements Zaznam {

    private boolean platnyZaznam;
    private String krstneMeno;
    private String priezvisko;
    private int cisloPreukazu;
    private LocalDate datum_narodenia;
    private ArrayList<Hospitalizacia> hospitalizacie;

    public Pacient(String krstneMeno, String priezvisko, int cisloPreukazu, LocalDate datum_narodenia) {
        this.platnyZaznam = false;
        this.krstneMeno = krstneMeno;
        this.priezvisko = priezvisko;
        this.cisloPreukazu = cisloPreukazu;
        this.datum_narodenia = datum_narodenia;
        this.hospitalizacie = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            hospitalizacie.add(new Hospitalizacia(LocalDate.now(), LocalDate.MIN, ""));
        }
    }

    public Pacient(int cisloPreukazu) {
        this.platnyZaznam = false;
        this.krstneMeno = "";
        this.priezvisko = "";
        this.cisloPreukazu = cisloPreukazu;
        this.datum_narodenia = LocalDate.now();
        this.hospitalizacie = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            hospitalizacie.add(new Hospitalizacia(LocalDate.now(), LocalDate.MIN, ""));
        }
    }

    public Pacient(String krstneMeno, String priezvisko, int cisloPreukazu, LocalDate datum_narodenia, ArrayList<Hospitalizacia> hospitalizacie) {
        this.platnyZaznam = false;
        this.krstneMeno = krstneMeno;
        this.priezvisko = priezvisko;
        this.cisloPreukazu = cisloPreukazu;
        this.datum_narodenia = datum_narodenia;
        this.hospitalizacie = hospitalizacie;
    }

    public ArrayList<Hospitalizacia> getHospitalizacie() {
        return hospitalizacie;
    }

    public Pacient() {
        this.platnyZaznam = false;
        this.krstneMeno = "";
        this.priezvisko = "";
        this.cisloPreukazu = -1;
        this.datum_narodenia = LocalDate.now();
        this.hospitalizacie = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            hospitalizacie.add(new Hospitalizacia(LocalDate.now(), LocalDate.MIN, ""));
        }

    }

    public Pacient(String krstneMeno, String priezvisko, int cisloPreukazu, LocalDate datum_narodenia, boolean platnyZaznam, ArrayList<Hospitalizacia> hosp) {
        this.platnyZaznam = platnyZaznam;
        this.krstneMeno = krstneMeno;
        this.priezvisko = priezvisko;
        this.cisloPreukazu = cisloPreukazu;
        this.datum_narodenia = datum_narodenia;
        this.hospitalizacie = new ArrayList<>();
        for (Hospitalizacia hospa : hosp) {
            hospitalizacie.add(hospa.kopiruj());
        }
    }

    public boolean isPlatnyZaznam() {
        return platnyZaznam;
    }

    @Override
    public void setPlatny(boolean bool) {
        this.platnyZaznam = bool;
    }

    public String getKrstneMeno() {
        return krstneMeno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public int getCisloPreukazu() {
        return cisloPreukazu;
    }

    public LocalDate getDatum_narodenia() {
        return datum_narodenia;
    }

    public int dajVelkostZaznamu() {
        return 1 + (2 * 25) + (2 * 25) + 4 + 8 + (100 * (1 + 8 + 8 + (40 * 2)));
    }

    public Pacient kopiruj() {
        Pacient pac = new Pacient(krstneMeno, priezvisko, cisloPreukazu, datum_narodenia, platnyZaznam, hospitalizacie);
        return pac;
    }

    public void pridajHospitalizaciu(LocalDate datumZaciatku, String diagnoza) {
        for (int i = 0; i < 100; i++) {
            if (!hospitalizacie.get(i).isJePlatna()) {
                Hospitalizacia hosp = new Hospitalizacia(datumZaciatku, LocalDate.MIN, diagnoza);
                hosp.setJePlatna(true);
                hospitalizacie.set(i, hosp);
                break;
            }

        }
//        for (Hospitalizacia hospitalizacia : hospitalizacie) {
//            if (!hospitalizacia.isJePlatna()) {
//                hospitalizacia = new Hospitalizacia(datumZaciatku, LocalDate.MIN, diagnoza);
//                hospitalizacia.setJePlatna(true);
//                break;
//            }
//        }
    }

    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            hlpOutStream.writeBoolean(platnyZaznam);
            hlpOutStream.writeChars(krstneMeno.trim());
            for (int i = 0; i < (25 - krstneMeno.length()) * 2; i++) {
                hlpOutStream.writeByte(0);
            }
            hlpOutStream.writeChars(priezvisko.trim());
            for (int i = 0; i < (25 - priezvisko.length()) * 2; i++) {
                hlpOutStream.writeByte(0);
            }
            hlpOutStream.writeInt(cisloPreukazu);
            hlpOutStream.writeLong(datum_narodenia.toEpochDay());
            for (int i = 0; i < hospitalizacie.size(); i++) {
                hlpOutStream.write(hospitalizacie.get(i).ToByteArray());
            }

            return hlpByteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }

    @Override
    public void FromByteArray(byte[] DataInputStream, DataInputStream hlpInStream) {
        StringBuilder sb = new StringBuilder();

        try {
            this.platnyZaznam = hlpInStream.readBoolean();
            for (int i = 0; i < 25; i++) {
                sb.append(hlpInStream.readChar());
            }
            this.krstneMeno = sb.toString().trim();
            sb.setLength(0);
            for (int i = 0; i < 25; i++) {
                sb.append(hlpInStream.readChar());
            }
            this.priezvisko = sb.toString().trim();
            this.cisloPreukazu = hlpInStream.readInt();
            this.datum_narodenia = LocalDate.ofEpochDay(hlpInStream.readLong());
            for (int i = 0; i < hospitalizacie.size(); i++) {
                hospitalizacie.get(i).FromByteArray(DataInputStream, hlpInStream);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion from byte array.");
        }
    }

    @Override
    public Object dajTyp() {
        return this;
    }

    @Override
    public void vypisZaznam() {
        if (platnyZaznam) {
            System.out.println(krstneMeno + "-" + priezvisko + "-" + cisloPreukazu + "-" + datum_narodenia);
        }
    }

    public void setKrstneMeno(String krstneMeno) {
        this.krstneMeno = krstneMeno;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public void setCisloPreukazu(int cisloPreukazu) {
        this.cisloPreukazu = cisloPreukazu;
    }

    public void setDatum_narodenia(LocalDate datum_narodenia) {
        this.datum_narodenia = datum_narodenia;
    }

    @Override
    public Object getKluc() {
        return cisloPreukazu;
    }

    @Override
    public boolean getPlatny() {
        return platnyZaznam;
    }

    @Override
    public String toString() {
        return "Cislo Preukazu: " + cisloPreukazu + " Meno: " + krstneMeno + " Priezvisko: " + priezvisko + " Datum Narodenia:" + datum_narodenia + "\n" + vypisHospitalizacie();
    }

    public void ukonciHospitalizaciu(LocalDate datumKonca) {
        for (Hospitalizacia hospitalizacia : hospitalizacie) {
            if (hospitalizacia.isJePlatna() && hospitalizacia.getDatumKonca().toEpochDay() < hospitalizacia.getDatumZaciatku().toEpochDay()) {
                hospitalizacia.setDatumKonca(datumKonca);
            }
        }
    }

    private String vypisHospitalizacie() {
        String vysledok = "";
        for (Hospitalizacia hospitalizacia : hospitalizacie) {
            if (hospitalizacia.isJePlatna()) {
                vysledok += "\t\t" + "Datum Zacatia: " + hospitalizacia.getDatumZaciatku() + " Datum Konca: " + hospitalizacia.getDatumKonca() + " Diagnoza: " + hospitalizacia.getDiagnoza() + "\n";
            }
        }
        return vysledok;
    }

}
