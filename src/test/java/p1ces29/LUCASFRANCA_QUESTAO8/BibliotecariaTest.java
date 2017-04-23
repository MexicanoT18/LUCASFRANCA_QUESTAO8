package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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
	public void TestarBloquearDesbloquearUsuarioSucesso()
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
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(false);
			when(usuario.GetBloqueado()).thenReturn(false);
			when(usuario.GetDataBloqueio()).thenReturn(dataBloqueio);
			
			bibliotecaria.RegistrarUsuario(nome);
			when(_bdUsuario.GetUsuario(nome)).thenReturn(usuario);
			when(_bdUsuario.ExisteUsuario(nome)).thenReturn(true);
			
			bibliotecaria.BloquearUsuario(nome, dataBloqueio);
			when(usuario.GetBloqueado()).thenReturn(true);
			when(usuario.GetDataBloqueio()).thenReturn(dataBloqueio);
			
			assertFalse(bibliotecaria.PodeSerDesbloqueado(nome, dataAntes));
			assertTrue(bibliotecaria.PodeSerDesbloqueado(nome, dataDepois));
			
			bibliotecaria.DesbloquearUsuario(nome, dataDepois);
			when(usuario.GetBloqueado()).thenReturn(false);
			when(usuario.GetDataBloqueio()).thenReturn(null);
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
		Usuario usuario = new Usuario(nomeUsuario);
		
		try
		{
			bibliotecaria.RegistrarUsuario(nomeUsuario);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			assertTrue(bibliotecaria.LivroDisponivel(nomeLivro));
			assertFalse(usuario.VerificarSeLivroEstaEmprestado(nomeLivro));
			
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro);
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
		Usuario usuario = new Usuario(nomeUsuario);
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(false);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro);
			fail("Livro nao deveria estar disponivel");
		}
		catch (Exception e)
		{
			assertEquals("Livro ja emprestado", e.getMessage());
		}
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			usuario.SetBloqueado(true);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(true);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro);
			fail("Usuario deveria estar bloqueado");
		}
		catch (Exception e)
		{
			assertEquals("Usuario bloqueado", e.getMessage());
		}
		
		try
		{
			when(_bdUsuario.GetUsuario(nomeUsuario)).thenReturn(usuario);
			usuario.SetBloqueado(false);
			when(_bdUsuario.EstaBloqueado(nomeUsuario)).thenReturn(false);
			usuario.EmprestarLivro(nomeLivro);
			when(_bdLivros.EstaDisponivel(nomeLivro)).thenReturn(true);
			bibliotecaria.EmprestarLivro(nomeUsuario, nomeLivro);
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

}
