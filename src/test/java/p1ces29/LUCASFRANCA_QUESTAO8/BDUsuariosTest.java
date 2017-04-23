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
		Usuario usuario;

		try
		{
			bd.CadastrarUsuario("Lucas Franca");
			usuario = bd.GetUsuario(nome);
			
			bd.BloquearUsuarioCobranca(nome);
			assertTrue(usuario.GetBloqueadoCobranca());
			
			bd.DesbloquearUsuario(nome);
			usuario = bd.GetUsuario(nome);
			assertFalse(usuario.GetBloqueadoCobranca());
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
