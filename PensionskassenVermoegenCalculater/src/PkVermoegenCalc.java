import java.math.*;

public class PkVermoegenCalc {	

	int alter;
	boolean istWeiblich;
	int einkommen;
	int maximalerLohn = 85320;
	int minimalerLohn = 21330;
	int koordinationsabzug =24885;
	int minimalerKoordinierterLohn = 3555;
	
	public static void main(String[] args) {
		
	}

	private int getPensionierungsalter(boolean istWeiblich) {
		int pensionierungsalter = (istWeiblich) ? 64 : 65;
		return pensionierungsalter;
	}

	private int getKoordinierterLohn() {
		int maxKoordinierterLohn = maximalerLohn - koordinationsabzug;
		int realerKoordinierterLohn = einkommen - koordinationsabzug;
		int versicherterKoordinierterLohn = 0;
		//true, wenn mehr verdient wird als das obligatorium 
		boolean a = (maxKoordinierterLohn < realerKoordinierterLohn) ? true : false;
		//true, wenn weniger als das minimum verdient wird
		boolean b = (minimalerKoordinierterLohn > realerKoordinierterLohn) ? true : false;
		
			if (a) {
				versicherterKoordinierterLohn = maxKoordinierterLohn;
			} else if (b) {
				versicherterKoordinierterLohn = minimalerKoordinierterLohn;
			} else {
				versicherterKoordinierterLohn = realerKoordinierterLohn;
			};

		return versicherterKoordinierterLohn;
	}

	/** 
	*@return:Summe der jährlichen Pensionkasseneinzahlungen (=Pensionskassenvermögen) basierend auf dem koordinierten Lohn
	*Annahme: Keine detailierten PK Daten eingegeben. Es wird angenommen, dass das Einkommen über alle Jahre das gleiche ist und nur der obligatorische Teil einbezahlt wird
	*Unter 25 gibt es keine PK Einzahlung, Anschliessend ändert sich der Beitrag alle 10 Jahre
	*/

	public int calculateTheoretischesPensionskassenVermögen(boolean istWeiblich, int einkommen) {
		
		this.istWeiblich = istWeiblich;
		this.einkommen = einkommen;
		int summeEinzahlungenProJahr = 0;
		int pensionierungsalter = getPensionierungsalter(istWeiblich);
		int k = getKoordinierterLohn();
		
		//a = theoretisches Alter für Berechnung
		for (int a = 25; a <= pensionierungsalter; a++) {
			int einzahlung = 0;
			if (a < 35) {
				einzahlung = (int) (k * 0.07);
				summeEinzahlungenProJahr += einzahlung;
			} else if (a >= 35 && a < 45) {
				einzahlung = (int) (k * 0.1);
				summeEinzahlungenProJahr += einzahlung;
			} else if (a >= 45 && a < 55) {
				einzahlung = (int) (k * 0.15);
				summeEinzahlungenProJahr += einzahlung;
			} else {
				einzahlung = (int) (k * 0.18);
				summeEinzahlungenProJahr += einzahlung;
			}
		};
		
		return summeEinzahlungenProJahr;
	}

}
