package com.jigi.jpa.exam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "team")
@Entity
public class TeamMember {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public TeamMember() {
    }

    public void setTeam(Team team) {
        this.team = team;

        if (!team.getTeamMembers().contains(this)) {
            team.getTeamMembers().add(this);
        }
    }

}
