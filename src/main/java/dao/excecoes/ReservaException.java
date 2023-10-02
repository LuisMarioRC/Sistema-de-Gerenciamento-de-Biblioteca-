package dao.excecoes;

import model.Reserva;

public class ReservaException extends Exception{
    private Reserva reserva;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";
    public static final String RESERVADO = "Livro RESERVADO por outro usuário";
    public static final String LIMITE = "Limite de RESERVAS atingido";

    public ReservaException(String texto, Reserva reserva){
        super(texto);
        this.reserva = reserva;
    }

    public ReservaException(String texto){
        super(texto);
    }
}
