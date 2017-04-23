package p1ces29.LUCASFRANCA_QUESTAO8;

import java.util.Set;
import java.util.TreeSet;

public class Usuario {
	private String _nome;
	private String _dataBloqueio;
	private boolean _bloqueado;
	private Set<String> _livrosEmprestados;
	
	public Usuario (
		String nome
		)
	{
		_nome = nome;
		_dataBloqueio = null;
		_bloqueado = false;
		_livrosEmprestados = new TreeSet<String>();
	}
	
	public void SetDataBloqueio(
		String dataBloqueio
		)
	{
		_dataBloqueio = dataBloqueio;
	}
	
	public void SetBloqueado(
		boolean bloqueado
		)
	{
		_bloqueado = bloqueado;
	}
	
	public String GetDataBloqueio()
	{
		return _dataBloqueio;
	}
	
	public boolean GetBloqueado()
	{
		return _bloqueado;
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
}
