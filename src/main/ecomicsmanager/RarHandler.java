package ecomicsmanager;

public class RarHandler {
    private String rootFolder;

    public RarHandler(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void rarFolder() {
        System.out.println("RAR-ing a folder in " + rootFolder);
    }

    public void unrarFile() {
        System.out.println("Unraring a file to a folder in " + rootFolder);
    }
}
