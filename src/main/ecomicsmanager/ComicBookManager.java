package ecomicsmanager;

import java.io.File;
import java.util.Scanner;

public class ComicBookManager {
    private static final String ROOT_FOLDER = "C:\\gibis\\";

    public static void main(String[] args) {
        ComicBookManager manager = new ComicBookManager();
        manager.initialize();
        manager.showMenu();
    }

    private void initialize() {
        File root = new File(ROOT_FOLDER);
        if (!root.exists()) {
            System.out.println("Error: Root folder " + ROOT_FOLDER + " does not exist.");
            System.exit(1);
        }
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Comic Book Manager ===");
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
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
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
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
