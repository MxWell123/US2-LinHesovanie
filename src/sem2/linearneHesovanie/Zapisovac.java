/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem2.linearneHesovanie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import sem2.aplikacia.Program;
import sem2.aplikacia.Sem2;
import sem2.zdravotnaKarta.Pacient;

/**
 *
 * @author davidecek
 */
public class Zapisovac {

    RandomAccessFile rdf;
    RandomAccessFile rdfPrep;

    public Zapisovac(String typ) {
        try {
            vytvorSubory(typ);
            vytvorSubory(typ + "Prepl");
            this.rdf = new RandomAccessFile(typ + "/" + typ + ".txt", "rw");
            this.rdfPrep = new RandomAccessFile(typ + "Prepl/" + typ + ".txt", "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Zapisovac(String typ, boolean pom) {
        try {
            this.rdf = new RandomAccessFile(typ + "/" + typ + ".txt", "rw");
            this.rdfPrep = new RandomAccessFile(typ + "Prepl/" + typ + ".txt", "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void zmazPriecinok(Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Sem2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void vytvorPriecinok(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void vytvorSubory(String typ) {
        Path cesta1 = Paths.get(typ);
        if (Files.exists(cesta1) == true) {
            zmazPriecinok(cesta1);
        }
        vytvorPriecinok(cesta1);
    }

    public long dajVelkostSuboru(String typ) {
        long pom = 0;
        try {
            pom = rdfPrep.length();
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pom;
    }

    public void ulozLinHesovanie(LinearneHesovanie lin) {
        Path cesta1 = Paths.get("LinHesovanie");
        if (Files.exists(cesta1) == true) {
            zmazPriecinok(cesta1);
        }
        vytvorPriecinok(cesta1);

        cesta1 = Paths.get("LinHesovanie/LinHesovanie.txt");
        try (FileWriter fw = new FileWriter(cesta1.toString(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(lin.getPocetSkupinBlokov() + "," + lin.getdMin() + "," + lin.getdMax() + "," + lin.getUroven() + "," + lin.getPocetZaznamov() + ","
                    + lin.getSplitPointer() + "," + lin.getPocetAlokovanychMiest() + "," + lin.getPocetObsadenychMiest() + "," + lin.getPocetZaznamovVPreplnovacomBloku() + "," + lin.getAktualnyPocetSkupin() + "," + lin.getPrazdnePreplnovacieBloky());

        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ulozBlok(Blok blok, long adresa, int velkostZaznamu, boolean preplnovaci) {
        try {
            if (preplnovaci) {
                long pom = adresa * ((blok.getPocetZaznamov() * (velkostZaznamu)) + 12);
                rdfPrep.seek(pom);
                rdfPrep.write(blok.ToByteArray());
            } else {
                long pom = adresa * ((blok.getPocetZaznamov() * (velkostZaznamu)) + 12);
                rdf.seek(pom);
                rdf.write(blok.ToByteArray());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] nacitajBlok(int pocetZaznamov, int velkostZaznamu, long adresa) {
        int pom2 = (pocetZaznamov * (velkostZaznamu)) + 12;
        byte[] paArray = new byte[pom2];
        try {
            long pom = adresa * ((pocetZaznamov * (velkostZaznamu)) + 12);
            rdf.seek(pom);
            rdf.read(paArray);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paArray;
    }

    public byte[] nacitajPreplnovaciBlok(int pocetZaznamov, int velkostZaznamu, long adresa) {
        int pom2 = (pocetZaznamov * (velkostZaznamu)) + 12;
        byte[] paArray = new byte[pom2];
        try {
            long pom = adresa * ((pocetZaznamov * (velkostZaznamu)) + 12);
            rdfPrep.seek(pom);
            rdfPrep.read(paArray);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paArray;
    }

    public void zavriSubory() {
        try {
            this.rdf.close();
            this.rdfPrep.close();
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void skratHlavnySubor(long adresa, int pocetZaznamov, int velkostZaznamu) {
        try {
            long dlzka = rdf.length();
            long pom = adresa * ((pocetZaznamov * velkostZaznamu) + 12);
            rdf.setLength(pom);
        } catch (IOException ex) {
            Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void skratPreplnovaciSubor(LinkedList<Long> prazdnePreplnovacieBloky, int pocetZaznamov, int velkostZaznamu) {
        long adresaPoslednehoVPoli = 0;
        long pomAdresa = 0;
        if (prazdnePreplnovacieBloky.size() > 0) {
            try {
                prazdnePreplnovacieBloky.sort(Long::compareTo);
                long velkostSuboru = this.rdfPrep.length();
                adresaPoslednehoVPoli = prazdnePreplnovacieBloky.getLast();
                long velkostBloku = (pocetZaznamov * (velkostZaznamu)) + 12;
                long adresaPosledneho = (velkostSuboru / velkostBloku) - 1;
                pomAdresa = velkostSuboru;
                if (adresaPosledneho == adresaPoslednehoVPoli) {
                    adresaPoslednehoVPoli = prazdnePreplnovacieBloky.pollLast();
                }
                while (adresaPosledneho == adresaPoslednehoVPoli) {
                    pomAdresa = pomAdresa - velkostBloku;
                    adresaPosledneho--;
                    if (prazdnePreplnovacieBloky.size() > 0) {
                        adresaPoslednehoVPoli = prazdnePreplnovacieBloky.pollLast();
                    } else {
                        break;
                    }
                }
                rdfPrep.setLength(pomAdresa);

            } catch (IOException ex) {
                Logger.getLogger(Zapisovac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
