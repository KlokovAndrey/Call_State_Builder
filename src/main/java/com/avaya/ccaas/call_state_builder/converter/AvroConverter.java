package com.avaya.ccaas.call_state_builder.converter;

import org.apache.avro.specific.SpecificRecord;

public interface AvroConverter<T, D> {

    D fromAvro(T avro);
}
