package p1ces29.LUCASFRANCA_QUESTAO8;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Livro {
	private String _nome;
	private boolean _emprestado;
	private boolean _extraviado;
	private String _nomeDono;
	private String _dataLimite;
	private static final SimpleDateFormat _format = new SimpleDateFormat("dd/MM/yy");
	
	public Livro(
		String nome
		)
	{
		_nome = nome;
		_emprestado = false;
		_nomeDono = null;
		_extraviado = false;
		_dataLimite = null;
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
	
	public void SetDataLimite(
		String dataLimite
		)
	{
		_dataLimite = dataLimite;
	}
	
	public boolean GetVencido(
		String dataAtual
		) throws Exception
	{
		Date limite = _format.parse(_dataLimite);
		Date atual = _format.parse(dataAtual);
		return limite.before(atual);
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
	
	public Livro Clonar()
	{
		Livro livro = new Livro(_nome);
		livro.SetDataLimite(_dataLimite);
		livro.SetEmprestado(_emprestado);
		livro.SetExtraviado(_extraviado);
		livro.SetNomeDono(_nomeDono);
		return livro;
	}
}
