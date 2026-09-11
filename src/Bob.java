/**
 * Máquina de estados finita (FSM) do Bob, o dono de casa.
 *
 * Estados: COOKING, CLEANING.
 * Atributos: cookingProgress (progresso da comida) e mess (bagunça),
 *            ambos começam em 0 e nunca ficam negativos.
 * Estado inicial: COOKING.
 *
 * Bob é totalmente independente do Juca: seus atributos não controlam o Juca
 * e uma transição do Bob não altera o estado do Juca (e vice-versa).
 */
public class Bob implements Agent {

    /** Estados possíveis do Bob. */
    private enum State { COOKING, CLEANING }

    private State state = State.COOKING;
    private int cookingProgress = 0;
    private int mess = 0;

    /** Estado inicial COOKING: anuncia a fala de entrada uma única vez. */
    public Bob() {
        fala("Hora de preparar uma refeição!");
    }

    @Override
    public String nome() {
        return "Bob";
    }

    @Override
    public void tick() {
        atualizar();      // 1
        aplicarLimites(); // 2
        imprimirEstado(); // 3
        transicionar();   // 4
    }

    // 1) Regras de atualização por estado.
    private void atualizar() {
        switch (state) {
            case COOKING  -> { cookingProgress += 3; mess += 2; }
            case CLEANING -> { mess -= 4; }
        }
    }

    // 2) Limites inferiores: cookingProgress e mess nunca ficam negativos.
    private void aplicarLimites() {
        cookingProgress = Math.max(cookingProgress, 0);
        mess            = Math.max(mess, 0);
    }

    // 3) Saída de console do tick.
    private void imprimirEstado() {
        System.out.println("[Bob] " + rotulo());
        System.out.println("[Bob] Progresso da comida: " + cookingProgress);
        System.out.println("[Bob] Bagunça: " + mess);
    }

    private String rotulo() {
        return switch (state) {
            case COOKING  -> "Cozinhando...";
            case CLEANING -> "Limpando...";
        };
    }

    // 4) Lógica de transição (executada após imprimir estado/valores).
    private void transicionar() {
        switch (state) {
            case COOKING  -> transicaoCooking();
            case CLEANING -> transicaoCleaning();
        }
    }

    /**
     * Permanece cozinhando enquanto cookingProgress < 12.
     * Quando cookingProgress >= 12: ajusta para 12, sai de COOKING e entra
     * em CLEANING (fala de saída antes da fala de entrada).
     */
    private void transicaoCooking() {
        if (cookingProgress >= 12) {
            cookingProgress = 12;
            fala("A comida está pronta!");              // saída de COOKING
            state = State.CLEANING;
            fala("Agora preciso limpar esta bagunça..."); // entrada em CLEANING
        }
    }

    /**
     * Permanece limpando enquanto mess > 0.
     * Quando mess <= 0: ajusta para 0, sai de CLEANING, reinicia o preparo
     * (cookingProgress = 0) e volta para COOKING.
     */
    private void transicaoCleaning() {
        if (mess <= 0) {
            mess = 0;
            fala("Tudo limpo!");                    // saída de CLEANING
            cookingProgress = 0;                    // reinicia próxima refeição
            state = State.COOKING;
            fala("Hora de preparar uma refeição!"); // entrada em COOKING
        }
    }

    private void fala(String frase) {
        System.out.println("[Bob] " + frase);
    }
}
