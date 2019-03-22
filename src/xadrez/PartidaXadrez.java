package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		ConfiguracaoInicial();
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
	
	private void ConfiguracaoInicial() {
		tabuleiro.AlocarPeca(new Torre(tabuleiro,Cor.BRANCO), new Posicao(2 ,1));
		tabuleiro.AlocarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0 ,4));
		tabuleiro.AlocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7 ,4));
	}
}
