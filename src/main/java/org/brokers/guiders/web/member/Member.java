package org.brokers.guiders.web.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private int gender;
    private String phone;
    private String photo;
    private String ctno;
    private String regDate;

}
