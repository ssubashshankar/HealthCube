package com.example.hCube.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.hCube.model.patients;

@Repository
public interface patientsRepo extends MongoRepository<patients, String> {

}
