package negocio;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import beans.FormaPagto;
import beans.ItemPedido;
import beans.Pedido;
import beans.Pessoa;
import beans.Produto;
import persistencia.FormaPgtoDAO;
import persistencia.ItemPedidoDAO;
import persistencia.PedidoDAO;
import persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class CarrinhoCtrl {
	FacesMessage mensagem = new FacesMessage("");
	private ItemPedido item = new ItemPedido();
	private Pedido pedido = new Pedido();
	private List<ItemPedido> listaPedido = new ArrayList<ItemPedido>();
	private double totalLista;
	private boolean listaVazia;
	private String descricaoPedido;
	private String formaSelecionada="";
	private List<String> form = new ArrayList<String>();
	private boolean verificarAdmin;
	private List<Pedido> listaPedidoLogado = new ArrayList<Pedido>();
	private List<ItemPedido> listaItensPedido = new ArrayList<ItemPedido>();
	private String nomeUserLog  = "";
	public static boolean finalizouCompra = false;
	
	
	public List<String> getForm() {
		List<FormaPagto> listaConsulForma = FormaPgtoDAO.listagem("");
		List<String> listForma = new ArrayList<String>();
		for (int i = 0; i < listaConsulForma.size(); i++) {
			listForma.add(listaConsulForma.get(i).getDescricao());
		}
		return listForma;
	}
	public void setForm(List<String> form) {
		this.form = form;
	}
	
	public void comprar(Produto prod) {			
		if (listaPedido.size() == 0) {
			item.setProduto(prod);
			listaPedido.add(item);
			mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Produto "+prod.getNome()+" adicionado no carrinho!",null);
		} else {
			item.setProduto(prod);
			if (verificarItemExiste(item)) {
				mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Adicionado mais 1 unidade de "+ prod.getNome(),null);
			} else {
				listaPedido.add(item);
				mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Produto "+prod.getNome()+" adicionado no carrinho!",null);
			}
		}
		item = new ItemPedido();
		FacesContext.getCurrentInstance().addMessage(null, mensagem);	
	}
	public String logarParaComprar() {
		finalizouCompra = true;
		return "/publico/login.xhtml";
	}
	
	public String finalizarPedido() {
		FormaPagto formaSelect = FormaPgtoDAO.pesqDescricao(formaSelecionada);
		Pessoa pessoa = PessoaDAO.pessoaQueFazPedido();
		pedido = new Pedido();
		for(int i=0; i<listaPedido.size(); i++) {
			listaPedido.get(i).setSubtotal(listaPedido.get(i).getProduto().getPreco()*listaPedido.get(i).getQtd());
			listaPedido.get(i).setValorUnitario(listaPedido.get(i).getProduto().getPreco());
			listaPedido.get(i).setPedido(pedido);
		}
		pedido.setItensPedidos(listaPedido);
		pedido.setValor(this.getTotalLista());
		pedido.setDescricao(this.descricaoPedido);
		pedido.setPessoa(pessoa);
		pedido.setFormaPagamento(formaSelect);
		pedido.setData(new Date(System.currentTimeMillis()));
		PedidoDAO.inserir(pedido);
		listaPedido = new ArrayList<ItemPedido>();
		mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Pedido Realizado com Sucesso!!",null);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		return "/cliente/listaPedido.xhtml";
	}
	
	public void excluirItemLista(ItemPedido item) {
		mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Item "+item.getProduto().getNome() +" excluído com sucesso!",null);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		listaPedido.remove(item);
	}
	
	public boolean verificarItemExiste(ItemPedido item) {
		for (int i = 0; i < listaPedido.size(); i++) {
			if (listaPedido.get(i).getProduto().getId() == item.getProduto().getId()) {				
				listaPedido.get(i).setQtd(listaPedido.get(i).getQtd()+1);
				return true;
			}
		}
		return false;		
	}

	public List<ItemPedido> getListaPedido() {
		for(int i=0; i<listaPedido.size();i++) {
			if(listaPedido.get(i).getQtd()==0) {
				excluirItemLista(listaPedido.get(i));
			}
		}
		return listaPedido;
	}

	public void setListaPedido(List<ItemPedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public double getTotalLista() {
		double soma=0;
		for (int i = 0; i < listaPedido.size(); i++) {
			soma = listaPedido.get(i).getSubtotal() + soma;
		}
		return soma;
	}

	public void setTotalLista(double totalLista) {
		this.totalLista = totalLista;
	}

	public boolean isListaVazia() {
		if(listaPedido.size()!=0) {
			return true;
		}else {
			return false;
		}
	}

	public void setListaVazia(boolean listaVazia) {
		this.listaVazia = listaVazia;
	}

	public String getDescricaoPedido() {
		return descricaoPedido;
	}

	public void setDescricaoPedido(String descricaoPedido) {
		this.descricaoPedido = descricaoPedido;
	}

	public String getFormaSelecionada() {
		return formaSelecionada;
	}

	public void setFormaSelecionada(String formaSelecionada) {
		this.formaSelecionada = formaSelecionada;
	}
	
	public void teste() {
		mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,formaSelecionada,null);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
	}
	public boolean isVerificarAdmin() {
		Pessoa pessoa = PessoaDAO.pessoaQueFazPedido();
		if(pessoa.getTipo().equals("ROLE_ADMINISTRADOR")) {
			return true;
		}else {
			return false;
		}
	}
	public void setVerificarAdmin(boolean verificarAdmin) {
		this.verificarAdmin = verificarAdmin;
	}
	public List<Pedido> getListaPedidoLogado() {
		List<Pedido> lisPedLog = PedidoDAO.listagem(PessoaDAO.pessoaQueFazPedido());
		return lisPedLog;
	}
	public void setListaPedidoLogado(List<Pedido> listaPedidoLogado) {
		this.listaPedidoLogado = listaPedidoLogado;
	}
	public List<ItemPedido> getListaItensPedido(Pedido pedido) {
		return pedido.getItensPedidos();
	}
	public void setListaItensPedido(List<ItemPedido> listaItensPedido) {
		this.listaItensPedido = listaItensPedido;
	}
	
	public List<ItemPedido> retornaIntensPedido(Pedido pedido){
		return ItemPedidoDAO.listagem(pedido);
	}
	public String getNomeUserLog() {
		return PessoaDAO.pessoaQueFazPedido().getNome();
	}
	public void setNomeUserLog(String nomeUserLog) {
		this.nomeUserLog = nomeUserLog;
	}
	
	
	
}



