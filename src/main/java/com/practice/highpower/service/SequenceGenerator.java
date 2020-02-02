package com.practice.highpower.service;

import com.practice.highpower.exception.SequenceException;

public interface SequenceGenerator {
    long getNextSequenceId(String key) throws SequenceException;
}
