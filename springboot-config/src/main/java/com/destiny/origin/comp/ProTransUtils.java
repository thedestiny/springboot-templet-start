package com.destiny.origin.comp;

import lombok.extern.slf4j.Slf4j;
import org.jboss.windup.decompiler.api.DecompilationFailure;
import org.jboss.windup.decompiler.api.DecompilationListener;
import org.jboss.windup.decompiler.api.DecompilationResult;
import org.jboss.windup.decompiler.procyon.ProcyonDecompiler;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


@Slf4j
public class ProTransUtils {

    public static void main(String[] args) throws IOException {

        String sourceJar = "generator-3.8.3.jar";
        Path sourceJarPath = Paths.get(sourceJar);
        String sourceJarFileName = sourceJarPath.getFileName().toString().replaceFirst("[.][^.]+$", "");

        File file = ResourceUtils.getFile("classpath:");
        String relativePath = file.getPath();
        String path = relativePath.substring(0, relativePath.lastIndexOf(File.separatorChar));
        String outputPath = path + File.separator + "procyon" + File.separator + sourceJarFileName;

        Long time = procyon(sourceJar, outputPath);
        System.out.println(String.format("decompiler time: %dms", time));
    }

    /**
     * 解析存在问题，不推荐
     *
     * @param source
     * @param targetPath
     * @return
     * @throws IOException
     */
    public static Long procyon(String source, String targetPath) throws IOException {
        long start = System.currentTimeMillis();
        Path archive = Paths.get(source);
        Path outDir = Paths.get(targetPath);
        ProcyonDecompiler dec = new ProcyonDecompiler();
        DecompilationResult res = dec.decompileArchive(archive, outDir, new DecompilationListener() {
            public void decompilationProcessComplete() {
                System.out.println("decompilationProcessComplete");
            }

            public void decompilationFailed(List<String> inputPath, String message) {
                System.out.println("decompilationFailed");
            }

            public void fileDecompiled(List<String> inputPath, String outputPath) {
            }

            public boolean isCancelled() {
                return false;
            }
        });

        if (!res.getFailures().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed decompilation of " + res.getFailures().size() + " classes: ");
            Iterator failureIterator = res.getFailures().iterator();
            while (failureIterator.hasNext()) {
                DecompilationFailure dex = (DecompilationFailure) failureIterator.next();
                sb.append(System.lineSeparator() + "    ").append(dex.getMessage());
            }
            System.out.println(sb);
        }
        System.out.println("Compilation results: " + res.getDecompiledFiles().size() + " succeeded, " + res.getFailures().size() + " failed.");
        dec.close();
        Long end = System.currentTimeMillis();
        return end - start;
    }




}
