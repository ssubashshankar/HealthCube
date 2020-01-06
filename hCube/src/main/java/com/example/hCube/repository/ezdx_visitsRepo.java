package com.example.hCube.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.hCube.model.ezdx_visits;

@Repository
public interface ezdx_visitsRepo extends MongoRepository<ezdx_visits, String> {

}
