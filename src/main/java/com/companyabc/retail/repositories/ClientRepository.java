package com.companyabc.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyabc.retail.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
