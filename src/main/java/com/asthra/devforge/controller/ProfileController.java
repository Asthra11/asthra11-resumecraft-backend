package com.asthra.devforge.controller;

import com.asthra.devforge.service.ResumeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.asthra.devforge.dto.ProfileRequest;
import com.asthra.devforge.entity.Profile;
import com.asthra.devforge.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://asthra11-resumecraft-frontend.vercel.app"
})
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ResumeService resumeService;

    @PostMapping("/create")
    public Profile createProfile(@RequestBody ProfileRequest request) {

        return profileService.createProfile(request);

    }
    @GetMapping("/{id}")
    public Profile getProfile(@PathVariable Long id) {

        return profileService.getProfile(id);

    }
    @GetMapping("/email/{email}")
    public Profile getProfileByEmail(@PathVariable String email) {

        return profileService.getProfileByEmail(email);

    }
    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable Long id,
                                 @RequestBody ProfileRequest request) {

        return profileService.updateProfile(id, request);

    }
    @GetMapping("/resume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long id) throws Exception {

        byte[] pdf = resumeService.generateResume(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {

        profileService.deleteProfile(id);

    }
}
