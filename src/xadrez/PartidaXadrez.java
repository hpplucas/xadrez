 package xadrez;

import java.util.concurrent.ExecutionException;

import tabuleiro.Peca;
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
	
	
	
	public PecaXadrez fazerMovimentoXadrez(PosicaoXadrez posOrigem, PosicaoXadrez posDestino) {
		Posicao origem = posOrigem.toPosition();
		Posicao destino = posDestino.toPosition();
		
		validarPosicaoOrigem(origem);
		
		validarPosicaoDestino(origem, destino);
		
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.AlocarPeca(p, destino);
		return pecaCapturada;
	}
	
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.aquiHaUmaPeca(posicao)) {
			throw new ExecaoXadrez("Não há peça na posição de origem.");
		}
		
		if (!tabuleiro.peca(posicao).isAlgumMovimento()) {
			throw new ExecaoXadrez("Não há movimentos possiveis para a peça escolhida.");
		}
	}
	
	private void validarPosicaoDestino (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossive(destino)) {
			throw new ExecaoXadrez("A peça escolhida não pode se mover para a posição de destino."); 
		}
	}
	
	
	
	private void alocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.AlocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void ConfiguracaoInicial() {
		
		alocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		alocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}
