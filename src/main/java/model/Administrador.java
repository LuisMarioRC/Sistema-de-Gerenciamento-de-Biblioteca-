package model;

public class Administrador extends Bibliotecario{

    public Administrador(){

    }

    private void bloquearConta(Operador conta){
        conta.setStatus(false);   // Muda o status da conta que deseja bloquear para false;
    }

    public Operador cadastrarOperadores(String nome, String cargo, Integer senha,Integer numeroDeIdentificacao){
        Operador b= new Operador();
        b.setNome(nome);
        b.setCargo(cargo);
        b.setSenha(senha);
        b.setNumeroDeIdentificacao(numeroDeIdentificacao);
        b.setStatus(true);
        return b;
    }

    public Usuario cadastrarUsuario(String nome, String endereco, Integer telefone, Integer numeroDeIdentificacao){
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEndereco(endereco);
        u.setTelefone(telefone);
        u.setNumeroDeIdentificacao(numeroDeIdentificacao);
        u.setStatus(true);
        return u;
    }

}
