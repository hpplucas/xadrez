 package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
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
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.incrementarContMov();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.AlocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento (Posicao origem, Posicao destino, Peca capturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarContMov();
		tabuleiro.AlocarPeca(p, origem);
		
		if (capturada != null) {
			tabuleiro.AlocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasNoTabuleiro.add(capturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.aquiHaUmaPeca(posicao)) {
			throw new ExecaoXadrez("Não há peça na posição de origem.");
		}
		
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExecaoXadrez("A peça escolhida não é sua.");
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
		throw new IllegalStateException("Não existe um Rei da cor " + cor + " no tabuleiro");
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
	
	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for (Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int li=0;li<mat.length;li++) {
				for (int co=0;co<mat.length;co++) {
					if (mat[li][co]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosition();
						Posicao destino = new Posicao(li,co);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarCheck(cor)) {
							return false;
						}
					}
				}
			}
			
			
		}
		return true;

	}

	
	private void alocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.AlocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
	}
	
	private void ConfiguracaoInicial() {
		
		alocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
		alocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

		alocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		alocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		alocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		alocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		alocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
		alocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
		
		}
}
