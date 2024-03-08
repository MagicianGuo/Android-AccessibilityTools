package com.magicianguo.accessibilitytools.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.constant.OptionValue;
import com.magicianguo.accessibilitytools.constant.ViewAreaType;
import com.magicianguo.accessibilitytools.databinding.ActivitySettingBinding;
import com.magicianguo.accessibilitytools.util.AlertManager;
import com.magicianguo.accessibilitytools.util.FileManager;
import com.magicianguo.accessibilitytools.util.SPUtils;
import com.magicianguo.accessibilitytools.view.ViewAreaItemView;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        updateViewAreaColorState();
        updateViewAreaRedState(false);
        updateViewAreaGreenState(false);
        updateViewAreaBlueState(false);
        updateViewAreaAlphaState(false);
        binding.sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewAreaItemView.red = progress;
                updateViewAreaColorState();
                updateViewAreaRedState(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setViewAreaRed(ViewAreaItemView.red);
            }
        });
        binding.sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewAreaItemView.green = progress;
                updateViewAreaColorState();
                updateViewAreaGreenState(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setViewAreaGreen(ViewAreaItemView.green);
            }
        });
        binding.sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewAreaItemView.blue = progress;
                updateViewAreaColorState();
                updateViewAreaBlueState(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setViewAreaBlue(ViewAreaItemView.blue);
            }
        });
        binding.sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewAreaItemView.alpha = progress;
                updateViewAreaColorState();
                updateViewAreaAlphaState(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setViewAreaAlpha(ViewAreaItemView.alpha);
            }
        });
        binding.rgViewAreaType.check(getCheckedViewAreaTypeId());
        binding.rgViewAreaType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final int type;
                if (checkedId == R.id.rb_view_area_type_1) {
                    type = ViewAreaType.ALL;
                } else if (checkedId == R.id.rb_view_area_type_2) {
                    type = ViewAreaType.IMPORTANT;
                } else if (checkedId == R.id.rb_view_area_type_3) {
                    type = ViewAreaType.CONTAIN_TEXT;
                } else {
                    throw new RuntimeException("Unknown id: " + checkedId);
                }
                AlertManager.viewAreaType = type;
                SPUtils.setViewAreaType(type);
            }
        });
        binding.swViewAreaTranslateTop.setChecked(AlertManager.isViewAreaTranslateTop);
        binding.swViewAreaTranslateTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertManager.isViewAreaTranslateTop = isChecked;
                SPUtils.setViewAreaTranslateTop(isChecked);
            }
        });
        binding.swViewAreaTranslateLeft.setChecked(AlertManager.isViewAreaTranslateLeft);
        binding.swViewAreaTranslateLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertManager.isViewAreaTranslateLeft = isChecked;
                SPUtils.setViewAreaTranslateLeft(isChecked);
            }
        });
        updateViewAreaTxtSizeState(false);
        binding.sbViewAreaTxtSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewAreaItemView.textSize = progress;
                updateViewAreaTxtSizeState(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SPUtils.setViewAreaTxtSize(ViewAreaItemView.textSize);
            }
        });
        binding.swViewAreaTxtShowPkg.setChecked(AlertManager.viewAreaTxtShowPkg);
        binding.swViewAreaTxtShowPkg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertManager.viewAreaTxtShowPkg = isChecked;
                SPUtils.setViewAreaTxtShowPkg(isChecked);
            }
        });
        binding.swViewAreaTxtSingleLine.setChecked(AlertManager.viewAreaTxtSingleLine);
        binding.swViewAreaTxtSingleLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertManager.viewAreaTxtSingleLine = isChecked;
                SPUtils.setViewAreaTxtSingleLine(isChecked);
            }
        });
        binding.swViewAreaWriteLog.setChecked(SPUtils.getViewAreaWriteToLog());
        binding.swViewAreaWriteLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertManager.viewAreaWriteToLog = isChecked;
                SPUtils.setViewAreaWriteToLog(isChecked);
                if (isChecked) {
                    Snackbar.make(binding.getRoot(), getString(R.string.settings_toast_view_area_write_log_to_xxx_path, FileManager.VIEW_AREA_LOG_PATH), Snackbar.LENGTH_LONG)
                            .setAction(R.string.confirm, v -> {})
                            .setTextMaxLines(4)
                            .show();
                }
            }
        });
        binding.btnRestoreDefaultSettings.setOnClickListener(v -> restoreDefaultSettings());
    }

    private void restoreDefaultSettings() {
        ViewAreaItemView.red = OptionValue.DEFAULT_RED;
        SPUtils.setViewAreaRed(OptionValue.DEFAULT_RED);
        ViewAreaItemView.green = OptionValue.DEFAULT_GREEN;
        SPUtils.setViewAreaGreen(OptionValue.DEFAULT_GREEN);
        ViewAreaItemView.blue = OptionValue.DEFAULT_BLUE;
        SPUtils.setViewAreaBlue(OptionValue.DEFAULT_BLUE);
        ViewAreaItemView.alpha = OptionValue.DEFAULT_ALPHA;
        SPUtils.setViewAreaAlpha(OptionValue.DEFAULT_ALPHA);
        updateViewAreaColorState();
        updateViewAreaRedState(false);
        updateViewAreaGreenState(false);
        updateViewAreaBlueState(false);
        updateViewAreaAlphaState(false);

        AlertManager.viewAreaType = OptionValue.DEFAULT_VIEW_AREA_TYPE;
        SPUtils.setViewAreaType(OptionValue.DEFAULT_VIEW_AREA_TYPE);
        binding.rgViewAreaType.check(getCheckedViewAreaTypeId());

        AlertManager.isViewAreaTranslateTop = OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_TOP;
        binding.swViewAreaTranslateTop.setChecked(OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_TOP);
        SPUtils.setViewAreaTranslateTop(OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_TOP);

        AlertManager.isViewAreaTranslateLeft = OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_LEFT;
        binding.swViewAreaTranslateLeft.setChecked(OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_LEFT);
        SPUtils.setViewAreaTranslateLeft(OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_LEFT);

        ViewAreaItemView.textSize = OptionValue.DEFAULT_VIEW_AREA_TXT_SIZE;
        SPUtils.setViewAreaTxtSize(OptionValue.DEFAULT_VIEW_AREA_TXT_SIZE);
        binding.sbViewAreaTxtSize.setProgress(OptionValue.DEFAULT_VIEW_AREA_TXT_SIZE);

        AlertManager.viewAreaTxtShowPkg = OptionValue.DEFAULT_VIEW_AREA_TXT_SHOW_PKG;
        SPUtils.setViewAreaTxtShowPkg(OptionValue.DEFAULT_VIEW_AREA_TXT_SHOW_PKG);
        binding.swViewAreaTxtShowPkg.setChecked(OptionValue.DEFAULT_VIEW_AREA_TXT_SHOW_PKG);

        AlertManager.viewAreaTxtSingleLine = OptionValue.DEFAULT_VIEW_AREA_TXT_SINGLE_LINE;
        SPUtils.setViewAreaTxtSingleLine(OptionValue.DEFAULT_VIEW_AREA_TXT_SINGLE_LINE);
        binding.swViewAreaTxtSingleLine.setChecked(OptionValue.DEFAULT_VIEW_AREA_TXT_SINGLE_LINE);

        AlertManager.viewAreaWriteToLog = OptionValue.DEFAULT_VIEW_AREA_WRITE_TO_LOG;
        SPUtils.setViewAreaWriteToLog(OptionValue.DEFAULT_VIEW_AREA_WRITE_TO_LOG);
        binding.swViewAreaWriteLog.setChecked(OptionValue.DEFAULT_VIEW_AREA_WRITE_TO_LOG);
    }

    private void updateViewAreaTxtSizeState(boolean fromUser) {
        binding.tvViewAreaTxtSize.setText(getString(R.string.settings_view_area_text_size, ViewAreaItemView.textSize));
        if (!fromUser) {
            binding.sbViewAreaTxtSize.setProgress(ViewAreaItemView.textSize);
        }
    }

    private int getCheckedViewAreaTypeId() {
        int type = AlertManager.viewAreaType;
        switch (type) {
            case ViewAreaType.ALL:
                return R.id.rb_view_area_type_1;
            case ViewAreaType.IMPORTANT:
                return R.id.rb_view_area_type_2;
            case ViewAreaType.CONTAIN_TEXT:
                return R.id.rb_view_area_type_3;
            default:
                throw new RuntimeException("Unsupported type: " + type);
        }
    }

    private void updateViewAreaColorState() {
        binding.ivViewAreaColor.setBackgroundColor(Color.argb(
                ViewAreaItemView.alpha, ViewAreaItemView.red, ViewAreaItemView.green, ViewAreaItemView.blue
        ));
    }

    private void updateViewAreaRedState(boolean fromUser) {
        binding.tvRed.setText(getString(R.string.settings_red, ViewAreaItemView.red, Integer.toHexString(ViewAreaItemView.red).toUpperCase()));
        if (!fromUser) {
            binding.sbRed.setProgress(ViewAreaItemView.red);
        }
    }

    private void updateViewAreaGreenState(boolean fromUser) {
        binding.tvGreen.setText(getString(R.string.settings_green, ViewAreaItemView.green, Integer.toHexString(ViewAreaItemView.green).toUpperCase()));
        if (!fromUser) {
            binding.sbGreen.setProgress(ViewAreaItemView.green);
        }
    }

    private void updateViewAreaBlueState(boolean fromUser) {
        binding.tvBlue.setText(getString(R.string.settings_blue, ViewAreaItemView.blue, Integer.toHexString(ViewAreaItemView.blue).toUpperCase()));
        if (!fromUser) {
            binding.sbBlue.setProgress(ViewAreaItemView.blue);
        }
    }

    private void updateViewAreaAlphaState(boolean fromUser) {
        binding.tvAlpha.setText(getString(R.string.settings_alpha, ViewAreaItemView.alpha, Integer.toHexString(ViewAreaItemView.alpha).toUpperCase()));
        if (!fromUser) {
            binding.sbAlpha.setProgress(ViewAreaItemView.alpha);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
