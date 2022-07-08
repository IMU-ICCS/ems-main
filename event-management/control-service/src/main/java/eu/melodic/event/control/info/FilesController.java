/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.control.properties.InfoServiceProperties;
import eu.melodic.event.util.EmsConstant;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringInputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@ConditionalOnProperty(value = "filesEnabled", prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "info", havingValue = "true", matchIfMissing = true)
public class FilesController {

    private final List<Path> roots;

    public FilesController(@NonNull InfoServiceProperties properties) {
        List<Path> tmp = properties.getFileRoots();
        this.roots = (tmp!=null) ? tmp : Collections.emptyList();
        log.info("FilesController: File roots: {}", roots);
    }

    @GetMapping("/files")
    public List<FILE> listRoots(HttpServletRequest request) {
        log.debug("listRoots(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        return toFileList(roots, null);
    }

    @GetMapping("/files/tree/roots")
    public List<List<FILE>> listTreeRoots(HttpServletRequest request) throws IOException {
        log.debug("listTreeRoots(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        LinkedList<List<FILE>> trees = new LinkedList<>();
        for (Path root : roots) {
            trees.add( toFileList(Files.walk(root).collect(Collectors.toList()), root) );
        }
        return trees;
    }

    @GetMapping("/files/tree/{rootId}")
    public List<FILE> listTreeFiles(HttpServletRequest request, @PathVariable int rootId) throws IOException {
        log.debug("listTreeFiles(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        log.debug("listTreeFiles(): --- Root-Id: {}", rootId);
        Path root = roots.get(rootId);
        return toFileList(Files.walk(root).collect(Collectors.toList()), root);
    }

    @GetMapping({"/files/dir/{rootId}", "/files/dir/{rootId}/**"})
    public List<FILE> listDirFiles(HttpServletRequest request, @PathVariable int rootId, WebRequest webRequest) throws IOException {
        log.debug("listDirFiles(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        String mvcPrefix = "/files/dir/" + rootId;
        log.debug("listDirFiles(): --- mvc-prefix: {}", mvcPrefix);
        String mvcPath = (String) webRequest.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        log.debug("listDirFiles(): --- mvc-path: {}", mvcPath);
        String pathStr = mvcPath.substring(mvcPrefix.length());
        log.debug("listDirFiles(): --- Root-Id: {}, Path: {}", rootId, pathStr);

        Path path = Paths.get(roots.get(rootId).toString(), pathStr);
        log.debug("listDirFiles(): --- Effective Path: {}", path);
        if (path.toFile().exists()) {
            if (path.toFile().isDirectory()) {
                return toFileList(Files.list(path).collect(Collectors.toList()), path);
            } else {
                return null;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: "+rootId+": "+pathStr);
        }
    }

    @GetMapping("/files/get/{rootId}/**")
    public ResponseEntity<InputStreamResource> getFile(HttpServletRequest request, @PathVariable int rootId, WebRequest webRequest) throws IOException {
        log.debug("listDirFiles(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        String mvcPrefix = "/files/get/" + rootId + "/";
        log.debug("listDirFiles(): --- mvc-prefix: {}", mvcPrefix);
        String mvcPath = (String) webRequest.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        log.debug("listDirFiles(): --- mvc-path: {}", mvcPath);
        String pathStr = mvcPath.substring(mvcPrefix.length());
        log.debug("listDirFiles(): --- Root-Id: {}, Path: {}", rootId, pathStr);

        File file = Paths.get(roots.get(rootId).toString(), pathStr).toFile();
        log.debug("listDirFiles(): --- Effective Path: {}", file);
        if (!file.exists()) {
            //return ResponseEntity.badRequest().body(new InputStreamResource(new StringInputStream("File not exists")));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: "+rootId+": "+pathStr);
        }
        if (!file.canRead()) {
            return ResponseEntity.badRequest().body(new InputStreamResource(new StringInputStream("File cannot be read")));
        }
        if (file.isFile()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getName());
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            String ext = StringUtils.substringAfterLast(file.getName(), ".").trim();
            //String mimeType = Files.probeContentType(file.toPath());
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            log.debug("listDirFiles(): --- File content type: {}", mimeType);
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            try {
                mediaType = MediaType.parseMediaType(mimeType);
            } catch (Exception e) {
                log.warn("listDirFiles(): --- Invalid File content type: {}\n", mimeType, e);
            }

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(mediaType)
                    .body(new InputStreamResource(new FileInputStream(file)));
        }
        return ResponseEntity.badRequest().body(new InputStreamResource(new StringInputStream("Not a regular file")));
    }

    private List<FILE> toFileList(@NonNull List<Path> paths, @Null Path root) {
        String prefix = (root!=null) ? root.toString() : "";
        List<FILE> list = new LinkedList<>();
        for (Path p : paths) {
            String pathStr = StringUtils.removeStart(p.toString(), prefix);
            File f = p.toFile();
            if (StringUtils.isNotBlank(pathStr))
                list.add(FILE.builder()
                        .path(pathStr)
                        .size(f.length())
                        .lastModified(f.lastModified())
                        .hidden(f.isHidden())
                        .dir(f.isDirectory())
                        .root(root==null)
                        .read(f.canRead()).write(f.canWrite()).exec(f.canExecute())
                        .build());
        }
        return list;
    }

    @Data
    @Builder
    public static class FILE {
        private final String path;
        private final long size;
        private final long lastModified;
        private final boolean hidden;
        private final boolean dir;
        private final boolean root;
        private final boolean read;
        private final boolean write;
        private final boolean exec;
    }
}
