package image.to.console;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class App {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        for (Ansi.Color bgCl : Ansi.Color.values()) {
            for (Ansi.Color fgCl : Ansi.Color.values()) {
                System.out.print(ansi().fg(fgCl).bg(bgCl).a(" ░▒▓█").reset());
            }
            System.out.println(bgCl);
        }

        //System.out.println(ansi().eraseScreen().fg(RED).a("Hello").cursorDown(2).fg(GREEN).a(" World").reset() );

        AnsiConsole.systemUninstall();
    }
}
