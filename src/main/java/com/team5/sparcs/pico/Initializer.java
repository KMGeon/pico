package com.team5.sparcs.pico;

import com.team5.sparcs.pico.domain.Img;
import com.team5.sparcs.pico.domain.Scientist;
import com.team5.sparcs.pico.repository.ImgRepository;
import com.team5.sparcs.pico.repository.ScienceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class Initializer {

    @Bean
    public CommandLineRunner initRoles(ScienceRepository scienceRepository,
                                       ImgRepository imgRepository) {
        return args -> {
            var o = new ArrayList<Img>();
            for (int i = 0; i < 10; i++) {
                Img img = Img.builder()
                        .imgUlr("img url 1" + i)
                        .fileName("img name" + i)
                        .build();
                o.add(img);
            }

            imgRepository.saveAll(o);


            scienceRepository.save(Scientist.builder()
                    .name("아인슈타인 (Albert Einstein)")
                    .firstDescription("물리에 대해서 알려줄게 !!")
                    .year("1879")
                    .imgUrl("1")
                    .build());

            scienceRepository.save(Scientist.builder()
                    .name("뉴턴 (Isaac Newton)")
                    .firstDescription("나와 우주 이야기 함께할래?")
                    .year("1643")
                    .imgUrl("2")
                    .build());

            scienceRepository.save(Scientist.builder()
                    .name("다윈 (Charles Darwin)")
                    .firstDescription("생물의 진화에 대해 이야기해볼까 ?")
                    .year("1809")
                    .imgUrl("3")
                    .build());

            scienceRepository.save(Scientist.builder()
                    .name("마리 퀴리 (Marie Curie)")
                    .firstDescription("방사능의 세계로 초대할게")
                    .year("1867")
                    .imgUrl("4")
                    .build());

            scienceRepository.save(Scientist.builder()
                    .name("갈릴레오 갈릴레이 (Galileo Galilei)")
                    .firstDescription("망원경으로 본 우주의 비밀을 공유하고 싶어")
                    .year("1564")
                    .imgUrl("5")
                    .build());

            scienceRepository.save(Scientist.builder()
                    .name("스티븐 호킹 (Stephen Hawking)")
                    .firstDescription("블랙홀의 신비에 대해 얘기해볼까")
                    .year("1942")
                    .imgUrl("6")
                    .build());
        };
    }
}