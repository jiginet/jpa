package com.jigi.jpa.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;


@DataJpaTest
@Profile("local")
class TeamTest {

    @Autowired
    private EntityManager em;

    @Test
    void test1() {
        Team team = new Team();
        team.setId("team1");
        team.setName("팀1");
        em.persist(team);

        TeamMember teamMember1 = new TeamMember();
        teamMember1.setId("member1");
        teamMember1.setUsername("정연");
        teamMember1.setTeam(team);
        //em.persist(member1);

        TeamMember teamMember2 = new TeamMember();
        teamMember2.setId("member2");
        teamMember2.setUsername("에일리");
        teamMember2.setTeam(team);
//        em.persist(member2);

        team.getTeamMembers().add(teamMember1);
        team.getTeamMembers().add(teamMember2);
        em.persist(team);

        for (TeamMember teamMember : team.getTeamMembers()) {
            System.out.println(teamMember);
            System.out.println(teamMember.getTeam().getId());
        }
    }
}