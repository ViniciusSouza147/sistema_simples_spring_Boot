package br.com.springbootgreendogdelivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springbootgreendogdelivery.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByName(String name);
	Page<Cliente> findNameContainingAllIgnoringCase(@Param("name") String name, Pageable pageable);
	Cliente findNameContainingAllIgnorinCase(@Param("name") String name);
	
}
