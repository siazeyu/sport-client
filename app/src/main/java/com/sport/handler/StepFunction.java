package com.sport.handler;

import android.app.Activity;

public abstract class StepFunction {


    private Activity context;

    public StepFunction(Activity context){
        this.context = context;
    }
    public abstract void onCharge(int step);

    public boolean isActivityValid(){
        return context == null || context.isFinishing() || context.isDestroyed();
    }

}
