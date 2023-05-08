package pood.eepilinepood;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Toode{
    double hind;
    String nimi;
    double allahindlus;

    // Konstruktor, kui allahindlus on toodud
    public Toode(double hind, String nimi, double allahindlus) {
        this.hind = hind;
        this.nimi = nimi;
        this.allahindlus = allahindlus;
    }

    // Konstruktor, kui tootel ei ole allahindlust
    public Toode(double hind, String nimi) {
        this.hind = hind;
        this.nimi = nimi;
        this.allahindlus = 0;
    }

    // Ostukorvi summa arvutamiseks (?)
    public double getHind() {
        if (allahindlus == 0) {
            return hind;
        } else {
            return hindAllahindlusega(hind, allahindlus);
        }
    }

    public double getSäästetudSumma(){
        if (allahindlus == 0) {
            return 0;
        } else {
            return hind - hindAllahindlusega(hind, allahindlus);
        }
    }

    // Allahindlusega toote uue hinna arvutamine
    public double hindAllahindlusega(double hind, double allahindlus) {
        double uusHind = hind-allahindlus*hind/100;
        BigDecimal ümardatult = new BigDecimal(uusHind).setScale(2, RoundingMode.HALF_UP);
        return ümardatult.doubleValue();
    }

    @Override
    // Toote + hinna väljastamine
    public String toString() {
        if (allahindlus!=0) {
            return " " + nimi + " " + hindAllahindlusega(hind, allahindlus) + " eurot (vana hind " + hind + " eurot)";
        } else {
            return " " + nimi + " " + hind + " eurot";
        }
    }
}
