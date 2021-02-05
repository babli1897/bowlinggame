package com.target.repository;

import com.target.model.Lane;
import org.springframework.data.repository.CrudRepository;

public interface LaneRepository extends CrudRepository<Lane,Long> {

    Lane findTop1ByLaneStatusOrderByIdAsc(String status);

    Integer countByLaneStatus(String status);
}
