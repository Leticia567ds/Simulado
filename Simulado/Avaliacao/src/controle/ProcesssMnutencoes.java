package controle;

import java.util.ArrayList;

import modelo.DAO;
import modelo.Manutencao;

public class ProcesssMnutencoes {

	
	public static ArrayList<Manutencao> manutencoes = new ArrayList<>();
	private static DAO d = new DAO();
	
	public static void abrir() {
		manutencoes = d.ler();
	}
	
	public static void salvar() {
		d.escrever(manutencoes);
	}
	
	public static void carregar() {
		// Limpar a lista
		manutencoes = new ArrayList<>();
		// Preencher com testes
		manutencoes.add(new Manutencao(1, "20/05/2022", "injetora", 30d, 2d));
		manutencoes.add(new Manutencao(2, "21/05/2022", "estera", 50d, 3d));
		manutencoes.add(new Manutencao(3, "21/05/2022", "Brach Hidralico", 100d, 4d));
	}
	
}
