module Sistema.de.Gerenciamento.de.Biblioteca {

    /**
     * Permite o acesso aos pacotes para os testes de unidade pelo TestSuite
     */
    opens dao.administrador;
    opens dao.bibliotecario;
    opens dao.emprestimos;
    opens dao.livro;
    opens dao.reserva;
    opens dao.usuario;
    opens model;

}