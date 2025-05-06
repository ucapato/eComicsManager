package ecomicsmanager;

public class FileRenamer {
    private String rootFolder;

    public FileRenamer(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void renameZipToCbz() {
        System.out.println("Renaming .zip to .cbz in " + rootFolder);
    }

    public void renameCbzToZip() {
        System.out.println("Renaming .cbz to .zip in " + rootFolder);
    }

    public void renameRarToCbr() {
        System.out.println("Renaming .rar to .cbr in " + rootFolder);
    }

    public void renameCbrToRar() {
        System.out.println("Renaming .cbr to .rar in " + rootFolder);
    }
}
