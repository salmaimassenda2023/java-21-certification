package org.example.challenge02;

import org.example.model.Member;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegistrationsImpl implements Registrations{

    private final Set<Member> registered = new HashSet<>();

    @Override
    public boolean register(Member member) {

        return registered.add(Objects.requireNonNull(member));
    }

    @Override
    public boolean unregister(String memberId) {
        return registered.remove(new Member(memberId, null));
    }

    @Override
    public boolean isRegistered(String memberId) {
        return registered.contains(new Member(memberId, null));
    }

    @Override
    public int countMembers() {
        return registered.size();
    }
}
