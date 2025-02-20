public class RenamePages {
    private String rootFolder;

    public RenamePages(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void execute() {
        System.out.println("Renaming pages in the right order in " + rootFolder);
    }
}
