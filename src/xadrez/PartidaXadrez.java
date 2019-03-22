package xadrez;

import tabuleiro.Tabuleiro;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
	}
	
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int li=0;li<tabuleiro.getLinhas();li++) {
			for (int co = 0;co<tabuleiro.getColunas();co++) {
				 mat[li][co] = (PecaXadrez) tabuleiro.peca(li,co);
			}
		}
		return mat;
		
		
	}
}
