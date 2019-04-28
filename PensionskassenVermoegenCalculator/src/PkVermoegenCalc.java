import java.awt.List;
import java.math.*;
import java.util.ArrayList;

public class PkVermoegenCalc {

	boolean istWeiblich;
	int einkommen;
	double zins;
	int maxAhvRente = 28440;
	
	public static void main(String[] args) {
		
	}

    /**
     *
     * @param istWeiblich
     * @return Pensionierungsalter abhängig vom Geschlecht
     */
	private int getPensionierungsalter(boolean istWeiblich) {
		int pensionierungsalter = (istWeiblich) ? 64 : 65;
		return pensionierungsalter;
	}

    /**
     *
     * @param einkommen
     * @return true, wenn einkommen grössergleich BVG Eintrittsschwelle
     */
	public boolean eintrittsschwelleErreicht(int einkommen) {
	    this.einkommen = einkommen;
        boolean eintrittsschwelleErreicht = (einkommen >= getMinimalerLohn()) ? true : false;
        return eintrittsschwelleErreicht;
    }

    /**
     *
     * @return Koordinationsabzug: 24885 in 2019
     */
    private int getKoordinationsabzug() {
	    int k = maxAhvRente / 8 * 7;
	    return k;
    }

    /**
     *
     * @return Minimaler koordinierter Lohn: Wenn Einkommen grösser als minimaler Lohn aber kleiner als Maximale AHV Rente: 3555 in 2019
     */
    private int getMinimalerKoordinierterLohn() {
	    int m = maxAhvRente / 8;
	    return m;
    }

    /**
     *
     * @return Minimal Lohn für BVG Versicherung (Eintrittsschwelle): 21330 in 2019
     */
    private int getMinimalerLohn() {
	    int m = getKoordinationsabzug() - getMinimalerKoordinierterLohn();
	    return m;
    }

    /**
     *
     * @return Maximaler Lohn für obligatorische BVG Versicherung: 85320 in 2019
     */
    private int getMaximalerLohn() {
	    int m = maxAhvRente * 3;
	    return m;
    }

    /**
     *
     * @return Maximal koordinierter Lohn (Maximaler Lohn - Koordinationsabzug): 60435 in 2019
     */
    private int getMaximalKoordinierterLohn() {
	    int m = getMaximalerLohn() - getKoordinationsabzug();
	    return m;
    }

    /**
     *
     * @return koordinierter Lohn basierend auf dem Einkommen und der Ober- bzw. Untergrenze
     */
	private int getKoordinierterLohn() {

		int realerKoordinierterLohn = einkommen - getKoordinationsabzug();
		int versicherterKoordinierterLohn = 0;
			if (realerKoordinierterLohn > getMaximalKoordinierterLohn()) {
				versicherterKoordinierterLohn = getMaximalKoordinierterLohn();
			} else if (!eintrittsschwelleErreicht(einkommen)) {
                versicherterKoordinierterLohn = 0;
            } else if (realerKoordinierterLohn < getMinimalerKoordinierterLohn()) {
			    versicherterKoordinierterLohn = getMinimalerKoordinierterLohn();
            } else {
				versicherterKoordinierterLohn = realerKoordinierterLohn;
			};

		return versicherterKoordinierterLohn;
	}

    /**
     *
     * @param istWeiblich
     * @param einkommen
     * @param zins
     * @return Summe der vorschüssig aufgezinsten Pensionskassenbeiträge
     * Annahme: Keine detailierten PK Daten eingegeben. Es wird angenommen, dass das Einkommen über alle Jahre das gleiche ist und nur der obligatorische Teil einbezahlt wird
     * Unter 25 gibt es keine PK Einzahlung, Anschliessend ändert sich der Beitrag alle 10 Jahre
     */
	public int calculateTheoretischesPensionskassenVermoegen(boolean istWeiblich, int einkommen, double zins) {

		this.istWeiblich = istWeiblich;
		this.einkommen = einkommen;
		this.zins = zins;
		int summeEinzahlungenProJahrVerzinst = 0;
		int pensionierungsalter = getPensionierungsalter(istWeiblich);
		int k = getKoordinierterLohn();
        double z = 1 + zins/100;
        double einzahlungVerzinst = 0;
		ArrayList<Integer> einzahlungen = new ArrayList<Integer>();
        ArrayList<Integer> einzahlungenVerzinstSummiert = new ArrayList<Integer>();

		//a = theoretisches Alter für Berechnung
        //einzahlungen - noch ohne zins, damit der Array ausgegeben werden kann um die Berechnung nachzuvollziehen
		for (int a = 25; a <= pensionierungsalter; a++) {
			int einzahlung = 0;
			if (a < 35) {
				einzahlung = (int) Math.round(k * 0.07);
				einzahlungen.add(einzahlung);
			} else if (a >= 35 && a < 45) {
				einzahlung = (int) Math.round(k * 0.1);
				einzahlungen.add(einzahlung);
			} else if (a >= 45 && a < 55) {
				einzahlung = (int) Math.round(k * 0.15);
				einzahlungen.add(einzahlung);
			} else {
				einzahlung = (int) Math.round(k * 0.18);
				einzahlungen.add(einzahlung);
			}
		};

        for (int einzahlung : einzahlungen) {

            einzahlungVerzinst = (einzahlungVerzinst + einzahlung) * z;
            einzahlungenVerzinstSummiert.add((int) Math.round(einzahlungVerzinst));
            summeEinzahlungenProJahrVerzinst = (int) Math.round(einzahlungVerzinst);
        }

		System.out.println("Einzahlung: " + einzahlungen);
		System.out.println("Einzahlungen verzinst: " + einzahlungenVerzinstSummiert);
		System.out.println("Koordinierter Lohn: " + k);

		return summeEinzahlungenProJahrVerzinst;
	}

}
