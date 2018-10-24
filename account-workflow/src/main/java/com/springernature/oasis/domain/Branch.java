package com.springernature.oasis.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Branch {

    private String code;
    private String name;
    private Address address;
}