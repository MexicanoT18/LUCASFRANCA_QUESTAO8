package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void TestarUsuarioSucesso()
	{
		Usuario usuario = new Usuario("Lucas");
		assertEquals("Lucas", usuario.GetNome());
		assertFalse(usuario.GetBloqueadoCobranca());

		usuario.SetBloqueadoCobranca(true);
		assertTrue(usuario.GetBloqueadoCobranca());
		usuario.SetBloqueadoCobranca(false);
		assertFalse(usuario.GetBloqueadoCobranca());
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
