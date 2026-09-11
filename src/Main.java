import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Ponto de entrada da simulação.
 *
 * A cada tick, TODOS os agentes são atualizados exatamente uma vez, na ordem:
 * primeiro Juca, depois Bob. As mensagens de cada agente são prefixadas com
 * [Juca] / [Bob] para que fique claro a qual agente cada linha pertence.
 *
 * Número de ticks: pode ser passado como argumento de linha de comando.
 *   Ex.: java -cp out Main 40
 * Se nenhum argumento válido for informado, usa o padrão de 30 ticks.
 */
public class Main {

    private static final int TICKS_PADRAO = 30;

    public static void main(String[] args) {
        // Garante acentuação correta no console em qualquer sistema operacional.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        int ticks = lerTicks(args);

        // Cada agente tem seu próprio estado e seus próprios atributos.
        List<Agent> agentes = List.of(new Juca(), new Bob());

        System.out.println();
        for (int t = 1; t <= ticks; t++) {
            System.out.println("========== Tick " + t + " ==========");
            // Em cada tick, cada agente é atualizado exatamente uma vez.
            for (Agent agente : agentes) {
                agente.tick();
            }
            System.out.println();
        }
    }

    private static int lerTicks(String[] args) {
        if (args.length > 0) {
            try {
                int n = Integer.parseInt(args[0]);
                if (n > 0) {
                    return n;
                }
            } catch (NumberFormatException ignored) {
                // valor inválido -> cai no padrão
            }
        }
        return TICKS_PADRAO;
    }
}
