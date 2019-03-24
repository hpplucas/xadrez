 package xadrez;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>(); 
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		ConfiguracaoInicial();
		turno = 1;
		jogadorAtual = Cor.BRANCO;
	}
	
	public int getTurno() {
		return turno;
	}
	
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
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
	
	public boolean[][] movimetosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosition();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez fazerMovimentoXadrez(PosicaoXadrez posOrigem, PosicaoXadrez posDestino) {
		Posicao origem = posOrigem.toPosition();
		Posicao destino = posDestino.toPosition();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExecaoXadrez("Voce nao pode se colocar em check.");
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.AlocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento (Posicao origem, Posicao destino, Peca capturada) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.AlocarPeca(p, origem);
		
		if (capturada != null) {
			tabuleiro.AlocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasNoTabuleiro.add(capturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.aquiHaUmaPeca(posicao)) {
			throw new ExecaoXadrez("N�o h� pe�a na posi��o de origem.");
		}
		
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExecaoXadrez("A pe�a escolhida n�o � sua.");
		}
		
		if (!tabuleiro.peca(posicao).isAlgumMovimento()) {
			throw new ExecaoXadrez("N�o h� movimentos possiveis para a pe�a escolhida.");
		}
	}
	
	private void validarPosicaoDestino (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossive(destino)) {
			throw new ExecaoXadrez("A pe�a escolhida n�o pode se mover para a posi��o de destino."); 
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual==Cor.BRANCO)?Cor.PRETO: Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO)? Cor.PRETO: Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p: lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("N�o existe um Rei da cor" + cor + "no tabuleiro");
	}
	
	
	private boolean testarCheck(Cor cor) {
		
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosition();
		
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	
	private void alocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.AlocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
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
