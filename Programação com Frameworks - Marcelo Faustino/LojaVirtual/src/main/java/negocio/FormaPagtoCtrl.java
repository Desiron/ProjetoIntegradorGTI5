package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

import beans.FormaPagto;
import persistencia.FormaPgtoDAO;

@ManagedBean
@SessionScoped
public class FormaPagtoCtrl implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private FormaPagto formaPgto = new FormaPagto();
	private String filtro = "";
	private List<FormaPagto> listagem = new ArrayList<FormaPagto>();
	
	
	public List<FormaPagto> getListagem() {
		return FormaPgtoDAO.listagem(filtro);
	}
	public void setListagem(List<FormaPagto> listagem) {
		this.listagem = listagem;
	}	
	
	public FormaPagto getFormaPgto() {
		if(formaPgto==null) {
			formaPgto = new FormaPagto();
		}
		return formaPgto;
	}
	public void setFormaPgto(FormaPagto formaPgto) {
		this.formaPgto = formaPgto;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public String actionGravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(formaPgto.getId() == 0) {
			FormaPgtoDAO.inserir(formaPgto);
			context.addMessage(null, new FacesMessage("Sucesso", "Inserido com sucesso!"));
		}else {
			FormaPgtoDAO.alterar(formaPgto);
			context.addMessage(null, new FacesMessage("Sucesso", "Alterado com sucesso!"));
		}
	return "/admin/lista_formaPgto";
	}
	
	public String actionInserir() {
		formaPgto = new FormaPagto();
		return "/admin/lista_formaPgto";
	}	
	public String actionExcluir() {
		FormaPgtoDAO.excluir(formaPgto);
		return "/admin/lista_formaPgto";
	}
	
	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Forma de Pagamento Selecionada", String.valueOf(((FormaPagto) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}	
}
