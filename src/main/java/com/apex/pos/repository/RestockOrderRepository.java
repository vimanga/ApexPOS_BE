package com.apex.pos.repository;

import com.apex.pos.model.RestockOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestockOrderRepository extends JpaRepository<RestockOrder, String> {
}
