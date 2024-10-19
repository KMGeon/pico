package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScienceService {

    private final ScienceRepository scienceRepository;



}
