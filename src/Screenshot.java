import java.io.File;

public class Screenshot {

    private DeletionListener listener;

    public Screenshot(DeletionListener listener) {
        this.listener = listener;
    }

    public boolean deleteScreenshots(File directory) {
        boolean found = false;
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String filename = file.getName();
                        if (filename.contains("Screenshot") || filename.contains("Screen Shot")) {

                                listener.onFileDeleted("Deleting: " + file.getAbsolutePath());

                            file.delete();
                            found = true;
                        }
                    }
                }
            }
        }
        return found;
    }
}
