package persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.Pedido;
import beans.Pessoa;

public class PedidoDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	public static void inserir(Pedido pedido) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.save(pedido);
		t.commit();
		sessao.close();
	}

	public static void alterar(Pedido pedido) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.update(pedido);
		t.commit();
		sessao.close();
	}

	public static void excluir(Pedido pedido) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.delete(pedido);
		t.commit();
		sessao.close();		
	}
	
	public static List<Pedido> listagem(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		if (pessoa == null) {
			consulta = sessao.createQuery("from Pedido order by pe_data");
		} else {
			consulta = sessao.createQuery("from Pedido where pes_id="+pessoa.getId()+" order by pe_data");
		}
		List<Pedido> lista = consulta.list();
		sessao.close();
		return lista;
	}
}
