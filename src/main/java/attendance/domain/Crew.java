package attendance.domain;

import java.util.List;
import java.util.Set;

public class Crew {

    private Set<String> crew;

    private Crew(Set<String> crew) {
        this.crew = crew;
    }

    public static Crew of(Set<String> crew) {
        return new Crew(crew);
    }
}
