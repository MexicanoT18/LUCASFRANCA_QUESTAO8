package p1ces29.LUCASFRANCA_QUESTAO8;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public void BloquearUsuario(
		String nome,
		String dataBloqueio
		) throws Exception
	{
		_bdUsuarios.BloquearUsuario(nome, dataBloqueio);
	}
	
	public boolean PodeSerDesbloqueado(
		String nome,
		String dataAtual
		) throws Exception
	{
		Usuario usuario = _bdUsuarios.GetUsuario(nome);
		if (!usuario.GetBloqueado() || usuario.GetDataBloqueio() == null)
		{
			return true;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date bloqueio = format.parse(usuario.GetDataBloqueio());
		Date atual = format.parse(dataAtual);
		return bloqueio.before(atual);
	}
	
	public boolean VerificarUsuarioBloqueado(
		String nome
		) throws Exception
	{
		return _bdUsuarios.EstaBloqueado(nome);
	}
	
	public void DesbloquearUsuario(
		String nome,
		String dataAtual
		) throws Exception
	{
		if (!PodeSerDesbloqueado(nome, dataAtual))
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
		String nomeLivro
		) throws Exception
	{
		if (!LivroDisponivel(nomeLivro))
		{
			throw new Exception("Livro ja emprestado");
		}
		if (VerificarUsuarioBloqueado(nomeUsuario))
		{
			throw new Exception("Usuario bloqueado");
		}
		Usuario usuario = _bdUsuarios.GetUsuario(nomeUsuario);
		if (usuario.VerificarSeLivroEstaEmprestado(nomeLivro))
		{
			throw new Exception("Livro ja nas maos do usuario");
		}
		usuario.EmprestarLivro(nomeLivro);
		_bdLivros.EmprestarLivro(nomeLivro, nomeUsuario);
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
}
