package model;

import dao.DAO;
import dao.excecoes.UsuarioException;

public class Usuario extends Operador{

    private String endereco;
    private String telefone;
    private Integer multa;
    private Integer numEmprestimos;



    public Usuario(String nome,String endereco,String telefone){
        super(nome);
        this.endereco=endereco;
        this.telefone=telefone;
        this.multa=0;
        this.numEmprestimos=0;
    }

    public Usuario bloquearConta(Usuario usuario) throws UsuarioException{
        usuario.setStatus(false);// Muda o status da conta que deseja bloquear para false;
        DAO.getUsuarioDAO().atualizar(usuario);
        return usuario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public Integer getMulta() {
        return multa;
    }

    public void setMulta(Integer multa) {
        this.multa = multa;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome=" + super.getNome() + '\''+
                ",endereco=" + endereco + '\'' +
                ", telefone=" + telefone +
                ", multa=" + multa + '\''+
                ", numeroDeIdentificacao=" + super.getNumeroDeIdentificacao()+ '\''+
                ",status=" +super.getStatus()+
                '}';
    }

    public Integer getNumEmprestimos() {
        return numEmprestimos;
    }

    public void setNumEmprestimos(Integer numEmprestimos) {
        this.numEmprestimos = numEmprestimos;
    }
}
