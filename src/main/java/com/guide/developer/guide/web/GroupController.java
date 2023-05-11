package com.guide.developer.guide.web;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.guide.developer.guide.model.Group;
import com.guide.developer.guide.model.GroupRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api")
class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path directory = Paths.get("./uploadedFiles");
            Path filepath = directory.resolve(filename);
            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
            return "Fichier mis en ligne avec succès!";
        } catch (IOException ex) {
            return "Erreur lors de la mise en ligne";
        }
    }

    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        String filePath = "./uploadedFiles/" + fileName;
        FileSystemResource file = new FileSystemResource(filePath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        InputStreamResource isr;
        try {
            isr = new InputStreamResource(file.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/json"))
                .body(isr);
    }

    @GetMapping("/guidesList")
    @ResponseBody
    public List<Map<String, String>> getFolderContents() {
        File folder = new File("./uploadedFiles");

        List<Map<String, String>> foldersList = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                Map<String, String> folderInfo = new HashMap<>();
                File guideStatusFile = new File(file.getAbsolutePath() + "/statutGuide.json");
                if (guideStatusFile.exists()) {
                    try {
                        String guideStatusContent = new String(Files.readAllBytes(guideStatusFile.toPath()));
                        JSONParser parser = new JSONParser(guideStatusContent);
                        Map<String, Object> guideStatus = parser.parseObject();
                        folderInfo.put("nom", guideStatus.get("nom").toString());
                        folderInfo.put("url", "/" + file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    folderInfo.put("nom", file.getName());
                    folderInfo.put("url", "/" + file.getName());
                    foldersList.add(folderInfo);
                }
                foldersList.add(folderInfo);
            }
        }

        return foldersList;
    }



    @GetMapping("/groups")
    Collection<Group> groups() {
        return groupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    ResponseEntity<Group> createGroup(@RequestBody Group group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    @PutMapping("/group/{id}")
    ResponseEntity<Group> updateGroup(@RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
