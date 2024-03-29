package com.ruitu.arad;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Preferences {
    SharedPreferences sharedPrefs;
    Editor editor;

    public Preferences(SharedPreferences preferences) {
        this.sharedPrefs = preferences;
    }

    public Preferences putBoolean(String key, boolean val) {
        edit();
        editor.putBoolean(key, val);
        return this;
    }

    public Preferences putInteger(String key, int val) {
        edit();
        editor.putInt(key, val);
        return this;
    }

    public Preferences putLong(String key, long val) {
        edit();
        editor.putLong(key, val);
        return this;
    }

    public Preferences putFloat(String key, float val) {
        edit();
        editor.putFloat(key, val);
        return this;
    }

    public Preferences putString(String key, String val) {
        edit();
        editor.putString(key, val);
        return this;
    }

    public Preferences putStringSet(String key, Set<String> set) {
        edit();
        editor.putStringSet(key, set);
        return this;
    }

    public Preferences put(Map<String, ?> vals) {
        edit();
        for (Entry<String, ?> val : vals.entrySet()) {
            if (val.getValue() instanceof Boolean)
                putBoolean(val.getKey(), (Boolean) val.getValue());
            if (val.getValue() instanceof Integer)
                putInteger(val.getKey(), (Integer) val.getValue());
            if (val.getValue() instanceof Long)
                putLong(val.getKey(), (Long) val.getValue());
            if (val.getValue() instanceof String)
                putString(val.getKey(), (String) val.getValue());
            if (val.getValue() instanceof Float)
                putFloat(val.getKey(), (Float) val.getValue());
            if (val.getValue() instanceof Set) {
                putStringSet(val.getKey(), (Set<String>) val.getValue());
            }
        }
        return this;
    }

    public boolean getBoolean(String key) {
        return sharedPrefs.getBoolean(key, false);
    }

    public int getInteger(String key) {
        return sharedPrefs.getInt(key, 0);
    }

    public long getLong(String key) {
        return sharedPrefs.getLong(key, 0);
    }

    public float getFloat(String key) {
        return sharedPrefs.getFloat(key, 0);
    }

    public String getString(String key) {
        return sharedPrefs.getString(key, "");
    }

    public Set<String> getStringSet(String key) {
        return sharedPrefs.getStringSet(key, null);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPrefs.getBoolean(key, defValue);
    }

    public int getInteger(String key, int defValue) {
        return sharedPrefs.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPrefs.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPrefs.getFloat(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sharedPrefs.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPrefs.getStringSet(key, defValue);
    }

    public Map<String, ?> get() {
        return sharedPrefs.getAll();
    }

    public boolean contains(String key) {
        return sharedPrefs.contains(key);
    }

    public void clear() {
        edit();
        editor.clear();
    }

    public void flush() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }

    public Preferences remove(String key) {
        edit();
        editor.remove(key);
        return this;
    }

    private void edit() {
        if (editor == null) {
            editor = sharedPrefs.edit();
        }
    }
}
