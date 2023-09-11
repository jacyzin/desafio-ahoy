package br.com.ahoy.xmluploader.adapter.in.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class Response<T> {

    private T data;
    private Collection<String> errors;
}
