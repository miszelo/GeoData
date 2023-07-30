package com.example.geodata.translators;

public interface ResponseTranslator<Input, Output> {
    Output translate(Input input);
}
