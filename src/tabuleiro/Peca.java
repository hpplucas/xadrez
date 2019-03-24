package tabuleiro;

public abstract class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
	}
	
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossive(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean isAlgumMovimento() {
		
		boolean[][] mat = movimentosPossiveis();
		for (int li=0;li<mat.length;li++) {	
			for (int co=0;co<mat.length;co++) {			
				if (mat[li][co]) {
					return true;
				}
			}
		} 
		
		return false;
	}
	
}
