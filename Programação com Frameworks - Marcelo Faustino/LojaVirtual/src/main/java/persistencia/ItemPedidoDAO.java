package persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.ItemPedido;
import beans.Pedido;

public class ItemPedidoDAO implements Serializable {
	
private static final long serialVersionUID = 1L;
	

	
	public static void excluir(Pedido pedido) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = (Transaction) sessao.beginTransaction();
		sessao.delete(pedido);
		t.commit();
		sessao.close();		
	}
	
	public static List<ItemPedido> listagem(Pedido pedido) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		if (pedido == null) {
			consulta = sessao.createQuery("from ItemPedido order by it_id");
		} else {
			consulta = sessao.createQuery("from ItemPedido where pe_id="+pedido.getId()+" order by it_id");
		}
		List<ItemPedido> lista = consulta.list();
		for(int i=0; i<lista.size(); i++) {
			System.out.println((i+1)+": Item da lista: Nome Produto=> " + lista.get(i).getProduto().getNome());
		}
		sessao.close();
		return lista;
	}

}
