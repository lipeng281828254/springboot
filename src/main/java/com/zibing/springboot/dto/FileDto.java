package com.zibing.springboot.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileDto implements Serializable {

    private String fileId;
    private String fileName;
}
