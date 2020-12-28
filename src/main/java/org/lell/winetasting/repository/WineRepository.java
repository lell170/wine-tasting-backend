package org.lell.winetasting.repository;

import org.lell.winetasting.model.Wine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends CrudRepository<Wine, Long> {
	Iterable<Wine> findByOrderByIdAsc();
}

