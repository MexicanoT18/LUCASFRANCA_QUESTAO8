package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.TreeSet;


public class BibliotecariaTest {

	BDUsuarios _bdUsuario;
	BDLivros _bdLivros;
	
	@Before
	public void MockarBD()
	{
		_bdUsuario = mock(BDUsuarios.class);
		_bdLivros = mock(BDLivros.class);
	}

	@Test
	public void TestarRegistrarRemoverUsuarioSucesso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nome = "Lucas Franca";
		
		try
		{
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(false);
			bibliotecaria.RegistrarUsuario(nome);
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(true);
			bibliotecaria.RemoverUsuario(nome);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void TestarRegistrarRemoverUsuarioFracasso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nome = "Lucas Franca";
		
		try
		{
			Mockito.doThrow(new Exception("Usuario ja cadastrado")).when(_bdUsuario).CadastrarUsuario(nome);
			bibliotecaria.RegistrarUsuario(nome);
			fail("Usuario nao deveria ser cadastrado duas vezes");
		}
		catch (Exception e)
		{
			assertEquals("Usuario ja cadastrado", e.getMessage());
		}
		
		try
		{
			Mockito.doThrow(new Exception("Usuario inexistente")).when(_bdUsuario).RemoverUsuario(nome);
			bibliotecaria.RemoverUsuario(nome);
			fail("Usuario nao deveria ser removido duas vezes");
		}
		catch (Exception e)
		{
			assertEquals("Usuario inexistente", e.getMessage());
		}
	}

	@Test
	public void TestarBloquearDesbloquearCobrancaUsuarioSucesso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nome = "Lucas Franca";
		Usuario usuario = mock(Usuario.class);
		when(usuario.GetNome()).thenReturn(nome);
		String dataBloqueio = "22/04/2017";
		String dataAntes = "21/04/2017";
		String dataDepois = "23/04/2017";
		
