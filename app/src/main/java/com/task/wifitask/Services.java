package com.task.wifitask;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private List<Class<?>> classList_ = new ArrayList<>();

    private final Context context_;


    public Services(Context context){
        context_ = context;
    }


    public void setClassList(List<Class<?>> classList) {
        classList_.clear();
        classList_ = classList;
    }

    public void addService(Class<?> clss){
        classList_.add(clss);
    }

    public void allStart(){
        classList_.stream().allMatch(clss->{
            context_.startService(new Intent(context_,clss));
            return true;
        });
    }

    public void allStop(){
        classList_.stream().allMatch(clss->{
            context_.stopService(new Intent(context_,clss));
            return true;
        });
    }
    public void clear(){
        allStop();
        classList_.clear();
    }

    public static class Builder{
        private Services services_;

        public Builder(Context context){
            services_ = new Services(context);
        }
        Builder addClass(Class<?> clss){
            services_.addService(clss);
            return this;
        }
        Services build(){
            return services_;
        }

    }
}
