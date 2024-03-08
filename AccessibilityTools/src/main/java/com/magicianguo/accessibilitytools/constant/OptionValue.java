package com.magicianguo.accessibilitytools.constant;

import com.magicianguo.accessibilitytools.util.DimenUtils;

public interface OptionValue {
    int DEFAULT_RED = 0x45;
    int DEFAULT_GREEN = 0xCB;
    int DEFAULT_BLUE = 0xA8;
    int DEFAULT_ALPHA = 0x20;
    int DEFAULT_VIEW_AREA_TYPE = ViewAreaType.IMPORTANT;
    boolean DEFAULT_VIEW_AREA_TRANSLATE_TOP = true;
    boolean DEFAULT_VIEW_AREA_TRANSLATE_LEFT = false;
    int DEFAULT_VIEW_AREA_TXT_SIZE = (int) DimenUtils.dp2px(10);
    boolean DEFAULT_VIEW_AREA_TXT_SHOW_PKG = false;
    boolean DEFAULT_VIEW_AREA_TXT_SINGLE_LINE = false;
    boolean DEFAULT_VIEW_AREA_WRITE_TO_LOG = false;
}
