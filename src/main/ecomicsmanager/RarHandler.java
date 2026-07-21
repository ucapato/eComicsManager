package ecomicsmanager;

// Class: responsible for RAR-related operations (compressing and extracting .rar files).
// Keeping this separate from ZipHandler is good practice — each class handles one archive format.
public class RarHandler {

    // Instance field: holds the working directory for all RAR operations.
    // NOTE: should be 'private final' since it is only assigned in the constructor and never changed.
    private String rootFolder;

    // Constructor: initializes the RarHandler with the folder it will operate on.
    public RarHandler(String rootFolder) {
        this.rootFolder = rootFolder; // 'this' distinguishes the field from the constructor parameter.
    }

    // NOTE: stub method — real implementation will invoke a RAR library or system command to compress a folder.
    // In Java, you can run external processes using ProcessBuilder or Runtime.exec().
    public void rarFolder() {
        System.out.println("RAR-ing a folder in " + rootFolder);
    }

    // NOTE: stub method — real implementation will extract a .rar archive into a target folder.
    public void unrarFile() {
        System.out.println("Unraring a file to a folder in " + rootFolder);
    }
}
