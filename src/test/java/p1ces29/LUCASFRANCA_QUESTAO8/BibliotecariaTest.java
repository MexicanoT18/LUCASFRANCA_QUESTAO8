package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BibliotecariaTest {
	
	BDUsuarios _bdUsuario;
	
	@Before
	public void MockarBD()
	{
		_bdUsuario = mock(BDUsuarios.class);
	}

	@Test
	public void TestarRegistrarRemoverUsuarioSucesso()
	{
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario);
		String nome = "Lucas Franca";
		
		try{
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
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario);
		String nome = "Lucas Franca";
		
		try{
			when(_bdUsuario.Cadastrar(nome)).thenThrow(new Exception("Usuario ja cadastrado"));
			bibliotecaria.RegistrarUsuario(nome);
			fail("Usuario nao deveria ser cadastrado duas vezes");
		}
		catch (Exception e)
		{
			assertEquals("Usuario ja cadastrado", e.getMessage());
		}
		
		try{
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
		Bibliotecaria bibliotecaria = new Bibliotecaria(_bdUsuario);
		String nome = "Lucas Franca";
		Usuario usuario = mock(Usuario.class);
		when(usuario.GetNome()).thenReturn(nome);
		String dataBloqueio = "22/04/2017";
		String dataAntes = "21/04/2017";
		String dataDepois = "23/04/2017";
		
		try{
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

}
