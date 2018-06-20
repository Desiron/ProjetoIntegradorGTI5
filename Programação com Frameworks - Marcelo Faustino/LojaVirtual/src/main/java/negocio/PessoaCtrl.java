package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import beans.Fone;
import beans.Pessoa;
import persistencia.PessoaDAO;
import persistencia.TelefoneDAO;

@ManagedBean
@SessionScoped
public class PessoaCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	private List<Pessoa> list = new ArrayList<Pessoa>();
	private String filtro = "";

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Pessoa> getList() {
		return PessoaDAO.listagem(filtro);
	}

	public void setList(List<Pessoa> list) {
		this.list = list;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String actionGravar() {
		if (pessoa.getId() == 0) {
			PessoaDAO.inserir(pessoa);
		} else {
			PessoaDAO.alterar(pessoa);
		}
		pessoa = new Pessoa();
		pessoa.setTipo("ROLE_CLIENTE");
		pessoa.setFones(new ArrayList<Fone>());
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		return "form_pessoa";
	}
	
	public String actionGravarCadastro() {
		PessoaDAO.inserir(pessoa);
		return "/publico/login.xhtml";
	}
	
	public String limpar() {
		pessoa = new Pessoa();
		pessoa.setTipo("ROLE_CLIENTE");
		pessoa.setFones(new ArrayList<Fone>());
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		return "form_pessoa";
	}

	public String actionInserirCadastro() {
		pessoa = new Pessoa();
		pessoa.setFones(new ArrayList<Fone>());
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		pessoa.setTipo("ROLE_CLIENTE");
		pessoa.getFones().add(fone);		 
		return "/publico/form_cliente.xhtml";
	}
	public String actionInserir() {
		pessoa = new Pessoa();
		pessoa.setFones(new ArrayList<Fone>());
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		pessoa.setTipo("ROLE_CLIENTE");
		pessoa.getFones().add(fone);
		return "form_pessoa";
	}

	public String actionExcluir(Pessoa pes) {
		PessoaDAO.excluir(pes);
		return "form_pessoa";
	}

	public String actionAlterar(Pessoa pes) {
		pessoa = pes;
		pessoa.setFones(TelefoneDAO.listagem(pes));
		return "form_pessoa";
	}

	public String actionInserirFone() {
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		pessoa.getFones().add(fone);
		return "form_pessoa";
	}

	public String actionExcluirFone(Fone f) {
		pessoa.getFones().remove(f);
		TelefoneDAO.excluirFone(f);
		return "form_pessoa";
	}	
	
	public static String usuarioLogado() {
		String usuario="";
		UserDetails user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		usuario = user.getUsername();
		return usuario;
	}
	
}
