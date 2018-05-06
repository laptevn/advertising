package com.laptevn.advertising.initialization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

final class FileUtils {
    public static File createTempFile(String prefix, String suffix, Optional<String> content, Optional<File> directory) throws IOException {
        File sourceFile = File.createTempFile(prefix, suffix, directory.orElse(null));
        if (content.isPresent()) {
            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(content.get());
            }
        }
        return sourceFile;
    }
}