package org.jules.controller;

import org.jules.Model.PlanningRow;
import org.jules.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.List;

@RestController
public class HomeController {

    private final HomeService homeService;
    private static final String TEXT_HTML_UTF8 = "text/html; charset=UTF-8";

    @Autowired
    HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        try {
            return buildPageResponse("/static/pages/notfoundpage/notfound.html", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            String htmlContent = "<html><body>Error</body></html>";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, TEXT_HTML_UTF8);
            return new ResponseEntity<>(htmlContent, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> home(@PathVariable("id") String id) {
        try {
            int intId = Integer.parseInt(id);
            if(homeService.isKnownNurse(intId)) {
                return buildPageResponse("/static/index.html", HttpStatus.OK);
            } else {
                return buildPageResponse("/static/pages/notfoundpage/notfound.html", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e) {
            String htmlContent = "<html><body>Error</body></html>";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, TEXT_HTML_UTF8);
            return new ResponseEntity<>(htmlContent, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<List<PlanningRow>> declarePlanning(@PathVariable("id") String id, @RequestBody List<PlanningRow> planningList) {
        try {
            homeService.declarePlanning(planningList, Integer.parseInt(id));
        } catch(Exception e) {
            return new ResponseEntity<>(planningList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> buildPageResponse(String pagePath, HttpStatus status) throws IOException {
        Resource resource = new ClassPathResource(pagePath); // Localisation du fichier
        String htmlContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, TEXT_HTML_UTF8);

        return new ResponseEntity<>(htmlContent, headers, status); // Retourne la page HTML
    }
}
