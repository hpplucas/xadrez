package aplication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partida = new PartidaXadrez();
		
		List<PecaXadrez> capturadas = new ArrayList<>();

		while (!partida.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.mostrarPartida(partida, capturadas);
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
				
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
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
		UI.clearScreen();
		UI.mostrarPartida(partida, capturadas);
		
	}

}
