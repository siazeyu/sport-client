package com.sport.handler.binder;

import android.os.Binder;
import com.sport.handler.StepService;

public class StepBinder extends Binder {

    private StepService stepService;

    public StepBinder(StepService stepService){
        this.stepService = stepService;
    }

    public StepService getStepService() {
        return stepService;
    }
}
