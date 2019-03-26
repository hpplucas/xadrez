package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partida;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "K"; //KING
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContMov() == 0;
	}
	
	
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//abaixo
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//direita
		p.setValues(posicao.getLinha(), posicao.getColuna()+ 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//noroeste 
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//nordeste
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//sudoeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//sudeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p)&&podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] =  true;
		}
		
		//Movimento especiao Roque
		if (getContMov() == 0 && !partida.getCheck()) {
			//Roque pequeno
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);	
			if (testeTorreRoque(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);	
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);	
				if (getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null) {
					mat[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
			
			//Roque grande
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);	
			if (testeTorreRoque(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);	
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);	
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);	
				if (getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null&& getTabuleiro().peca(p3)==null) {
					mat[posicao.getLinha()][posicao.getColuna()-2] = true;
				}
			}
		}
		
		return mat;
	}



}
