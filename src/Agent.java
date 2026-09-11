/**
 * Contrato comum a todos os agentes da simulação.
 *
 * Cada agente possui seu próprio estado e seus próprios atributos. A cada tick
 * da simulação, o método {@link #tick()} é chamado exatamente uma vez para o
 * agente: ele atualiza os atributos, imprime o estado/valores atuais e, por
 * fim, aplica a lógica de transição de estados.
 */
public interface Agent {

    /** Nome do agente, usado para identificar as mensagens no console. */
    String nome();

    /** Executa um passo (tick) da simulação para este agente. */
    void tick();
}
