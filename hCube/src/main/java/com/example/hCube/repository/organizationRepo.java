package com.example.hCube.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hCube.model.organization;

public interface organizationRepo extends MongoRepository<organization, String>{

}
