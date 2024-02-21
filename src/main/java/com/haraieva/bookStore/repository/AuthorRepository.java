package com.haraieva.bookStore.repository;

import com.haraieva.bookStore.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

	@Query(value = "SELECT * " +
			"FROM author a " +
			"WHERE a.first_name = ?1 " +
			"AND a.last_name = ?2", nativeQuery = true)
	Optional<AuthorEntity> findByFirstnameAndLastname(String firstName, String lastName);

//	Optional<AuthorEntity> findByLastnameAndFirstname(String lastName, String firstName);
}
