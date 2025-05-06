package ecomicsmanager;

public class ZipHandler {
    private String rootFolder;

    public ZipHandler(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void zipFolder() {
        System.out.println("Zipping a folder in " + rootFolder);
    }

    public void unzipFile() {
        System.out.println("Unzipping a file to a folder in " + rootFolder);
    }
}
