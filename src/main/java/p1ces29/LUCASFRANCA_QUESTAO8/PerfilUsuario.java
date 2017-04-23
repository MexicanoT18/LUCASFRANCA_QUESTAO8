package p1ces29.LUCASFRANCA_QUESTAO8;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PerfilUsuario {
	private Bibliotecaria _bibliotecaria;
	private String _nome;
	
	public PerfilUsuario(
		Bibliotecaria bibliotecaria,
		String nome
		) throws NullPointerException
	{
		if (bibliotecaria == null)
		{
			throw new NullPointerException("Bibliotecaria null");
		}
		if (nome == null)
		{
			throw new NullPointerException("Nome null");
		}
		_bibliotecaria = bibliotecaria;
		_nome = nome;
	}

	public String GetEstado(
		String dataAtual
		) throws Exception
	{
		return _bibliotecaria.GetEstadoUsuario(_nome, dataAtual);
	}
	
	public List<Livro> GetListaLivros() throws Exception
	{
		return _bibliotecaria.GetListaLivros(_nome);
	}
	
	public String GetEstadoLivro(
		String nomeLivro
		) throws Exception
	{
		return _bibliotecaria.GetEstadoLivro(nomeLivro);
	}
	
}
