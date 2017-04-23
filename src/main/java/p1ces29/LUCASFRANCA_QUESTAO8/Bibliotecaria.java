package p1ces29.LUCASFRANCA_QUESTAO8;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Bibliotecaria {

	private BDUsuarios _bdUsuarios;
	private BDLivros _bdLivros;
	
	public Bibliotecaria(
		BDUsuarios bdUsuarios,
		BDLivros bdLivros
		) throws NullPointerException
	{
		if (bdUsuarios == null)
		{
			throw new NullPointerException("Banco de dados de usuarios null");
		}
		if (bdLivros == null)
		{
			throw new NullPointerException("Banco de dados de livros null");
		}
		_bdUsuarios = bdUsuarios;
		_bdLivros = bdLivros;
	}
	
	public void RegistrarUsuario(
		String nome
		) throws Exception
	{
		_bdUsuarios.CadastrarUsuario(nome);
	}
	
	public void RemoverUsuario(
		String nome
		) throws Exception
	{
		_bdUsuarios.RemoverUsuario(nome);
	}
	
	public void BloquearUsuarioCobranca(
		String nome,
		String dataBloqueio
		) throws Exception
	{
		_bdUsuarios.BloquearUsuarioCobranca(nome);
	}
	
	public boolean EstaBloqueadoPorCobranca(
		String nome
		) throws Exception
	{
		return _bdUsuarios.GetUsuario(nome).GetBloqueadoCobranca();
	}
	
	public boolean EstaBloqueadoPorTempo(
		String nome,
		String dataAtual
		) throws Exception
	{
		Set<String> livrosEmprestados = _bdUsuarios.GetUsuario(nome).GetLivrosEmprestados();
		
		for(String nomeLivro : livrosEmprestados)
		{
			if (_bdLivros.GetLivro(nomeLivro).GetVencido(dataAtual))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean VerificarUsuarioBloqueado(
		String nome,
		String dataAtual
		) throws Exception
	{
		return _bdUsuarios.EstaBloqueado(nome) || EstaBloqueadoPorTempo(nome, dataAtual);
	}
	
	public void DesbloquearUsuario(
		String nome,
		String dataAtual
		) throws Exception
	{
		if (EstaBloqueadoPorTempo(nome, dataAtual))
		{
			throw new Exception("Usuario nao pode ser desbloqueado");
		}
		_bdUsuarios.DesbloquearUsuario(nome);
	}
	
	public boolean LivroDisponivel(
		String nome
		) throws Exception
	{
		return _bdLivros.EstaDisponivel(nome);
	}
	
	public void EmprestarLivro(
		String nomeUsuario,
		String nomeLivro,
		String dataLimite,
		String dataAtual
		) throws Exception
	{
		Usuario usuario = _bdUsuarios.GetUsuario(nomeUsuario);
		if (usuario.VerificarSeLivroEstaEmprestado(nomeLivro))
		{
			throw new Exception("Livro ja nas maos do usuario");
		}
		if (!LivroDisponivel(nomeLivro))
		{
			throw new Exception("Livro ja emprestado");
		}
		if (VerificarUsuarioBloqueado(nomeUsuario, dataAtual))
		{
			throw new Exception("Usuario bloqueado");
		}
		usuario.EmprestarLivro(nomeLivro);
		_bdLivros.EmprestarLivro(nomeLivro, nomeUsuario, dataLimite);
	}
	
	public void DevolverLivro(
		String nomeUsuario,
		String nomeLivro
		) throws Exception
	{
		if (LivroDisponivel(nomeLivro))
		{
			throw new Exception("Livro ja esta na biblioteca");
		}
		Usuario usuario = _bdUsuarios.GetUsuario(nomeUsuario);
		if (!usuario.VerificarSeLivroEstaEmprestado(nomeLivro))
		{
			throw new Exception("Livro nao esta nas maos do usuario");
		}
		_bdLivros.DevolverLivro(nomeLivro);
		usuario.RemoverLivro(nomeLivro);
	}
	
	public void RegistrarLivroComoExtraviado(
		String nome
		) throws Exception
	{
		Livro livro = _bdLivros.GetLivro(nome);
		if (livro.GetExtraviado())
		{
			throw new Exception("Livro ja estava extraviado");
		}
		livro.SetExtraviado(true);
	}
	
	public void RegistrarLivroComoNaoExtraviado(
		String nome
		) throws Exception
	{
		Livro livro = _bdLivros.GetLivro(nome);
		if (!livro.GetExtraviado())
		{
			throw new Exception("Livro nao estava extraviado");
		}
		livro.SetExtraviado(false);
	}
	
	public String GetEstadoLivro(
		String nome
		) throws Exception
	{
		Livro livro = _bdLivros.GetLivro(nome);
		if (livro.GetExtraviado())
		{
			return "Extraviado";
		}
		else if (livro.GetEmprestado())
		{
			return "Emprestado";
		}
		else
		{
			return "Disponivel";
		}
	}
	
	public String GetEstadoUsuario(
		String nome,
		String dataAtual
		) throws Exception
	{
		if (EstaBloqueadoPorCobranca(nome))
		{
			return "Bloqueado por cobranca";
		}
		else if (EstaBloqueadoPorTempo(nome, dataAtual))
		{
			return "Bloqueado por tempo";
		}
		else
		{
			return "Desbloqueado";
		}
	}
	
	public List<Livro> GetListaLivros(
		String nome
		) throws Exception
	{
		Set<String> livrosEmprestados = _bdUsuarios.GetUsuario(nome).GetLivrosEmprestados();
		List<Livro> livros = new ArrayList<Livro>();
		Livro livro;
		
		for(String nomeLivro : livrosEmprestados)
		{
			livro = _bdLivros.GetLivro(nomeLivro).Clonar();
			livros.add(livro);
		}
		
		return livros;
	}
}
