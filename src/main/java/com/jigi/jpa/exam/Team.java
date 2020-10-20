package com.jigi.jpa.exam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "teamMembers")
@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team() {
    }

    public void addMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);

        if (teamMember.getTeam() != this) {
            teamMember.setTeam(this);
        }
    }
}
