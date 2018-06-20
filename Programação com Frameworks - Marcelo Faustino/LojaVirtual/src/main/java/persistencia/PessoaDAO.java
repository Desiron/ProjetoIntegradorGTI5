package persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.Pessoa;
import negocio.PessoaCtrl;

public class PessoaDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static void inserir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.save(pessoa);
		t.commit();
		sessao.close();
	}

	public static void alterar(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.update(pessoa);
		t.commit();
		sessao.close();
	}

	public static void excluir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.delete(pessoa);
		t.commit();
		sessao.close();		
	}
	
	public static List<Pessoa> listagem(String filtro) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		if (filtro.trim().length() == 0) {
			consulta = sessao.createQuery("from Pessoa order by pes_nome");
		} else {
			consulta = sessao.createQuery("from Pessoa where pes_nome like :parametro order by pes_nome");
			consulta.setString("parametro", "%" + filtro + "%");			
		}
		List<Pessoa> lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static Pessoa pessoaQueFazPedido() {
		String email = PessoaCtrl.usuarioLogado();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta = sessao.createQuery("from Pessoa where pes_email='"+email+"'");
		List<Pessoa> pesssoaLogada = consulta.list();
		Pessoa peLog = pesssoaLogada.get(0);				
		sessao.close();
		return peLog;
	}
}
