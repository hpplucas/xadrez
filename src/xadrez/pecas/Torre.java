package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public  class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}

	@Override
	public String toString() {
		return "R"; //Rook 
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().aquiHaUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaAdiversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().aquiHaUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaAdiversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
				
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().aquiHaUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaAdiversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//baixo
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
				
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().aquiHaUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaAdiversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		
		return mat;
	}


}
