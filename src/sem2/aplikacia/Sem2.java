/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.aplikacia;

import sem2.aplikacia.Test;
import java.io.IOException;
import java.time.LocalDate;
import sem2.linearneHesovanie.LinearneHesovanie;
import sem2.zdravotnaKarta.Pacient;

/**
 *
 * @author davidecek
 */
public class Sem2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        Pacient pacient1 = new Pacient();
//        Pacient pacient2 = new Pacient("Tomas", "Bavala", 1360, LocalDate.now());
//        Pacient pacient3 = new Pacient("Tomas", "Bavala", 5948, LocalDate.now());
//        Pacient pacient4 = new Pacient("Tomas", "Bavala", 8029, LocalDate.now());
//        Pacient pacient5 = new Pacient("Tomas", "Bavala", 6447, LocalDate.now());
//        Pacient pacient6 = new Pacient("Tomas", "Bavala", 3515, LocalDate.now());
//        Pacient pacient7 = new Pacient("Tomas", "Bavala", 1053, LocalDate.now());
//        Pacient pacient8 = new Pacient("Tomas", "Bavala", 4491, LocalDate.now());
//        Pacient pacient9 = new Pacient("Tomas", "Bavala", 9761, LocalDate.now());
//        Pacient pacient10 = new Pacient("Tomas", "Bavala", 8719, LocalDate.now());
//        Pacient pacient11 = new Pacient("Tomas", "Bavala", 2854, LocalDate.now());
//        LinearneHesovanie lin = new LinearneHesovanie(2, 0.64, 0.8, 2, pacient1, 1);
//        lin.vlozZaznam(pacient2);
//        lin.vlozZaznam(pacient3);
//        lin.vlozZaznam(pacient4);
//        lin.vlozZaznam(pacient5);
//        lin.vlozZaznam(pacient6);
//        lin.vlozZaznam(pacient7);
//        lin.vlozZaznam(pacient8);
//        lin.vlozZaznam(pacient9);
//        lin.vlozZaznam(pacient10);
//        lin.vlozZaznam(pacient11);
//
//        lin.vymazZaznam(pacient2);
//        lin.vymazZaznam(pacient3);
//        lin.vymazZaznam(pacient4);        
//        lin.vymazZaznam(pacient5);
//        lin.sekvencnyVypis();
        Test t = new Test();
        t.testInsertDelete();
//        lin.najdi(pacient2, -1);
//        lin.vypisSubor();
//        System.out.println("//////////////////////////////////////////////////");
//        System.out.println("Preplnovaci");
//        System.out.println("//////////////////////////////////////////////////");
//        lin.vypisPreplnovaciSubor();

    }

}
