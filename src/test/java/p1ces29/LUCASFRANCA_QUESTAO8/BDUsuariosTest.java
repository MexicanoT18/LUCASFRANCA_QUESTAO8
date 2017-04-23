package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Test;

public class BDUsuariosTest {

	@Test
	public void TestarInsercaoRemocaoUsuarioSucesso() {
		BDUsuarios bd = new BDUsuarios();
		String nome = "Lucas Franca";
		
		try
		{
			assertFalse(bd.ExisteUsuario(nome));
			bd.CadastrarUsuario(nome);
			assertTrue(bd.ExisteUsuario(nome));
			assertEquals(nome, bd.GetUsuario(nome).GetNome());
			bd.RemoverUsuario(nome);
			assertFalse(bd.ExisteUsuario(nome));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		
	}

	@Test
	public void TestarInsercaoRemocaoUsuarioFracasso() {
		BDUsuarios bd = new BDUsuarios();
		String nome = "Lucas Franca";
		
		try
		{
			bd.GetUsuario(nome);
			fail("Usuario nao deveria existir");
		}
		catch (Exception e)
		{
			assertEquals("Usuario inexistente", e.getMessage());
		}
		
		try
		{
			bd.RemoverUsuario(nome);
			fail("Usuario nao deveria existir");
		}
		catch (Exception e)
		{
			assertEquals("Usuario inexistente", e.getMessage());
		}
		
		try
		{
			bd.CadastrarUsuario(nome);
			bd.CadastrarUsuario(nome);
			fail("Usuario nao deveria ser inserido duas vezes");
		}
		catch (Exception e)
		{
			assertEquals("Usuario ja cadastrado", e.getMessage());
		}
	}

	@Test
	public void TestarBloqueioUsuariosSucesso() {
		BDUsuarios bd = new BDUsuarios();
		String nome = "Lucas Franca";
		String data = "22/04/2017";
		Usuario usuario;

		try
		{
			bd.CadastrarUsuario("Lucas Franca");
			usuario = bd.GetUsuario(nome);
			
			bd.BloquearUsuario(nome, data);
			assertTrue(usuario.GetBloqueado());
			assertEquals(data, usuario.GetDataBloqueio());
			
			bd.DesbloquearUsuario(nome);
			usuario = bd.GetUsuario(nome);
			assertFalse(usuario.GetBloqueado());
			assertEquals(null, usuario.GetDataBloqueio());
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
