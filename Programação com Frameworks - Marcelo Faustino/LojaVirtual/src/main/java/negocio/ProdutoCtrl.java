package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import beans.Produto;
import persistencia.ProdutoDAO;

@ManagedBean
@SessionScoped
public class ProdutoCtrl implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private String filtro = "";
	private Produto produto;
	private List<Produto> lista = new ArrayList<Produto>();	
	
	
	public List<Produto> getLista() {
		return ProdutoDAO.listagem(filtro);
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String actionGravar()  {
		if (produto.getId() == 0) {
			ProdutoDAO.inserir(produto);
		} else {
			ProdutoDAO.alterar(produto);
		}
		produto = new Produto();
		return "/admin/form_produto.xhtml";
	}

	public String actionInserir() {
		produto = new Produto();
		return "/admin/form_produto.xhtml";
	}

	public  String actionExcluir(Produto p) {
		ProdutoDAO.excluir(p);
		return "/admin/form_produto.xhtml";
	}

	public String actionAlterar(Produto p) {
		produto = p;
		return "/admin/form_produto.xhtml";
	}	
	
}
