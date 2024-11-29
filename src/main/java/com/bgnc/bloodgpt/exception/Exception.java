package com.bgnc.bloodgpt.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exception<E>{
    private String path;

    private String hostName;
    private Date createdAt;

    private E message;
}






