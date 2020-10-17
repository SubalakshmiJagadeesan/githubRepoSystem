package com.example.githubreposystem.ui.detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubreposystem.R;
import com.example.githubreposystem.model.Repository;

public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";

    private Repository mRepository;

    public static DetailFragment newInstance(Repository param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepository = (Repository) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ((TextView) view.findViewById(R.id.tv_name)).setText(mRepository.getName());
        ((TextView) view.findViewById(R.id.tv_owner)).setText(mRepository.getOwner().getLogin());
        ((TextView) view.findViewById(R.id.tv_url)).setText(mRepository.getUrl());
        ((TextView) view.findViewById(R.id.tv_Comment)).setText("Comment : "+mRepository.getComment());
        Glide.with(getContext())
                .load(mRepository.getOwner().getAvatar_url())
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ImageView) view.findViewById(R.id.iv_avatar)));
        return view;
    }


    public void onBackPressed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(this);
        trans.commit();
        manager.popBackStack();
    }
}