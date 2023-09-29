package model;

import dao.DAO;
import dao.excecoes.UsuarioException;

import java.time.LocalDate;

public class Usuario extends Operador{

    private String endereco;
    private String telefone;
    private LocalDate fimDaMulta;



    public Usuario(String nome,String endereco,String telefone){
        super(nome);
        this.endereco=endereco;
        this.telefone=telefone;
        this.fimDaMulta = null;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "nome=" + super.getNome() + '\''+
                ",endereco=" + endereco + '\'' +
                ", telefone=" + telefone + '\'' +
                ", numeroDeIdentificacao=" + super.getNumeroDeIdentificacao()+ '\''+
                ",status=" +super.getStatus()+ '\''+
                ", fimDaMulta= "+ fimDaMulta+
                '}';
    }

    public LocalDate getFimDaMulta() {
        return fimDaMulta;
    }

    public void setFimDaMulta(LocalDate fimDaMulta) {
        this.fimDaMulta = fimDaMulta;
    }
}
