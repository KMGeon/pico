package com.team5.sparcs.pico.repository;

import com.team5.sparcs.pico.domain.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceRepository  extends JpaRepository<Scientist, Long> {

}
