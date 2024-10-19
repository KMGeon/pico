package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.QuizVO;
import com.team5.sparcs.pico.repository.QuizRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;


    @Data
    public static class Scientist {
        String name;
        List<String> descriptions;

        Scientist(String name, List<String> descriptions) {
            this.name = name;
            this.descriptions = descriptions;
        }
    }

    private static final Map<String, List<List<String>>> QUESTION_CHOICES = new HashMap<>();
    private static final Map<String, Integer> SCIENTIST_SCORES = new HashMap<>();
    private static final Map<String, Scientist> SCIENTISTS = new HashMap<>();

    static {
        QUESTION_CHOICES.put("Q2", Arrays.asList(
                Arrays.asList("아리스토텔레스", "퀴리", "뉴턴"),
                Arrays.asList("아인슈타인", "가우스", "튜링")
        ));
        QUESTION_CHOICES.put("Q3", Arrays.asList(
                Arrays.asList("뉴턴", "퀴리", "가우스"),
                Arrays.asList("아인슈타인", "갈릴레오", "튜링")
        ));
        QUESTION_CHOICES.put("Q4", Arrays.asList(
                Arrays.asList("아인슈타인", "튜링", "다윈"),
                Arrays.asList("뉴턴", "퀴리", "가우스")
        ));
        QUESTION_CHOICES.put("Q5", Arrays.asList(
                Arrays.asList("아리스토텔레스", "갈릴레오", "다윈"),
                Arrays.asList("아인슈타인", "튜링", "뉴턴")
        ));
        QUESTION_CHOICES.put("Q8", Arrays.asList(
                Arrays.asList("뉴턴", "가우스", "퀴리"),
                Arrays.asList("아인슈타인", "갈릴레오", "다윈")
        ));
        QUESTION_CHOICES.put("Q9", Arrays.asList(
                Arrays.asList("가우스", "뉴턴", "튜링"),
                Arrays.asList("아리스토텔레스", "다윈", "갈릴레오")
        ));
        QUESTION_CHOICES.put("Q11", Arrays.asList(
                Arrays.asList("뉴턴", "가우스", "퀴리"),
                Arrays.asList("아인슈타인", "갈릴레오", "다윈")
        ));
        QUESTION_CHOICES.put("Q13", Arrays.asList(
                Arrays.asList("뉴턴", "튜링", "가우스"),
                Arrays.asList("아리스토텔레스", "갈릴레오", "아인슈타인")
        ));
        QUESTION_CHOICES.put("Q15", Arrays.asList(
                Arrays.asList("뉴턴", "퀴리", "가우스"),
                Arrays.asList("아인슈타인", "다윈", "튜링")
        ));
        QUESTION_CHOICES.put("Q17", Arrays.asList(
                Arrays.asList("뉴턴", "퀴리", "가우스"),
                Arrays.asList("아인슈타인", "갈릴레오", "다윈")
        ));
        QUESTION_CHOICES.put("Q18", Arrays.asList(
                Arrays.asList("가우스", "튜링", "뉴턴"),
                Arrays.asList("아리스토텔레스", "갈릴레오", "아인슈타인")
        ));
        QUESTION_CHOICES.put("Q19", Arrays.asList(
                Arrays.asList("뉴턴", "가우스", "퀴리"),
                Arrays.asList("아인슈타인", "갈릴레오", "다윈")
        ));

        Arrays.asList("아인슈타인", "뉴턴", "퀴리", "다윈", "갈릴레오", "가우스", "아리스토텔레스", "튜링")
                .forEach(scientist -> SCIENTIST_SCORES.put(scientist, 0));

        SCIENTISTS.put("아인슈타인", new Scientist("알베르트 아인슈타인",
                Arrays.asList("비상한 괴짜!", "상상력이 지식보다 중요한 몽상가", "시간과 공간을 뛰어넘는 천재")));
        SCIENTISTS.put("뉴턴", new Scientist("아이작 뉴턴",
                Arrays.asList("냉철한 천재!", "꼼꼼한 분석가", "침착한 완벽주의자")));
        SCIENTISTS.put("퀴리", new Scientist("마리 퀴리",
                Arrays.asList("끈기 있는 탐구자", "끈기/열정의 여왕", "희생적인 연구자")));
        SCIENTISTS.put("다윈", new Scientist("찰스 다윈",
                Arrays.asList("진화의 선구자", "호기심 많은 탐험가", "변화의 관찰자")));
        SCIENTISTS.put("갈릴레오", new Scientist("갈릴레오 갈릴레이",
                Arrays.asList("마이웨이 고집쟁이", "진실을 추구하는 반항아", "하늘을 사랑한 감서")));
        SCIENTISTS.put("가우스", new Scientist("카를 프리드리히 가우스",
                Arrays.asList("천재", "수학의 왕자", "정밀함의 마스터")));
        SCIENTISTS.put("아리스토텔레스", new Scientist("아리스토텔레스",
                Arrays.asList("철학과 과학의 다리", "박학다식한 사색가", "지식의 아버지")));
        SCIENTISTS.put("튜링", new Scientist("앨런 튜링",
                Arrays.asList("비상한 논리가", "암호 해독의 영웅", "논리적 상상력의 천재")));
    }

    public Scientist recommendScientist(List<Integer> userAnswers) {
        if (userAnswers.size() != 12) {
            throw new IllegalArgumentException("12개의 답변이 필요합니다.");
        }

        resetScores();
        calculateScores(userAnswers);
        return getTopScientist();
    }

    private void resetScores() {
        SCIENTIST_SCORES.replaceAll((k, v) -> 0);
    }

    private void calculateScores(List<Integer> userAnswers) {
        List<String> questions = new ArrayList<>(QUESTION_CHOICES.keySet());
        Collections.sort(questions);

        for (int i = 0; i < userAnswers.size(); i++) {
            int answer = userAnswers.get(i);
            String question = questions.get(i);

            if (answer < 1 || answer > 2) {
                throw new IllegalArgumentException(question + "에 대한 답변은 1 또는 2여야 합니다.");
            }

            List<String> scientists = QUESTION_CHOICES.get(question).get(answer - 1);
            for (String scientist : scientists) {
                SCIENTIST_SCORES.put(scientist, SCIENTIST_SCORES.get(scientist) + 1);
            }
        }
    }

    private Scientist getTopScientist() {
        String topScientistName = SCIENTIST_SCORES.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("추천할 과학자를 찾을 수 없습니다."));

        return SCIENTISTS.get(topScientistName);
    }

    public List<QuizVO> findAll() {
        return quizRepository.findAll();
    }
}
