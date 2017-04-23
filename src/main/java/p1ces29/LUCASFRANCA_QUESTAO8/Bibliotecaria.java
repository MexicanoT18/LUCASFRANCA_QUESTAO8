package p1ces29.LUCASFRANCA_QUESTAO8;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Bibliotecaria {

	private BDUsuarios _bdUsuarios;
	
	public Bibliotecaria(
		BDUsuarios bdUsuarios
		)
	{
		_bdUsuarios = bdUsuarios;
	}
	
	public void RegistrarUsuario(
		String nome
		) throws Exception
	{
		_bdUsuarios.Cadastrar(nome);
	}
	
	public void RemoverUsuario(
		String nome
		) throws Exception
	{
		_bdUsuarios.RemoverUsuario(nome);
	}
	
	public void BloquearUsuario(
		String nome,
		String dataBloqueio
		) throws Exception
	{
		_bdUsuarios.BloquearUsuario(nome, dataBloqueio);
	}
	
	public boolean PodeSerDesbloqueado(
		String nome,
		String dataAtual
		) throws Exception
	{
		Usuario usuario = _bdUsuarios.GetUsuario(nome);
		if (!usuario.GetBloqueado() || usuario.GetDataBloqueio() == null)
		{
			return true;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date bloqueio = format.parse(usuario.GetDataBloqueio());
		Date atual = format.parse(dataAtual);
		return bloqueio.before(atual);
	}
	
	public void DesbloquearUsuario(
		String nome,
		String dataAtual
		) throws Exception
	{
		if (!PodeSerDesbloqueado(nome, dataAtual))
		{
			throw new Exception("Usuario nao pode ser desbloqueado");
		}
		_bdUsuarios.DesbloquearUsuario(nome);
	}
}