		try
		{
			when(_bdUsuario.GetUsuario(nome)).thenReturn(usuario);
			when(usuario.GetLivrosEmprestados()).thenReturn(new TreeSet<String>());
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(false);
			when(usuario.GetBloqueadoCobranca()).thenReturn(false);
			
			bibliotecaria.RegistrarUsuario(nome);
			when(_bdUsuario.GetUsuario(nome)).thenReturn(usuario);
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(true);
			
			bibliotecaria.BloquearUsuarioCobranca(nome, dataBloqueio);
			when(usuario.GetBloqueadoCobranca()).thenReturn(true);
			assertTrue(bibliotecaria.EstaBloqueadoPorCobranca(nome));
			assertEquals("Bloqueado por cobranca", bibliotecaria.GetEstadoUsuario(nome, dataBloqueio));
			
			assertFalse(bibliotecaria.EstaBloqueadoPorTempo(nome, dataAntes));
			assertFalse(bibliotecaria.EstaBloqueadoPorTempo(nome, dataDepois));
			
			bibliotecaria.DesbloquearUsuario(nome, dataDepois);
			when(usuario.GetBloqueadoCobranca()).thenReturn(false);
			assertFalse(bibliotecaria.EstaBloqueadoPorCobranca(nome));
			assertEquals("Desbloqueado", bibliotecaria.GetEstadoUsuario(nome, dataBloqueio));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void TestarEmprestimoLivroSucesso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nomeUsuario = "Lucas Franca";
		String nomeLivro = "O Pequeno Principe";
		String dataLimite = "25/04/2017";
		String dataAtual = "22/04/2017";
		Usuario usuario = new Usuario(nomeUsuario);
		
		try
		{
			bibliotecaria.RegistrarUsuario(nomeUsuario);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			assertTrue(bibliotecaria.LivroDisponivel(nomeLivro));
			assertFalse(usuario.VerificarSeLivroEstaEmprestado(nomeLivro));
			
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro, dataLimite, dataAtual);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(false);
			assertFalse(bibliotecaria.LivroDisponivel(nomeLivro));
			assertTrue(usuario.VerificarSeLivroEstaEmprestado(nomeLivro));
			
			bibliotecaria.DevolverLivro(nomeUsuario, nomeLivro);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			assertTrue(bibliotecaria.LivroDisponivel(nomeLivro));
			assertFalse(usuario.VerificarSeLivroEstaEmprestado(nomeLivro));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void TestarEmprestimoLivroFracasso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nomeUsuario = "Lucas Franca";
		String nomeLivro = "O Pequeno Principe";
		String dataLimite = "25/04/2017";
		String dataAtual = "22/04/2017";
		Usuario usuario = new Usuario(nomeUsuario);
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(false);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro, dataLimite, dataAtual);
			fail("Livro nao deveria estar disponivel");
		}
		catch (Exception e)
		{
			assertEquals("Livro ja emprestado", e.getMessage());
		}
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			usuario.SetBloqueadoCobranca(true);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(true);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro, dataLimite, dataAtual);
			fail("Usuario deveria estar bloqueado");
		}
		catch (Exception e)
		{
			assertEquals("Usuario bloqueado", e.getMessage());
		}
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			usuario.SetBloqueadoCobranca(false);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			usuario.EmprestarLivro(nomeLivro);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro, dataLimite, dataAtual);
			fail("Usuario ja deveria ter o livro");
		}
		catch (Exception e)
		{
			assertEquals("Livro ja nas maos do usuario", e.getMessage());
			usuario.RemoverLivro(nomeLivro);
		}
		
		try
		{
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			usuario.EmprestarLivro(nomeLivro);
			bibliotecaria.DevolverLivro(nomeUsuario, nomeLivro);
			fail("Livro deveria ja estar na biblioteca");
		}
		catch (Exception e)
		{
			assertEquals("Livro ja esta na biblioteca", e.getMessage());
			usuario.RemoverLivro(nomeLivro);
		}
		
		try
		{
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(false);
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			bibliotecaria.DevolverLivro(nomeUsuario, nomeLivro);
			fail("Livro nao deveria estar com usuario");
		}
		catch (Exception e)
		{
			assertEquals("Livro nao esta nas maos do usuario", e.getMessage());
		}
	}
	
	@Test
	public void TestarEstadoLivro()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
		String nome = "O Pequeno Principe";
		Livro livro = new Livro(nome);
		
		try
		{
			when(_bdLivros.GetLivro(nome)).thenReturn(livro);
			
			bibliotecaria.RegistrarLivroComoExtraviado(nome);
			assertTrue(livro.GetExtraviado());
			assertEquals("Extraviado", bibliotecaria.GetEstadoLivro(nome));
			livro.SetEmprestado(true);
			assertEquals("Extraviado", bibliotecaria.GetEstadoLivro(nome));
			livro.SetEmprestado(false);
			assertEquals("Extraviado", bibliotecaria.GetEstadoLivro(nome));
			
			bibliotecaria.RegistrarLivroComoNaoExtraviado(nome);
			assertFalse(livro.GetExtraviado());
			assertEquals("Disponivel", bibliotecaria.GetEstadoLivro(nome));

			livro.SetEmprestado(true);
			assertEquals("Emprestado", bibliotecaria.GetEstadoLivro(nome));
			livro.SetEmprestado(false);
			assertEquals("Disponivel", bibliotecaria.GetEstadoLivro(nome));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void TestarBloqueioPorTempo()
	{
		String nomeLivro = "O Pequeno Principe";
		String nomeUsuario = "Lucas Franca";
		String dataBloqueio = "22/04/2017";
		
		Set<String> livrosEmprestados = new TreeSet<String>();
		livrosEmprestados.add(nomeLivro);
		Usuario usuario = mock(Usuario.class);
		when(usuario.GetLivrosEmprestados()).thenReturn(livrosEmprestados);
		when(usuario.GetNome()).thenReturn(nomeUsuario);
		when(usuario.GetBloqueadoCobranca()).thenReturn(false);
		
		try
		{
			Livro livro = mock(Livro.class);
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			when(_bdLivros.GetLivro(nomeLivro)).thenReturn(livro);
			when(livro.GetNome()).thenReturn(nomeLivro);

			Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario, _bdLivros);
			when(livro.GetVencido(dataBloqueio)).thenReturn(true);
			assertTrue(bibliotecaria.EstaBloqueadoPorTempo(nomeUsuario, dataBloqueio));
			assertEquals("Bloqueado por tempo", bibliotecaria.GetEstadoUsuario(nomeUsuario, dataBloqueio));
			
			when(livro.GetVencido(dataBloqueio)).thenReturn(false);
			assertFalse(bibliotecaria.EstaBloqueadoPorTempo(nomeUsuario, dataBloqueio));
			assertEquals("Desbloqueado", bibliotecaria.GetEstadoUsuario(nomeUsuario, dataBloqueio));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
