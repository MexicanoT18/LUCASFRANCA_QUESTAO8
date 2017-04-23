package p1ces29.LUCASFRANCA_QUESTAO8;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Usuario {
	private String _nome;
	private boolean _bloqueadoCobranca;
	private Set<String> _livrosEmprestados;
	
	public Usuario (
		String nome
		)
	{
		_nome = nome;
		_bloqueadoCobranca = false;
		_livrosEmprestados = new TreeSet<String>();
	}
	
	public void SetBloqueadoCobranca(
		boolean bloqueado
		)
	{
		_bloqueadoCobranca = bloqueado;
	}
	
	public boolean GetBloqueadoCobranca()
	{
		return _bloqueadoCobranca;
	}
	
	public String GetNome()
	{
		return _nome;
	}
	
	public void EmprestarLivro(
		String nome
		)
	{
		_livrosEmprestados.add(nome);
	}
	
	public boolean VerificarSeLivroEstaEmprestado(
		String nome
		)
	{
		return _livrosEmprestados.contains(nome);
	}
	
	public void RemoverLivro(
		String nome
		)
	{
		_livrosEmprestados.remove(nome);
	}
	
	public Set<String> GetLivrosEmprestados()
	{
		return Collections.unmodifiableSet(_livrosEmprestados);
	}
}
