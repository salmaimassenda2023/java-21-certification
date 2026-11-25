package org.example.challenge02;

import org.example.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationsImplTest {
    private Member m1 = new Member("1","A");
    private Registrations registrations ;

    @BeforeEach
    void setUp(){
        registrations=new RegistrationsImpl();
        registrations.register(m1);
    }

    @Test
    void register_newId_returnTrue(){
        Member m3 = new Member("2","B");
      assertTrue(registrations.register(m3));
    }
    @Test
    void register_sameIdDifferentName_returnFalse(){
        Member m2 = new Member("1","AA");
        assertFalse(registrations.register(m2));

    }

    @Test
    void register_null_throwsNPE(){
        assertThrows(NullPointerException.class,()-> registrations.register(null));
    }

}