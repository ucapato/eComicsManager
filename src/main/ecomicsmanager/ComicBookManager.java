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

    // Entry point: Java always starts execution from a method named main(String[] args).
    // args[] is an array of strings passed from the command line when running the program.
    public static void main(String[] args) {
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
        // Scanner reads user input from System.in (the keyboard).
        // NOTE: should be used with try-with-resources to ensure it is always closed, even if an exception occurs.
        Scanner scanner = new Scanner(System.in);

        // Infinite loop: keeps showing the menu until the user chooses to exit (case 0).
        while (true) {
            System.out.println("\n=== Comic Book Manager ==="); // \n adds a blank line before the menu.
            System.out.println("Root Directory: " + ROOT_FOLDER);
            System.out.println("1. Rename pages in the right order");
            System.out.println("2. ZIP a folder");
            System.out.println("3. Unzip a file to a folder");
            System.out.println("4. Rename .zip to .cbz");
            System.out.println("5. Rename .cbz to .zip");
            System.out.println("6. RAR a folder");
            System.out.println("7. Unrar a file to a folder");
            System.out.println("8. Rename .rar to .cbr");
            System.out.println("9. Rename .cbr to .rar");
            System.out.println("0. Exit");
            System.out.print("Choose an option: "); // print (no 'ln') keeps the cursor on the same line.

            // NOTE: nextInt() will throw InputMismatchException if the user types a non-integer.
            // A safer approach is to use nextLine() + Integer.parseInt() wrapped in a try/catch.
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumes the leftover newline character after the number.

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
                case 6 -> new RarHandler(ROOT_FOLDER).rarFolder();
                case 7 -> new RarHandler(ROOT_FOLDER).unrarFile();
                case 8 -> new FileRenamer(ROOT_FOLDER).renameRarToCbr();
                case 9 -> new FileRenamer(ROOT_FOLDER).renameCbrToRar();
                case 0 -> {
                    // Block arrow case: use curly braces when you need multiple statements.
                    System.out.println("Exiting... Goodbye!");
                    scanner.close(); // Releases the Scanner resource.
                    return; // Exits the method (and the loop), ending the program gracefully.
                }
                default -> System.out.println("Invalid option! Please try again."); // Catches any unrecognized input.
            }
        }
    }
}
