package com.dly.route_api;

import android.os.Bundle;

/**
 * Created by dly on 2018/6/26.
 */
public class IntentWrapper {

    private Bundle mBundle;

    private IntentWrapper(Builder builder) {
        this.mBundle = builder.mBundle;
    }

    public static class Builder {
        private Bundle mBundle;

        public Builder() {

        }

        public Builder withString(String key, String value) {
            mBundle.putString(key, value);
            return this;
        }

        public IntentWrapper build() {
            return new IntentWrapper(this);
        }
    }
}
