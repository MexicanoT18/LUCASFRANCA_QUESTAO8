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
			assertTrue(bd.EstaDisponivel(nome));
			
			bd.GetLivro(nome).SetExtraviado(true);
			assertFalse(bd.EstaDisponivel(nome));
			
			bd.GetLivro(nome).SetExtraviado(false);
			assertTrue(bd.EstaDisponivel(nome));
			
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
			assertTrue(bd.EstaDisponivel(nome));
			assertFalse(livro.GetEmprestado());
			assertEquals(null, livro.GetNomeDono());
			
			bd.EmprestarLivro(nome, nomeDono);
			assertFalse(bd.EstaDisponivel(nome));
			assertTrue(livro.GetEmprestado());
			assertEquals(nomeDono, livro.GetNomeDono());
			
			bd.DevolverLivro(nome);
			assertTrue(bd.EstaDisponivel(nome));
			assertFalse(livro.GetEmprestado());
			assertEquals(null, livro.GetNomeDono());
			
			livro.SetExtraviado(true);
			assertFalse(bd.EstaDisponivel(nome));
			
			livro.SetExtraviado(false);
			assertTrue(bd.EstaDisponivel(nome));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
