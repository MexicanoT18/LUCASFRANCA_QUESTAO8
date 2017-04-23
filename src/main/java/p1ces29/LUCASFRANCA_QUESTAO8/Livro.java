package p1ces29.LUCASFRANCA_QUESTAO8;

public class Livro {
	private String _nome;
	private boolean _emprestado;
	private boolean _extraviado;
	private String _nomeDono;
	
	public Livro(
		String nome
		)
	{
		_nome = nome;
		_emprestado = false;
		_nomeDono = null;
		_extraviado = false;
	}
	
	public String GetNome()
	{
		return _nome;
	}
	
	public boolean GetEmprestado()
	{
		return _emprestado;
	}
	
	public boolean GetExtraviado()
	{
		return _extraviado;
	}
	
	public String GetNomeDono()
	{
		return _nomeDono;
	}
	
	public void SetEmprestado(
		boolean emprestado
		)
	{
		_emprestado = emprestado;
	}
	
	public void SetExtraviado(
		boolean extraviado
		)
	{
		_extraviado = extraviado;
	}
	
	public void SetNomeDono(
		String nomeDono
		)
	{
		_nomeDono = nomeDono;
	}
}
