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
import sem2.linearneHesovanie.Zaznam;

/**
 *
 * @author davidecek
 */
public class Hospitalizacia {

    private LocalDate datumZaciatku;
    private LocalDate datumKonca;
    private String diagnoza;
    private boolean jePlatna;

    public Hospitalizacia(LocalDate datumZaciatku, LocalDate datumKonca, String diagnoza) {
        this.jePlatna = false;
        this.datumZaciatku = datumZaciatku;
        this.datumKonca = datumKonca;
        this.diagnoza = diagnoza;
    }

    public Hospitalizacia(LocalDate datumZaciatku, LocalDate datumKonca, String diagnoza, boolean jePlatna) {
        this.datumZaciatku = datumZaciatku;
        this.datumKonca = datumKonca;
        this.diagnoza = diagnoza;
        this.jePlatna = jePlatna;
    }

    public boolean isJePlatna() {
        return jePlatna;
    }

    public void setJePlatna(boolean jePlatna) {
        this.jePlatna = jePlatna;
    }

    public LocalDate getDatumZaciatku() {
        return datumZaciatku;
    }

    public LocalDate getDatumKonca() {
        return datumKonca;
    }

    public String getDiagnoza() {
        return diagnoza;
    }

    public void setDatumKonca(LocalDate datumKonca) {
        this.datumKonca = datumKonca;
    }

    public int getVelkostZaznamu() {
        return 1 + 8 + 8 + (40*2);
    }
     public Hospitalizacia kopiruj() {
        Hospitalizacia hosp = new Hospitalizacia(datumZaciatku, datumKonca, diagnoza, jePlatna);
        return hosp;
    }

    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            hlpOutStream.writeBoolean(jePlatna);
            hlpOutStream.writeLong(datumZaciatku.toEpochDay());
            hlpOutStream.writeLong(datumKonca.toEpochDay());
            hlpOutStream.writeChars(diagnoza.trim());
            for (int i = 0; i < (40 - diagnoza.length()) * 2; i++) {
                hlpOutStream.writeByte(0);
            }

            return hlpByteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }

    public void FromByteArray(byte[] DataInputStream, DataInputStream hlpInStream) {
        StringBuilder sb = new StringBuilder();

        try {
            this.jePlatna = hlpInStream.readBoolean();
            this.datumZaciatku = LocalDate.ofEpochDay(hlpInStream.readLong());
            this.datumKonca = LocalDate.ofEpochDay(hlpInStream.readLong());
            for (int i = 0; i < 40; i++) {
                sb.append(hlpInStream.readChar());
            }
            this.diagnoza = sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion from byte array.");
        }
    }

}
