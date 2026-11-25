package org.example.challenge02;

import org.example.model.Member;

public interface Registrations {

    public boolean register(Member member);
    public boolean unregister(String memberId);
    public boolean isRegistered(String memberId);
    public int countMembers();

}
