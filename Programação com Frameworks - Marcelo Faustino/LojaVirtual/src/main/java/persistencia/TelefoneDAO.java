package persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.Fone;
import beans.Pessoa;
import beans.Produto;

public class TelefoneDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<Fone> listagem(Pessoa pes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		consulta = sessao.createQuery("from Fone where pes_id = :parametro order by pes_id");
		consulta.setInteger("parametro", pes.getId());
		List<Fone> lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static void excluirFone(Fone fone) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.delete(fone);
		t.commit();
		sessao.close();
	}	
}
