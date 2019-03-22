package aplication;

import xadrez.PecaXadrez;

public class UI {

	public static void MostrarTabuleiro(PecaXadrez[][] pecas) {
		
		for (int li = 0;li<pecas.length;li++) {
			System.out.print((8 - li) + " ");
			
			for (int co = 0; co<pecas.length;co++) {
				MostrarPeca(pecas[li][co]);
				
			}
			
			System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
	}
	
	private static void MostrarPeca(PecaXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}
}
