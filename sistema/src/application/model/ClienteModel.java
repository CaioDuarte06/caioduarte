package application.model;

public class ClienteModel {

	private int id;
	private String nome;
	private String cpf;
	private String cnpj;
	private String email;
	private String status;

	public ClienteModel() {
	}

	public ClienteModel(String nome, String cpf, String cnpj, String email, String status) {
		this.nome = nome;
		this.cpf = cpf;
		this.cnpj=cnpj;
		this.email = email;
		this.status = status;
	}

	// getters e setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
	    return nome;
	}
}