package aplication;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partida = new PartidaXadrez();

		while (true) {
			try {
				UI.clearScreen();
				UI.MostrarTabuleiro(partida.getPecas());
				System.out.println();
				
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partida.movimetosPossiveis(origem);
				UI.clearScreen();
				UI.MostrarTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partida.fazerMovimentoXadrez(origem, destino);
			}
			catch (ExecaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine();
			}
			
		}
		
	}

}
