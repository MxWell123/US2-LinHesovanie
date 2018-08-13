/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.aplikacia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import sem2.zdravotnaKarta.Hospitalizacia;
import sem2.zdravotnaKarta.Pacient;

/**
 *
 * @author davidecek
 */
public class Generator {

    private Random rr;

    public Generator() {
        final java.util.Random rand = new java.util.Random();
        rr = new Random(2515);
    }

    public String generujString() {
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final java.util.Random rand = new java.util.Random();

        final Set<String> identifiers = new HashSet<String>();

        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(1) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public LocalDate generujDatum() {

        int den = rr.nextInt(28) + 1;
        int mesiac = rr.nextInt(12) + 1;
        int rok = rr.nextInt((2017 - 1970) + 1) + 1970;
        LocalDate d = null;
        d = LocalDate.of(rok, mesiac, den);

        return d;
    }

    public void generuj(Program program) {

        for (int i = 0; i < 5000; i++) {
            int cisloPreukazu = rr.nextInt(20000) + 1;
            LocalDate datumZaciatku = generujDatum();
            ArrayList<Hospitalizacia> hospitalizacie = new ArrayList<>();
            int pom = 0;
//            program.pridajPacienta(generujString(), generujString(), cisloPreukazu, generujDatum());
//            program.zacniPacientoviHospitalizaciu(cisloPreukazu, datumZaciatku, generujString());

            for (int j = 0; j < rr.nextInt(50); j++) {
                pom += rr.nextInt(30);
                hospitalizacie.add(new Hospitalizacia(datumZaciatku, datumZaciatku.plusDays(pom), generujString(), true));
                datumZaciatku = datumZaciatku.plusDays(pom);
                //  program.ukonciPacientoviHospitalizaciu(cisloPreukazu, datumZaciatku);
            }
            if (rr.nextBoolean()) {
                //program.zacniPacientoviHospitalizaciu(cisloPreukazu, datumZaciatku, generujString());
                hospitalizacie.add(new Hospitalizacia(datumZaciatku, LocalDate.MIN, generujString(), true));
            }
            int pocetOpakovani = 100 - hospitalizacie.size();
            for (int j = 0; j < pocetOpakovani; j++) {
                hospitalizacie.add(new Hospitalizacia(LocalDate.MIN, LocalDate.MIN, generujString(), false));
            }
            Pacient pacient = new Pacient(generujString(), generujString(), cisloPreukazu, generujDatum(), hospitalizacie);
            program.lin.vlozZaznam(pacient);
        }

    }

}
