package com.lec.ch04_member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private int no;
    private String name;
    private String tel;

    public Member(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

}
