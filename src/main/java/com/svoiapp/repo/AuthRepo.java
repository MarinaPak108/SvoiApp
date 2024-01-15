package com.svoiapp.repo;


import com.svoiapp.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository <AuthEntity, Integer> {

    AuthEntity findAuthEntityByName (String name);
}
