package com.batch.configurtion.listener;

import org.springframework.batch.core.SkipListener;

public class SkipListern implements SkipListener {
    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {

    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {


    }
}
