/**
 * Máquina de estados finita (FSM) do Juca.
 *
 * Estados: WORKING, EATING, SLEEPING.
 * Atributos: hunger (fome) e fatigue (cansaço), ambos começam em 0.
 * Estado inicial: WORKING.
 *
 * Ordem de cada tick:
 *   1) atualizar atributos conforme o estado atual;
 *   2) aplicar limites inferiores (nunca negativos);
 *   3) imprimir rótulo do estado + valores atuais;
 *   4) executar a lógica de transição (que pode emitir falas de saída/entrada).
 */
public class Juca implements Agent {

    /** Estados possíveis do Juca. */
    private enum State { WORKING, EATING, SLEEPING }

    private State state = State.WORKING;
    private int hunger = 0;
    private int fatigue = 0;

    /**
     * Ao ser criado, o Juca já está em WORKING (estado inicial), então
     * anunciamos a fala de entrada do estado inicial uma única vez.
     */
    public Juca() {
        fala("Hora de ir para o trabalho!");
    }

    @Override
    public String nome() {
        return "Juca";
    }

    @Override
    public void tick() {
        atualizar();     // 1
        aplicarLimites(); // 2
        imprimirEstado(); // 3
        transicionar();   // 4
    }

    // 1) Regras de atualização por estado.
    private void atualizar() {
        switch (state) {
            case WORKING  -> { hunger += 2; fatigue += 5;  }
            case EATING   -> { hunger -= 5;                 }
            case SLEEPING -> { hunger += 1; fatigue -= 10;  }
        }
    }

    // 2) Limites inferiores: hunger e fatigue nunca ficam negativos.
    private void aplicarLimites() {
        hunger  = Math.max(hunger, 0);
        fatigue = Math.max(fatigue, 0);
    }

    // 3) Saída de console do tick.
    private void imprimirEstado() {
        System.out.println("[Juca] " + rotulo());
        System.out.println("[Juca] Fome: " + hunger);
        System.out.println("[Juca] Cansaço: " + fatigue);
    }

    private String rotulo() {
        return switch (state) {
            case WORKING  -> "Trabalhando...";
            case EATING   -> "Comendo...";
            case SLEEPING -> "Dormindo...";
        };
    }

    // 4) Lógica de transição (executada após imprimir estado/valores).
    private void transicionar() {
        switch (state) {
            case WORKING  -> transicaoWorking();
            case EATING   -> transicaoEating();
            case SLEEPING -> transicaoSleeping();
        }
    }

    /**
     * Prioridade em WORKING: sono antes de fome.
     *   fatigue > 50 -> SLEEPING
     *   senão hunger > 10 -> EATING
     *   caso contrário permanece trabalhando.
     */
    private void transicaoWorking() {
        if (fatigue > 50) {
            state = State.SLEEPING;
            fala("Bateu um sono...");        // entrada em SLEEPING
        } else if (hunger > 10) {
            state = State.EATING;
            fala("Bateu uma fome...");       // entrada em EATING
        }
        // caso contrário: permanece em WORKING (sem fala)
    }

    /**
     * Permanece comendo até ficar satisfeito (hunger <= 0).
     * Ao ficar satisfeito: ajusta hunger = 0, sai de EATING e vai direto para
     * WORKING (por invariante de projeto, fatigue não é alterado em EATING).
     */
    private void transicaoEating() {
        if (hunger <= 0) {
            hunger = 0;
            fala("Ufa! Já estou cheio...");  // saída de EATING
            state = State.WORKING;
            fala("Hora de ir para o trabalho!"); // entrada em WORKING
        }
        // caso contrário: permanece em EATING
    }

    /**
     * Permanece dormindo até acordar (fatigue <= 0).
     * Ao acordar: ajusta fatigue = 0 e decide o próximo estado:
     *   hunger <= 10 -> WORKING
     *   hunger  > 10 -> EATING
     */
    private void transicaoSleeping() {
        if (fatigue <= 0) {
            fatigue = 0;
            if (hunger <= 10) {
                state = State.WORKING;
                fala("Hora de ir para o trabalho!"); // entrada em WORKING
            } else {
                state = State.EATING;
                fala("Bateu uma fome...");            // entrada em EATING
            }
        }
        // caso contrário: permanece em SLEEPING
    }

    private void fala(String frase) {
        System.out.println("[Juca] " + frase);
    }
}
