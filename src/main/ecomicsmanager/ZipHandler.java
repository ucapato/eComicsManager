package ecomicsmanager;

// Class: responsible for ZIP-related operations (compressing and extracting .zip files).
// Java has built-in support for ZIP via java.util.zip — no external library needed.
public class ZipHandler {

    // Instance field: the working directory for ZIP operations.
    // NOTE: should be 'private final' since it is only assigned in the constructor and never changed.
    private String rootFolder;

    // Constructor: sets the folder this handler will work with.
    public ZipHandler(String rootFolder) {
        this.rootFolder = rootFolder; // 'this.rootFolder' = the field; 'rootFolder' = the parameter.
    }

    // NOTE: stub method — real implementation will use java.util.zip.ZipOutputStream to compress
    // all files in rootFolder into a .zip archive.
    public void zipFolder() {
        System.out.println("Zipping a folder in " + rootFolder);
    }

    // NOTE: stub method — real implementation will use java.util.zip.ZipInputStream to extract
    // the contents of a .zip file into a target folder.
    public void unzipFile() {
        System.out.println("Unzipping a file to a folder in " + rootFolder);
    }
}
