package ecomicsmanager;

// Class: the single entry point of the application.
// In Java, the class containing main() is the "launcher" — it should do as little as possible,
// just start the app and delegate everything else to the appropriate class.
// Having one dedicated entry point makes it immediately clear where the program starts.
public class Main {

    // Entry point: Java always looks for this exact signature to start the program.
    // 'public' = accessible from anywhere, 'static' = belongs to the class (no object needed),
    // 'void' = returns nothing, 'String[] args' = command-line arguments passed at runtime.
    public static void main(String[] args) {
        // Delegation pattern: Main doesn't do any work itself — it hands off to ComicBookManager.
        // This keeps responsibilities separate: Main = start the app, ComicBookManager = run the app.
        // args is passed through so ComicBookManager can use command-line arguments if needed in the future.
        ComicBookManager.start(args);
    }
}
