package org.chason.limter.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
public class Limiter {
    private String url;
    private int limitNum;
    private long time;
    private TimeUnit timeUnit;
    private String limitAlgorithm;
}