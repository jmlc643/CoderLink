package com.upao.pe.coderlink.repos;

import com.upao.pe.coderlink.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCompanyName(String companyName);
}
