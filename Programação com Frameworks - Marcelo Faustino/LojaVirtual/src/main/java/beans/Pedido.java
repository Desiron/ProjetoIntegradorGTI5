package beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pe_id")
	private int id;
	
	@Column(name= "pe_valor")
	private double valor;
	
	@Column(name= "pe_data")
	private Date data;
	
	@Column(name= "pe_descricao")
	private String descricao;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL )
	private List<ItemPedido> itensPedidos;	
	
	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}

	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}

	@ManyToOne
	@JoinColumn(name="pes_id")
	private Pessoa pessoa;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@ManyToOne
	@JoinColumn(name = "fpg_id")
	private FormaPagto formaPagamento;

	public FormaPagto getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagto formaPagamento) {
		this.formaPagamento = formaPagamento;
	}	
	
}
