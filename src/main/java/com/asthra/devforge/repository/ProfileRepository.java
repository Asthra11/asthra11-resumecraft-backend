package com.asthra.devforge.repository;

import com.asthra.devforge.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByEmail(String email);

}