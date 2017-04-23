package p1ces29.LUCASFRANCA_QUESTAO8;

import static org.junit.Assert.*;

import org.junit.Test;

public class BDLivrosTest {

	@Test
	public void TestarInsercaoRemocaoLivroSucesso() {
		BDLivros bd = new BDLivros();
		String nome = "O Pequeno Principe";
		
		try
		{
			assertFalse(bd.ExisteLivro(nome));
			bd.AdicionarLivro(nome);
			assertTrue(bd.ExisteLivro(nome));
			assertEquals(nome, bd.GetLivro(nome).GetNome());
			bd.RemoverLivro(nome);
			assertFalse(bd.ExisteLivro(nome));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		
	}

	@Test
	public void TestarInsercaoRemocaoLivroFracasso() {
		BDLivros bd = new BDLivros();
		String nome = "O Pequeno Principe";
		
		try
		{
			bd.GetLivro(nome);
			fail("Livro nao deveria existir");
		}
		catch (Exception e)
		{
			assertEquals("Livro inexistente", e.getMessage());
		}
		
		try
		{
			bd.RemoverLivro(nome);
			fail("Livro nao deveria existir");
		}
		catch (Exception e)
		{
			assertEquals("Livro inexistente", e.getMessage());
		}
		
		try
		{
			bd.AdicionarLivro(nome);
			bd.AdicionarLivro(nome);
			fail("Livro nao deveria ser inserido duas vezes");
		}
		catch (Exception e)
		{
			assertEquals("Livro ja cadastrado", e.getMessage());
		}
	}
	
	@Test
	public void TestarEmprestarLivroSucesso()
	{
		BDLivros bd = new BDLivros();
		String nome = "O Pequeno Principe";
		String nomeDono = "Lucas Franca";
		Livro livro;
		
		try
		{
			bd.AdicionarLivro(nome);
			livro = bd.GetLivro(nome);
			assertFalse(bd.EstaEmprestado(nome));
			assertFalse(livro.GetEmprestado());
			assertEquals(null, livro.GetNomeDono());
			
			bd.EmprestarLivro(nome, nomeDono);
			assertTrue(bd.EstaEmprestado(nome));
			assertTrue(livro.GetEmprestado());
			assertEquals(nomeDono, livro.GetNomeDono());
			
			bd.DevolverLivro(nome);
			assertFalse(bd.EstaEmprestado(nome));
			assertFalse(livro.GetEmprestado());
			assertEquals(null, livro.GetNomeDono());
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
