package com.edig.imbackend.auth.repository;

import com.edig.imbackend.auth.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, String> {
}
