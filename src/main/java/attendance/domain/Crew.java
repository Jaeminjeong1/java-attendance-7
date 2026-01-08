package attendance.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Crew {

    private final List<Member> members;

    public Crew(List<Member> members) {
        this.members = members;
    }

    public boolean contains(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Member> findMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public Set<String> getMemberNames() {
        Set<String> names = new HashSet<>();
        for (Member member : members) {
            names.add(member.getName());
        }
        return names;
    }
}
