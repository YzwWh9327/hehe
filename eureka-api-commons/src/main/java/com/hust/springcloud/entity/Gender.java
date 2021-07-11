package com.hust.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Gender {
    F('F'),
    M('M'),
    ;
    private Character gender;
}
