package ecomicsmanager;

// Class: responsible for renaming comic book page files into the correct sequential order.
// For example: renaming "page3.jpg", "page1.jpg", "page2.jpg" → "001.jpg", "002.jpg", "003.jpg".
public class RenamePages {

    // Instance field: the folder where the page files to be renamed are located.
    // NOTE: should be 'private final' since it is only assigned once in the constructor.
    private String rootFolder;

    // Constructor: receives the root folder path and stores it for use in execute().
    public RenamePages(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    // 'execute' is a common method name for classes that perform a single main action.
    // NOTE: stub method — real implementation will list files in rootFolder, sort them,
    // and rename them sequentially using File.renameTo() or Files.move() from java.nio.file.
    public void execute() {
        System.out.println("Renaming pages in the right order in " + rootFolder);
    }
}
