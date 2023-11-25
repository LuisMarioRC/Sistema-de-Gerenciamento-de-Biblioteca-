module Sistema.de.Gerenciamento.de.Biblioteca {

    /**
     * Permite o acesso aos pacotes para os testes de unidade pelo TestSuite
     */
    opens org.example.dao.administrador;
    opens org.example.dao.bibliotecario;
    opens org.example.dao.emprestimos;
    opens org.example.dao.livro;
    opens org.example.dao.reserva;
    opens org.example.dao.usuario;
    opens org.example.model;

}