package p1ces29.LUCASFRANCA_QUESTAO8;

import java.util.Map;
import java.util.TreeMap;

public class BDLivros {
	public Map<String, Livro> _livros;
	
	public BDLivros()
	{
		_livros = new TreeMap<String, Livro>();
	}
	
	public void AdicionarLivro(
		String nome
		) throws Exception
	{
		if (ExisteLivro(nome))
		{
			throw new Exception("Livro ja cadastrado");
		}
		Livro livro = new Livro(nome);
		_livros.put(nome, livro);
	}

	public Livro GetLivro(
		String nome
		) throws Exception
	{
		if (!ExisteLivro(nome))
		{
			throw new Exception("Livro inexistente");
		}
		return _livros.get(nome);
	}

	public void RemoverLivro(
		String nome
		) throws Exception
	{
		if (!ExisteLivro(nome))
		{
			throw new Exception("Livro inexistente");
		}
		_livros.remove(nome);
	}
	
	public boolean ExisteLivro(
		String nome
		) throws Exception
	{
		return _livros.containsKey(nome);
	}
	
	public boolean EstaDisponivel(
		String nome
		) throws Exception
	{
		if (!ExisteLivro(nome))
		{
			throw new Exception("Livro inexistente");
		}
		return !_livros.get(nome).GetEmprestado() && !_livros.get(nome).GetExtraviado();
	}
	
	public void EmprestarLivro(
		String nome,
		String nomeDono
		) throws Exception
	{
		if (!EstaDisponivel(nome))
		{
			throw new Exception("Livro ja emprestado");
		}
		Livro livro = _livros.get(nome);
		livro.SetEmprestado(true);
		livro.SetNomeDono(nomeDono);
	}
	
	public void DevolverLivro(
		String nome
		) throws Exception
	{
		if (EstaDisponivel(nome))
		{
			throw new Exception("Livro nao emprestado");
		}
		Livro livro = _livros.get(nome);
		livro.SetEmprestado(false);
		livro.SetNomeDono(null);
	}
}
