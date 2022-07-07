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
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringInputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
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
    public List<String> listRoots(HttpServletRequest request) {
        log.debug("listRoots(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        return toStr(roots, null);
    }

    @GetMapping("/files/tree/roots")
    public List<List<String>> listTreeRoots(HttpServletRequest request) throws IOException {
        log.debug("listTreeRoots(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        LinkedList<List<String>> trees = new LinkedList<>();
        for (Path root : roots) {
            trees.add( toStr(Files.walk(root).collect(Collectors.toList()), root) );
        }
        return trees;
    }

    @GetMapping("/files/tree/{rootId}")
    public List<String> listTreeFiles(HttpServletRequest request, @PathVariable int rootId) throws IOException {
        log.debug("listTreeFiles(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        log.debug("listTreeFiles(): --- Root-Id: {}", rootId);
        Path root = roots.get(rootId);
        return toStr(Files.walk(root).collect(Collectors.toList()), root);
    }

    @GetMapping({"/files/dir/{rootId}", "/files/dir/{rootId}/**"})
    public List<String> listDirFiles(HttpServletRequest request, @PathVariable int rootId, WebRequest webRequest) throws IOException {
        log.debug("listDirFiles(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        String mvcPrefix = "/files/dir/" + rootId;
        log.debug("listDirFiles(): --- mvc-prefix: {}", mvcPrefix);
        String mvcPath = (String) webRequest.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        log.debug("listDirFiles(): --- mvc-path: {}", mvcPath);
        String path = mvcPath.substring(mvcPrefix.length());
        log.debug("listDirFiles(): --- Root-Id: {}, Path: {}", rootId, path);

        Path dir = Paths.get(roots.get(rootId).toString(), path);
        log.debug("listDirFiles(): --- Effective Path: {}", dir);
        if (dir.toFile().isDirectory()) {
            return toStr(Files.list(dir).collect(Collectors.toList()), dir);
        } else {
            return toStr(Collections.singletonList(dir), roots.get(rootId));
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
        String path = mvcPath.substring(mvcPrefix.length());
        log.debug("listDirFiles(): --- Root-Id: {}, Path: {}", rootId, path);

        File file = Paths.get(roots.get(rootId).toString(), path).toFile();
        log.debug("listDirFiles(): --- Effective Path: {}", file);
        if (!file.exists()) {
            return ResponseEntity.badRequest().body(new InputStreamResource(new StringInputStream("File not exists")));
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

    private List<String> toStr(@NonNull List<Path> list, @Null Path root) {
        String prefix = (root!=null) ? root.toString() : "";
        return list.stream()
                .map(Path::toString)
                .map(s-> StringUtils.removeStart(s, prefix))
                .filter(s->!s.isEmpty())
                .collect(Collectors.toList());
    }
}
