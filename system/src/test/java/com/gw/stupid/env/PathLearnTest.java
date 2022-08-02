package com.gw.stupid.env;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author guanwu
 * @created on 2022-07-29 16:58:13
 **/
public class PathLearnTest {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("");
        System.out.println(path.toAbsolutePath());
        System.out.println(path.isAbsolute());
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.isReadable(path));
        System.out.println(path.getRoot());
        Path path1 = Paths.get("/Users/guanwu/dev/stupid", "stupid/system/src/main/java/com/gw/stupid/env/EnvUtils.java");
        System.out.println(path1.getNameCount());
        for (Path path2 : path1) {
            System.out.println("current: " + path2);
        }
        System.out.println(path1.getRoot());
        System.out.println(Files.getFileStore(path1));

        Path basePath = Paths.get("/Users/guanwu/dev/stupid");
        Path fullPath = basePath.relativize(path1);
        System.out.println(fullPath);

        System.out.println(basePath.resolve(basePath.resolve(path1)));

        byte[] data = new byte[2048];
        List<String> strings = Files.readAllLines(path1);
        strings.forEach(System.out::println);

//        Path deletePath = Paths.get("/Users/guanwu/filetest/dir2");
//        rmDir(deletePath);
        Path sourcePath = Paths.get("/Users/guanwu/filetest/dir1");
        Path targetPath1 = Paths.get("/Users/guanwu/filetest/dir5cp");
        cpDir(sourcePath, targetPath1);

        testFileSystem();

        Path listener = Paths.get("/Users/guanwu/filetest/testlistener");
        testFileListener(listener);
        Files.walk(listener)
                .filter(Files::isDirectory)
                .forEach(PathLearnTest::testFileListener);

    }

    private static void testFileListener(Path path) {

        WatchService ws = null;
        try {
            ws = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WatchService finalService = ws;
        try {
            path.register(finalService, StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            WatchKey poll = null;
            try {
                poll = finalService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WatchEvent<?>> watchEvents = poll.pollEvents();
            for (WatchEvent<?> watchEvent : watchEvents) {
                System.out.println("evt.context(): " + watchEvent.context() + "" +
                        "\n evt.count():" + watchEvent.count() +
                        "\nevt.kind(): " + watchEvent.kind());
            }
            System.exit(0);
        });

    }

    static void rmDir(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    static void cpDir(Path source, Path target) throws IOException {
        Files.walkFileTree(source, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path targetDir = target.resolve(source.relativize(dir));
                        try {
                            if (!Files.exists(dir)) {
                                Files.copy(dir, targetDir);
                            }
                        } catch (IOException e) {

                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
                        if (Files.exists(target.resolve(source.relativize(file)))) {
                            return FileVisitResult.CONTINUE;
                        }
                        Files.copy(file, target.resolve(source.relativize(file)), LinkOption.NOFOLLOW_LINKS);
                        return FileVisitResult.CONTINUE;
                    }
                });
    }

    static void testFileSystem() {
        System.out.println(System.getProperty("os.name"));
        FileSystem fileSystem = FileSystems.getDefault();
        for (FileStore fileStore : fileSystem.getFileStores()) {
            System.out.println("file store: " + fileStore);
        }

        for (Path path : fileSystem.getRootDirectories()) {
            System.out.println("Root Directory: " + path);
        }
        System.out.println(fileSystem.getSeparator());
    }
}

