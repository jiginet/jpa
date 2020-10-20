package com.jigi.jpa.domain;

import com.github.javafaker.Faker;
import com.jigi.jpa.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;

@DataJpaTest
@Profile("local")
class MemberTest {

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {

        Faker faker = new Faker(new Locale("ko"));

        for (int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setCity(faker.address().cityName());
            address.setStreet(faker.address().streetName());
            address.setZipcode(faker.address().zipCode());

            Member member = new Member();
            member.setAddress(address);
            member.setName(faker.name().fullName());

            em.persist(member);
        }
    }

    @Test
    void jpqlTest1() {

        String username = "홍길동";

        TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = :username", Member.class);
        query.setParameter("username", username);

        List<Member> resultList = query.getResultList();

        for (Member member : resultList) {
            System.out.println("member = " + member);
        }

    }

    @Test
    void jpqlTest2() {

        List<Object[]> resultList = em.createQuery("select m.name, m.id from Member m")
                .getResultList();

        for (Object[] row : resultList) {
            System.out.println("member.name = " + row[0]);
            System.out.println("member.id = " + row[1]);
        }
    }

    @Test
    void jpqlTest3() {

        TypedQuery<UserDto> query = em.createQuery("select new com.jigi.jpa.dto.UserDto(m.name, m.id) from Member m", UserDto.class);

        List<UserDto> resultList = query.getResultList();

        for (UserDto user : resultList) {
            System.out.println("member.name = " + user.getUsername());
            System.out.println("member.id = " + user.getAge());
        }
    }

    @Test
    void jpqlTest4() {

        TypedQuery<Member> query = em.createQuery("select m from Member m order by m.name asc", Member.class);
        query.setFirstResult(10);
        query.setMaxResults(20);

        for (Member user : query.getResultList()) {
            System.out.println("member.name = " + user.getName());
            System.out.println("member.address.city = " + user.getAddress().getCity());
            System.out.println("member.address.streetname = " + user.getAddress().getStreet());
        }
    }

    @Test
    void jpqlTest5() {

        Member member = new Member();
        member.setId(10l);

        List resultList = em.createQuery("select m from Member m where  m = :member")
                .setParameter("member", member)
                .getResultList();

        for (Object row : resultList) {
            System.out.println("name = " + ((Member)row).getName());
        }
    }

    @Test
    void jpqlTest6() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);
        cq.select(m);

        TypedQuery<Member> query = em.createQuery(cq);
        List<Member> members = query.getResultList();

        for (Member row : members) {
            System.out.println("name = " + row.getName());
        }
    }

}