package com.guide.developer.guide.web;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.guide.developer.guide.model.Group;
import com.guide.developer.guide.model.GroupRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("dir") String dir, @Param("name") String name) {
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            log.info("Fichier mis en ligne: " + filename + " dans le dossier: " + dir + " avec le nom: " + name);
            Path directory = Paths.get("./uploadedFiles/" + dir);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                JSONArray guides = new JSONArray();
                JSONObject guide = new JSONObject();
                guide.put("nom", name);
                guide.put("version", "1");
                guide.put("usesQuestions", true);
                guide.put("usesPages", true);
                guide.put("usesGE", false);
                guide.put("usesLexique", false);
                guide.put("usesSecteursAltitudes", false);
                guide.put("usesMap", false);
                guides.add(guide);

                Path filepath = directory.resolve("statutGuide.json");
                Files.write(filepath, guides.toString().getBytes());
            }
            String statutPath = "./uploadedFiles/" + dir + "/statutGuide.json";
            File statutFile = new File(statutPath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode statutJson = objectMapper.readTree(new FileInputStream(statutFile));
            if (filename.equals("question.json")) {
                ((ObjectNode) statutJson).set("usesQuestions", JsonNodeFactory.instance.booleanNode(true));
            }
            if (filename.equals("pages.json")) {
                ((ObjectNode) statutJson).set("usesPages", JsonNodeFactory.instance.booleanNode(true));
            }
            if (filename.equals("ge.json")) {
                ((ObjectNode) statutJson).set("usesGE", JsonNodeFactory.instance.booleanNode(true));
            }
            if (filename.equals("lexique.json")) {
                ((ObjectNode) statutJson).set("usesLexique", JsonNodeFactory.instance.booleanNode(true));
            }
            if (filename.equals("secteursAltitudes.json")) {
                ((ObjectNode) statutJson).set("usesSecteursAltitudes", JsonNodeFactory.instance.booleanNode(true));
            }
            if (filename.equals("map.json")) {
                ((ObjectNode) statutJson).set("usesMap", JsonNodeFactory.instance.booleanNode(true));
            }
            statutJson = objectMapper.readTree(objectMapper.writeValueAsString(statutJson));
            objectMapper.writeValue(new File("./uploadedFiles/"+dir+"/statutGuide.json"), statutJson);

            Path filepath = directory.resolve(filename);
            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
            return "Fichier mis en ligne avec succès!";
        } catch (IOException ex) {
            log.info("Erreur lors de la mise en ligne du fichier: " + ex);
            return "Erreur lors de la mise en ligne";
        }
    }

    @GetMapping("/files/{dirName}/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName, @PathVariable String dirName) {
        String filePath = "./uploadedFiles/" + dirName + "/" + fileName;
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
                        List<Map<String, String>> guides = (List<Map<String, String>>) parser.parse();
                        Map<String, String> guide = guides.get(0);
                        String nom = guide.get("nom");
                        folderInfo.put("nom", nom);
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
