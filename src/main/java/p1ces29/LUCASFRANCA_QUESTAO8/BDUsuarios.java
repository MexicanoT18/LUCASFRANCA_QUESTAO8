package p1ces29.LUCASFRANCA_QUESTAO8;

import java.util.Map;
import java.util.TreeMap;

public class BDUsuarios {
	private Map<String, Usuario> _usuarios;
	
	public BDUsuarios()
	{
		_usuarios = new TreeMap<String, Usuario>();
	}
	
	public void CadastrarUsuario(
		String nome
		) throws Exception
	{
		if (ExisteUsuario(nome))
		{
			throw new Exception("Usuario ja cadastrado");
		}
		Usuario usuario = new Usuario(nome);
		_usuarios.put(nome, usuario);
	}
	
	public Usuario GetUsuario(
		String nome
		) throws Exception
	{
		if (!ExisteUsuario(nome))
		{
			throw new Exception("Usuario inexistente");
		}
		return _usuarios.get(nome);
	}
	
	public void RemoverUsuario(
		String nome
		) throws Exception
	{
		if (!ExisteUsuario(nome))
		{
			throw new Exception("Usuario inexistente");
		}
		_usuarios.remove(nome);
	}
	
	public boolean ExisteUsuario(
		String nome
		) throws Exception
	{
		return _usuarios.containsKey(nome);
	}
	
	public boolean EstaBloqueado(
		String nome
		) throws Exception
	{
		if (!ExisteUsuario(nome))
		{
			throw new Exception("Usuario inexistente");
		}
		return _usuarios.get(nome).GetBloqueadoCobranca();
	}
	
	public void BloquearUsuarioCobranca(
		String nome
		) throws Exception
	{
		GetUsuario(nome).SetBloqueadoCobranca(true);
	}
	
	public void DesbloquearUsuario(
		String nome
		) throws Exception
	{
		GetUsuario(nome).SetBloqueadoCobranca(false);
	}
}
