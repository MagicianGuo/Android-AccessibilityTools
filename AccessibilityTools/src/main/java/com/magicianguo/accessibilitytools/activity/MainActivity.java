package com.magicianguo.accessibilitytools.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.databinding.ActivityMainBinding;
import com.magicianguo.accessibilitytools.util.AlertManager;
import com.magicianguo.accessibilitytools.util.PermissionUtils;
import com.magicianguo.accessibilitytools.util.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PermissionUtils.checkNotificationPermission(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAccessibilityState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initView() {
        binding.btnGoAccessibility.setOnClickListener(v -> {
            PermissionUtils.goAccessibilityPage(this);
            ToastUtils.shortCall(R.string.toast_please_select_my_app);
        });
        binding.btnGoAppDetail.setOnClickListener(v -> {
            PermissionUtils.goAppDetailPage(this);
        });
        updateBtnViewExplorerState(AlertManager.isViewExplorerViewShowing);
        binding.btnViewExplorer.setOnClickListener(v -> {
            if (checkAlertPermission()) {
                boolean selected = v.isSelected();
                if (selected) {
                    AlertManager.hideViewExplorerView();
                } else {
                    AlertManager.showViewExplorerView();
                }
                updateBtnViewExplorerState(!selected);
            }
        });
        updateBtnTurnPageState(AlertManager.isTurnPageViewShowing);
        binding.btnTurnPage.setOnClickListener(v -> {
            if (checkAlertPermission()) {
                boolean selected = v.isSelected();
                if (selected) {
                    AlertManager.hideTurnPageView();
                } else {
                    AlertManager.showTurnPageView();
                }
                updateBtnTurnPageState(!selected);
            }
        });
        updateBtnClickToolsState(AlertManager.isClickToolsViewShowing);
        binding.btnClickTools.setOnClickListener(v -> {
            if (checkAlertPermission()) {
                boolean selected = v.isSelected();
                if (selected) {
                    AlertManager.hideClickToolsView();
                } else {
                    AlertManager.showClickToolsView();
                }
                updateBtnClickToolsState(!selected);
            }
        });
        binding.btnPalette.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaletteActivity.class);
            startActivity(intent);
        });
        binding.btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void updateBtnViewExplorerState(boolean showing) {
        if (showing) {
            binding.btnViewExplorer.setSelected(true);
            binding.btnViewExplorer.setText(R.string.view_explorer_tools_on);
        } else {
            binding.btnViewExplorer.setSelected(false);
            binding.btnViewExplorer.setText(R.string.view_explorer_tools_off);
        }
    }

    private void updateBtnTurnPageState(boolean showing) {
        if (showing) {
            binding.btnTurnPage.setSelected(true);
            binding.btnTurnPage.setText(R.string.turn_page_tools_on);
        } else {
            binding.btnTurnPage.setSelected(false);
            binding.btnTurnPage.setText(R.string.turn_page_tools_off);
        }
    }

    private void updateBtnClickToolsState(boolean showing) {
        if (showing) {
            binding.btnClickTools.setSelected(true);
            binding.btnClickTools.setText(R.string.click_tools_on);
        } else {
            binding.btnClickTools.setSelected(false);
            binding.btnClickTools.setText(R.string.click_tools_off);
        }
    }

    private void updateAccessibilityState() {
        boolean enabled = PermissionUtils.isAccessibilityEnabled();
        binding.tvAccessibilityState.setText(enabled ? R.string.accessibility_enabled : R.string.accessibility_disabled);
        binding.btnViewExplorer.setEnabled(enabled);
        binding.btnTurnPage.setEnabled(enabled);
        binding.btnClickTools.setEnabled(enabled);
    }

    private boolean checkAlertPermission() {
        if (PermissionUtils.hasAlertPermission()) {
            return true;
        } else {
            PermissionUtils.requestAlertPermission(this);
            ToastUtils.shortCall(R.string.toast_please_select_my_app);
            return false;
        }
    }
}