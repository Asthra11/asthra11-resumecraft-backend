package com.asthra.devforge.service;


import com.asthra.devforge.dto.ProfileRequest;
import com.asthra.devforge.entity.Profile;
import com.asthra.devforge.repository.ProfileRepository;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(ProfileRequest request) {
        Profile profile = profileRepository.findByEmail(request.getEmail());

        if (profile == null) {
            profile = new Profile();
        }

        profile.setFullName(request.getFullName());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());

        profile.setObjective(request.getObjective());

        profile.setCollegeName(request.getCollegeName());
        profile.setDegree(request.getDegree());
        profile.setBranch(request.getBranch());
        profile.setGraduationYear(request.getGraduationYear());
        profile.setCgpa(request.getCgpa());

        profile.setSkills(request.getSkills());
        profile.setProjects(request.getProjects());
        profile.setExperience(request.getExperience());
        profile.setCertifications(request.getCertifications());

        profile.setGithub(request.getGithub());
        profile.setLinkedin(request.getLinkedin());

        return profileRepository.save(profile);
    }
    public Profile getProfile(Long id) {

        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public Profile getProfileByEmail(String email) {

        return profileRepository.findByEmail(email);

    }
    public Profile updateProfile(Long id, ProfileRequest request) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setFullName(request.getFullName());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());

        profile.setObjective(request.getObjective());

        profile.setCollegeName(request.getCollegeName());
        profile.setDegree(request.getDegree());
        profile.setBranch(request.getBranch());
        profile.setGraduationYear(request.getGraduationYear());
        profile.setCgpa(request.getCgpa());

        profile.setSkills(request.getSkills());
        profile.setProjects(request.getProjects());
        profile.setExperience(request.getExperience());
        profile.setCertifications(request.getCertifications());

        profile.setGithub(request.getGithub());
        profile.setLinkedin(request.getLinkedin());

        return profileRepository.save(profile);
    }
    public void deleteProfile(Long id) {

        profileRepository.deleteById(id);

    }
}