import java.util.Scanner;

public class TestPkVermoegenCalc {
	

	public static void main(String[] args) {
		boolean istWeiblich = false;
		int einkommen = 0;
		int pkVermoegen = 0;
		
		Scanner scannerVariable = new Scanner(System.in);
		
		System.out.println("Geschlecht (m/w): ");
		
		String g = scannerVariable.next();
		if (g.contains("m")) {
			istWeiblich = false;
		} else {
			istWeiblich = true;
		};
		
		System.out.println("Einkommen (als ganze Zahl): ");

		einkommen = scannerVariable.nextInt();
		
		PkVermoegenCalc c = new PkVermoegenCalc();
		pkVermoegen = c.calculateTheoretischesPensionskassenVermoegen(istWeiblich, einkommen);
		
		System.out.println("Das folgende PK Vermoegen wurde berechnet: ");
		System.out.println(pkVermoegen);
		System.out.println("Die Berechnung basiert auf folgenden Angaben:");
		System.out.println("Ist weiblich = " + istWeiblich);
		System.out.println("Einkommen = " + einkommen);
	}

}
