package com.asthra.devforge.service;

import com.asthra.devforge.entity.Profile;
import com.asthra.devforge.repository.ProfileRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ResumeService {

    @Autowired
    private ProfileRepository profileRepository;
    private Font titleFont() {
        return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
    }

    private Font subTitleFont() {
        return FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
    }

    private Font headingFont() {
        return FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                15,
                new BaseColor(37, 99, 235)
        );
    }

    private Font normalFont() {
        return FontFactory.getFont(
                FontFactory.HELVETICA,
                12,
                BaseColor.DARK_GRAY
        );
    }

    private Font boldFont() {
        return FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                12,
                BaseColor.BLACK
        );
    }

    public byte[] generateResume(Long id) throws Exception {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);

        document.open();

        Paragraph name = new Paragraph(
                profile.getFullName().toUpperCase(),
                titleFont()
        );

        name.setAlignment(Element.ALIGN_CENTER);

        document.add(name);

        Paragraph subtitle = new Paragraph(
                "Professional Resume",
                subTitleFont()
        );

        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(15);

        document.add(subtitle);

        // Contact Details
        Paragraph contact = new Paragraph(
                profile.getEmail()
                        + "   |   "
                        + profile.getPhone()
                        + "   |   "
                        + profile.getGithub()
                        + "   |   "
                        + profile.getLinkedin(),
                normalFont()
        );

        contact.setAlignment(Element.ALIGN_CENTER);

        document.add(contact);

        Paragraph address = new Paragraph(
                profile.getAddress(),
                normalFont()
        );

        address.setAlignment(Element.ALIGN_CENTER);
        address.setSpacingAfter(15);

        document.add(address);
        LineSeparator line = new LineSeparator();
        line.setLineColor(new BaseColor(37, 99, 235));
        line.setLineWidth(2);

        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("────────────────────────────────────────"));

        Paragraph objectiveHeading = new Paragraph(
                "CAREER OBJECTIVE",
                headingFont()
        );

        objectiveHeading.setSpacingBefore(20);
        objectiveHeading.setSpacingAfter(10);

        document.add(objectiveHeading);

        Paragraph objective = new Paragraph(
                profile.getObjective(),
                normalFont()
        );

        objective.setAlignment(Element.ALIGN_JUSTIFIED);
        objective.setSpacingAfter(20);

        document.add(objective);
        Paragraph educationHeading = new Paragraph(
                "EDUCATION",
                headingFont()
        );

        educationHeading.setSpacingBefore(10);
        educationHeading.setSpacingAfter(10);

        document.add(educationHeading);

        document.add(new Paragraph(
                profile.getCollegeName(),
                boldFont()
        ));

        document.add(new Paragraph(
                profile.getDegree() + " (" + profile.getBranch().toUpperCase() + ")",
                normalFont()
        ));

        document.add(new Paragraph(
                "Expected Graduation: " + profile.getGraduationYear(),
                normalFont()
        ));

        document.add(new Paragraph(
                "CGPA: " + profile.getCgpa(),
                normalFont()
        ));

        document.add(Chunk.NEWLINE);
        Paragraph skillsHeading = new Paragraph(
                "SKILLS",
                headingFont()
        );

        skillsHeading.setSpacingBefore(15);
        skillsHeading.setSpacingAfter(10);

        document.add(skillsHeading);

        List skillList = new List(List.UNORDERED);

        for (String skill : profile.getSkills().split(",")) {

            if (!skill.trim().isEmpty()) {
                skillList.add(new ListItem(skill.trim(), normalFont()));
            }

        }

        document.add(skillList);

        document.add(Chunk.NEWLINE);
        Paragraph projectsHeading = new Paragraph(
                "PROJECTS",
                headingFont()
        );

        projectsHeading.setSpacingBefore(15);
        projectsHeading.setSpacingAfter(10);

        document.add(projectsHeading);

        List projectList = new List(List.UNORDERED);

        for (String project : profile.getProjects().split("\\n")) {

            if (!project.trim().isEmpty()) {
                projectList.add(new ListItem(project.trim(), normalFont()));
            }

        }

        document.add(projectList);

        document.add(Chunk.NEWLINE);
        if (profile.getExperience() != null &&
                !profile.getExperience().isBlank()) {

            Paragraph experienceHeading = new Paragraph(
                    "EXPERIENCE",
                    headingFont()
            );

            experienceHeading.setSpacingBefore(15);
            experienceHeading.setSpacingAfter(10);

            document.add(experienceHeading);

            Paragraph experience = new Paragraph(
                    profile.getExperience(),
                    normalFont()
            );

            experience.setAlignment(Element.ALIGN_JUSTIFIED);

            document.add(experience);

            document.add(Chunk.NEWLINE);
        }
        if (profile.getCertifications() != null &&
                !profile.getCertifications().isBlank()) {

            Paragraph certificationHeading = new Paragraph(
                    "CERTIFICATIONS",
                    headingFont()
            );

            certificationHeading.setSpacingBefore(15);
            certificationHeading.setSpacingAfter(10);

            document.add(certificationHeading);

            List certificationList = new List(List.UNORDERED);

            for (String certification : profile.getCertifications().split(",")) {

                if (!certification.trim().isEmpty()) {

                    certificationList.add(
                            new ListItem(
                                    certification.trim(),
                                    normalFont()
                            )
                    );

                }

            }

            document.add(certificationList);

            document.add(Chunk.NEWLINE);
        }

        document.close();

        return out.toByteArray();
    } }