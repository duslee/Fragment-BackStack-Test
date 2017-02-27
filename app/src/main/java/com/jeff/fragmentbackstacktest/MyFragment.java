package com.jeff.fragmentbackstacktest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jeff on 2016/12/2.
 */

public class MyFragment extends Fragment {

    private static final String KEY_FOR_TAG = "keyForTag";

    private String mTag;

    public static MyFragment newInstance(String tag) {
        MyFragment fragment = new MyFragment();

        Bundle args = new Bundle();
        args.putString(KEY_FOR_TAG, tag);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_test, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTag = getArguments().getString(KEY_FOR_TAG);
        ((TextView) view.findViewById(R.id.textView)).setText(mTag);

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).addFragmentToTop(mTag, mTag + "-1");
            }
        });

        view.findViewById(R.id.replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replace(mTag, mTag + "-replace");
            }
        });

        view.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).remove(mTag);
            }
        });
    }
}
