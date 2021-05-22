package com.catalogoprodutos.repository;

import java.util.List;

import com.catalogoprodutos.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
  
     @Query("{ $and [ {'price' :{$gte: ?0} }, {'price' :$lte?1} } ] }")
       List<Product> findByValue(Double min_price, Double max_price);
   
     @Query ("{  $and: [{$and: [ {'price':{$gte: ?0} }, {'price':{$lte: ?1} } ]}, { $or: [ {'description': ?2}, {'name': ?2} ]}] }")
       List<Product> findByValue(Double min_price, Double max_price, String q);

}
