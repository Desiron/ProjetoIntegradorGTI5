package persistencia;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.FormaPagto;

public class FormaPgtoDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static void inserir(FormaPagto fomaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(fomaPgto);
		t.commit();
		sessao.close();
	}
	
	public static void alterar(FormaPagto fomaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.update(fomaPgto);
		t.commit();
		sessao.close();
	}

	public static void excluir(FormaPagto fomaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(fomaPgto);;
		t.commit();
		sessao.close();
	}
	
	public static List<FormaPagto> listagem (String filtro) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		List<FormaPagto> lista = null;
		if(filtro.trim().length() == 0) {
			consulta = sessao.createQuery("from FormaPagto order by fpg_descricao");
		}else {
			consulta = sessao.createQuery("from FormaPagto where fpg_descricao like :parametro order by fpg_descricao");
			consulta.setString("parametro","%"+ filtro + "%");
		}
		lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static FormaPagto pesqDescricao(String valor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta = sessao.createQuery("from FormaPagto where fpg_descricao= :parametro");
		consulta.setString("parametro", valor);
		FormaPagto forma = (FormaPagto) consulta.uniqueResult();
		sessao.close();
		return forma;
	}
	
	
}
