package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_id")
	private int id;
	@Column(name = "it_qtd")
	private int qtd=1;
	@Column(name = "it_valorunit")
	private double valorUnitario;
	@Column(name = "it_subtotal")
	private double subtotal;

		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public double getValorUnitario() {		
		return this.produto.getPreco();
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public double getSubtotal() {
		return this.produto.getPreco()*qtd;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Produto produto;

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@ManyToOne
	@JoinColumn(name="pe_id")
	private Pedido pedido;


	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
