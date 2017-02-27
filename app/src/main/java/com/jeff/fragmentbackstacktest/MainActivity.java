package com.jeff.fragmentbackstacktest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FOR_MAIN = "tagForMain";

    private Stack<String> mTagStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String tag = "1";
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, MyFragment.newInstance(tag), tag)
//                .addToBackStack(null)
                .commit();
        mTagStack.push(tag);
        showStack();
    }

    public void addFragmentToTop(final String oldTag, final String newTag) {
        FragmentManager fm = getSupportFragmentManager();

        Log.d("test", "MainActivity addFragmentToTop: 111 oldTag = " + oldTag + ", newTag = " + newTag);
        Fragment fragment = fm.findFragmentByTag(oldTag);
        Log.d("test", "MainActivity addFragmentToTop: fragment = " + fragment);
        FragmentTransaction ft = fm
                .beginTransaction()
                .hide(fragment)
                .add(R.id.content, MyFragment.newInstance(newTag), newTag);
        ft.addToBackStack(newTag);
        ft.commit();
        mTagStack.push(newTag);
        showStack();
    }

    /**
     * replace方法把已有的Fragment全部移除后，再添加
     *
     * @param tag
     */
    public void replace(String tag) {
        Log.d("test", "MainActivity replace: 222 tag = " + tag);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MyFragment.newInstance(tag), tag)
//                .addToBackStack(TAG_FOR_MAIN)
                .commit();
        mTagStack.set(mTagStack.size() - 1, tag);
        showStack();
    }

    public void replace(final String oldTag, final String newTag) {
        FragmentManager fm = getSupportFragmentManager();

        Log.d("test", "MainActivity replace: 222 oldTag = " + oldTag + ", newTag = " + newTag);

        Fragment oldFragment = fm.findFragmentByTag(oldTag);
        FragmentTransaction ft = fm.beginTransaction();

        ft.remove(oldFragment)
                .add(R.id.content, MyFragment.newInstance(newTag), newTag)
                .commit();
        mTagStack.set(mTagStack.size() - 1, newTag);
        showStack();
    }

    public void remove(String tag) {
        FragmentManager fm = getSupportFragmentManager();

        Log.d("test", "MainActivity remove: 333 tag = " + tag);
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        Fragment removeFragment = fm.findFragmentByTag(tag);
        Log.d("test", "MainActivity remove: 333 removeFragment = " + removeFragment);

        showStack();
        FragmentTransaction ft = fm.beginTransaction();

        ft.remove(removeFragment);
        if (mTagStack.size() >= 2) {
            Fragment showFragment = fm
                    .findFragmentByTag(mTagStack.get(mTagStack.size() - 2));
            Log.d("test", "MainActivity remove: 333 showFragment = " + showFragment);
            ft.show(showFragment);
        }
        ft.commit();

        Log.d("test", "MainActivity remove: mTagStack.pop() = " + mTagStack.pop());
        Log.d("test", "MainActivity remove: tag = " + tag);
        Log.d("test", "MainActivity remove 111: backStack.size = " + fm.getBackStackEntryCount());
        fm.popBackStack();
        Log.d("test", "MainActivity remove 222: backStack.size = " + fm.getBackStackEntryCount());

        showStack();
    }

    private void showStack() {
        Log.d("test", "MainActivity showStack 111: Stack.size = " + mTagStack.size());
        StringBuilder sb = new StringBuilder("Stack =");
        for (int i = 0; i < mTagStack.size(); i++) {
            if (i == 0) {
                sb.append(mTagStack.get(i));
                continue;
            }

            sb.append(", " + mTagStack.get(i));
        }
        Log.d("test", "MainActivity showStack 222: " + sb.toString());
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("test", "MainActivity onBackPressed: backStackCount = " + backStackCount);
        if (backStackCount > 0) {
            remove(mTagStack.peek());
        } else
            super.onBackPressed();
    }
}
