package pood.eepilinepood;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {

        HashMap<Integer, Toode> tooted = new HashMap<>();

        Toode toode = new Toode(2, "Lusikas", 50);
        Toode toode1 = new Toode(1.35, "kahvel");

        tooted.put(0, toode);
        tooted.put(1, toode1);


        Ostukorv ostukorv = new Ostukorv();

        System.out.println(ostukorv.ostukorviMaksumus());

        System.out.println(ostukorv);

    }

}

