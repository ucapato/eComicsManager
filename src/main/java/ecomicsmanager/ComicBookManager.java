package ecomicsmanager;

import java.io.File;
import java.util.Scanner;

// Class: groups related data and behavior together.
// This class is the "brain" of the app — it manages the menu and coordinates all operations.
public class ComicBookManager {

    // Constant: a fixed value that never changes (final = cannot be reassigned, static = belongs to the class, not an instance).
    // NOTE: hardcoded path — works for now but makes the app non-portable (won't work on other machines).
    // A better approach is to pass the path as a CLI argument or read it from a config file.
    private static final String ROOT_FOLDER = "C:\\gibis\\";

    // Static factory-style method: called by Main to bootstrap the application.
    // 'static' means it can be called without creating an instance first — Main.main() calls this directly.
    // Renamed from main() to start() to make clear this is not the entry point — Main.java is.
    public static void start(String[] args) {
        // Creating an object (instance) of this class using the constructor.
        // 'new' allocates memory and calls the constructor to initialize the object.
        ComicBookManager manager = new ComicBookManager();
        manager.initialize();
        manager.showMenu();
    }

    // Private method: only accessible within this class. Used to validate the root folder before starting.
    private void initialize() {
        // File: a Java class that represents a file or directory path on disk.
        File root = new File(ROOT_FOLDER);

        // .exists() checks if the path actually exists on the filesystem.
        if (!root.exists()) {
            System.out.println("Error: Root folder " + ROOT_FOLDER + " does not exist.");
            System.exit(1); // Exits the program with error code 1 (non-zero = something went wrong).
        }
    }

    private void showMenu() {
        // try-with-resources: automatically closes Scanner when the block ends, even if an exception occurs.
        // Scanner reads user input from System.in (the keyboard).
        try (Scanner scanner = new Scanner(System.in)) {

        // Infinite loop: keeps showing the menu until the user chooses to exit (case 0).
        while (true) {
            System.out.println("\n=== Comic Book Manager ==="); // \n adds a blank line before the menu.
            System.out.println("Root Directory: " + ROOT_FOLDER);
            System.out.println("1. Rename pages in the right order");
            System.out.println("2. ZIP a folder");
            System.out.println("3. Unzip a file to a folder");
            System.out.println("4. Rename .zip to .cbz");
            System.out.println("5. Rename .cbz to .zip");
            System.out.println("6. Unrar a file to a folder");
            System.out.println("7. Rename .rar to .cbr");
            System.out.println("8. Rename .cbr to .rar");
            System.out.println("0. Exit");
            System.out.print("Choose an option: "); // print (no 'ln') keeps the cursor on the same line.

            // nextLine() reads the whole input as a String — never throws on unexpected input.
            String input = scanner.nextLine();
            int choice;
            try {
                // Integer.parseInt() converts the String to an integer.
                // Throws NumberFormatException if the String is not a valid number.
                choice = Integer.parseInt(input.trim()); // trim() removes any accidental leading/trailing spaces.
            } catch (NumberFormatException e) {
                // Instead of crashing, we print a friendly message and loop back to show the menu again.
                System.out.println("Invalid input! Please enter a number.");
                continue; // 'continue' skips the rest of this iteration and goes back to the top of the while loop.
            }

            // Switch expression (Java 14+): cleaner alternative to if/else chains.
            // Each 'case' matches a value of 'choice' and runs the corresponding action.
            switch (choice) {
                // Arrow syntax (->): executes the right side directly, no 'break' needed.
                // Each handler class is instantiated fresh per action, receiving the ROOT_FOLDER.
                case 1 -> new RenamePages(ROOT_FOLDER).execute();
                case 2 -> new ZipHandler(ROOT_FOLDER).zipFolder();
                case 3 -> new ZipHandler(ROOT_FOLDER).unzipFile();
                case 4 -> new FileRenamer(ROOT_FOLDER).renameZipToCbz();
                case 5 -> new FileRenamer(ROOT_FOLDER).renameCbzToZip();
                case 6 -> new RarHandler(ROOT_FOLDER).unrarFile();
                case 7 -> new FileRenamer(ROOT_FOLDER).renameRarToCbr();
                case 8 -> new FileRenamer(ROOT_FOLDER).renameCbrToRar();
                case 0 -> {
                    // Block arrow case: use curly braces when you need multiple statements.
                    System.out.println("Exiting... Goodbye!");
                    return; // Exits the method (and the loop), ending the program gracefully.
                    // Scanner is closed automatically by try-with-resources — no need to call scanner.close() manually.
                }
                default -> System.out.println("Invalid option! Please try again."); // Catches any unrecognized input.
            }
        }
        } // end try-with-resources — Scanner is closed here automatically.
    }
}
