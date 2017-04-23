package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Test;

public class LivroTest {

	@Test
	public void TestarLivroSucesso()
	{
		String dataBloqueio = "22/04/2017";
		String dataAntes = "21/04/2017";
		String dataDepois = "23/04/2017";
		String nome = "O Pequeno Principe";
		Livro livro = new Livro(nome);
		
		assertEquals(nome, livro.GetNome());
		assertFalse(livro.GetEmprestado());
		assertEquals(null, livro.GetNomeDono());

		livro.SetEmprestado(true);
		assertTrue(livro.GetEmprestado());
		livro.SetEmprestado(false);
		assertFalse(livro.GetEmprestado());
		
		livro.SetNomeDono("Lucas Franca");
		assertEquals("Lucas Franca", livro.GetNomeDono());
		
		try
		{
			livro.SetDataLimite(dataBloqueio);
			assertFalse(livro.GetVencido(dataAntes));
			assertTrue(livro.GetVencido(dataDepois));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
