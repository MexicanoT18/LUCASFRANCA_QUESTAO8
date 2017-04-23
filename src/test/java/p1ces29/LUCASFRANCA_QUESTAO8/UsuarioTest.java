package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void TestarUsuarioSucesso()
	{
		Usuario usuario = new Usuario("Lucas");
		assertEquals("Lucas", usuario.GetNome());
		assertFalse(usuario.GetBloqueado());
		assertEquals(null, usuario.GetDataBloqueio());

		usuario.SetBloqueado(true);
		assertTrue(usuario.GetBloqueado());
		usuario.SetBloqueado(false);
		assertFalse(usuario.GetBloqueado());
		
		usuario.SetDataBloqueio("24/04/2017");
		assertEquals("24/04/2017", usuario.GetDataBloqueio());
	}

	@Test
	public void TestarUsuarioEmprestimoLivrosSucesso()
	{
		Usuario usuario = new Usuario("Lucas");
		assertFalse(usuario.VerificarSeLivroEstaEmprestado("O Pequeno Principe"));
		usuario.EmprestarLivro("O Pequeno Principe");
		assertTrue(usuario.VerificarSeLivroEstaEmprestado("O Pequeno Principe"));
		usuario.RemoverLivro("O Pequeno Principe");
		assertFalse(usuario.VerificarSeLivroEstaEmprestado("O Pequeno Principe"));
	}
}
