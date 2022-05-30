package com.avaya.ccaas.call_state_builder.converter;

public interface AvroConverter<T, D> {

    D fromAvro(T avro);
}
