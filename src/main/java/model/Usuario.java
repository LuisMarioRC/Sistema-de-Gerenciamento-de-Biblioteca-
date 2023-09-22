package model;

public class Usuario extends Operador{

    private String endereco;
    private Integer telefone;
    private Integer multa;




    public Usuario(String nome,String endereco,Integer telefone){
        super(nome);
        this.endereco=endereco;
        this.telefone=telefone;
        this.multa=0;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
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

}
