package com.magicianguo.accessibilitytools.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.constant.ViewAreaType;
import com.magicianguo.accessibilitytools.databinding.ActivitySettingBinding;
import com.magicianguo.accessibilitytools.util.AlertManager;
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
