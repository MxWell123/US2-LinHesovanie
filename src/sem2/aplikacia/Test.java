/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.aplikacia;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Random;
import sem2.linearneHesovanie.LinearneHesovanie;
import sem2.zdravotnaKarta.Pacient;

/**
 *
 * @author davidecek
 */
public class Test {

    public Test() {
    }

    public void testInsertNajdi() throws IOException {
        Random random = new Random(0);
        Pacient pacient1 = new Pacient();
        LinearneHesovanie lin = new LinearneHesovanie(2, 0.64, 0.8, 3, pacient1, 4);
        LinkedList<Pacient> listPacientov = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            int rnd = random.nextInt(1000000);
            Pacient pacient = new Pacient("Tomas", "Bavala", rnd, LocalDate.now());
            if (lin.vlozZaznam(pacient)) {
                listPacientov.add(pacient);
            }
        }
//        int j = 0;
        for (Pacient pacient : listPacientov) {
//            j++;
            if (lin.najdi(pacient, -1) != null) {
                System.out.println(pacient.getKluc());
                System.out.println("error");
            }
        }
//        lin.vypisSubor();
//        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
//        lin.vypisPreplnovaciSubor();;
//        lin.getZapisovac().zavriSubory();
    }

    public void testInsertDelete() throws IOException {
        Random random = new Random(0);
        Pacient pacient1 = new Pacient();
        LinearneHesovanie lin = new LinearneHesovanie(2, 0.64, 0.8, 2, pacient1,1);
        LinkedList<Pacient> listPacientov = new LinkedList<>();
        for (int i = 0; i < 10000; i++) {
            int rnd = random.nextInt(1000000);
            Pacient pacient = new Pacient("Tomas", "Bavala", rnd, LocalDate.now());
            if (lin.vlozZaznam(pacient)) {
                listPacientov.add(pacient);
            }
        }
        for (Pacient pacient : listPacientov) {
            if (!lin.vymazZaznam(pacient)) {
                System.out.println("error");
            }
            
        }
        lin.getZapisovac().zavriSubory();

    }

}
