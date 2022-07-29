package com.gw.stupid.env;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

        byte []data = new byte[2048];
        List<String> strings = Files.readAllLines(path1);
        strings.forEach(System.out::println);

    }
}
