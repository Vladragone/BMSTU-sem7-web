package com.example.game.repository;

import com.example.game.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select distinct l.name from Location l")
    List<String> findDistinctNames();
    List<Location> findByName(String name);

}
