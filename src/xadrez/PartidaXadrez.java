package xadrez;

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
	
	private void alocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.AlocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void ConfiguracaoInicial() {
		alocarNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		alocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
	}
}
