package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

public class HonorHomeBadger implements Badger {

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Bundle bundle = new Bundle();
        bundle.putString("package", context.getPackageName());
        bundle.putString("class", componentName.getClassName());
        bundle.putInt("badgenumber", badgeCount);

        try {
            // Honor uses a different provider authority
            context.getContentResolver().call(
                    Uri.parse("content://com.hihonor.android.launcher.settings/badge/"),
                    "change_badge",
                    null,
                    bundle
            );
        } catch (Exception e) {
            throw new ShortcutBadgeException("Unable to execute badge on Honor launcher", e);
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.hihonor.android.launcher" // Honor launcher package
        );
    }
}