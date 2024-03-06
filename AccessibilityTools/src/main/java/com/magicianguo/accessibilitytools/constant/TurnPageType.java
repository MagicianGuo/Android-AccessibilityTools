package com.magicianguo.accessibilitytools.constant;

import androidx.annotation.IntDef;

public interface TurnPageType {
    int LEFT = 0;
    int UP = 1;
    int RIGHT = 2;
    int UP_VIDEO = 3;

    @IntDef({ LEFT, UP, RIGHT, UP_VIDEO})
    @interface Type {
    }
}
