package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableZipWith {

    private static final Logger log = LoggerFactory.getLogger(FlowableZipWith.class);

    public static void main(String[] args) {

        Flowable<String> temperature = Flowable.fromArray("10º", "1º", "25º", "2º", "7º");

        Flowable<String> wind = Flowable.fromArray("1km/h", "60km/h", "10km/h", "20km/h", "7km/h");

        Flowable<String> zipWith = temperature.zipWith(wind,
                (t, w) -> t.concat(" - ").concat(w));

        zipWith.subscribe(log::info);
    }

}
