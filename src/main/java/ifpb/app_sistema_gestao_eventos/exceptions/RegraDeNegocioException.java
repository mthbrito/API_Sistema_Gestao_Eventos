package ifpb.app_sistema_gestao_eventos.exceptions;

public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}