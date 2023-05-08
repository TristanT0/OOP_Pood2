package pood.eepilinepood;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.ToDoubleBiFunction;

public class Ostukorv {

    List<Toode> ostukorv = new ArrayList<>();

    public void lisaToode(Toode toode){
        ostukorv.add(toode);
    }

    public double ostukorviMaksumus(){

        double summa = 0;

        for (int i = 0; i < ostukorv.size(); i++) {
            summa += ostukorv.get(i).getHind();
        }
        return summa;
    }

    public double kokkuSäästetud(){

        double säästetud = 0;

        for (int i = 0; i < ostukorv.size(); i++) {
            säästetud += ostukorv.get(i).getSäästetudSumma();
        }
        return säästetud;
    }

    @Override
    public String toString() {
        StringBuilder tooted = new StringBuilder();

        tooted.append("Tooted ostukorvis: \n");
        for (int i = 0; i < ostukorv.size(); i++) {

            tooted.append(" ").append(ostukorv.get(i).toString()).append('\n');
        }
        return tooted.toString();
    }
}
