package aplication;

import xadrez.PartidaXadrez;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PartidaXadrez partida = new PartidaXadrez();
		UI.MostrarTabuleiro(partida.getPecas());
	}

}