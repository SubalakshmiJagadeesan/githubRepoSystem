package com.example.githubreposystem.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubreposystem.R;
import com.example.githubreposystem.model.Repository;
import com.example.githubreposystem.ui.detail.DetailFragment;

import java.util.List;

public class DashboardFragment extends Fragment implements RVAdapterInterface {

    private DashboardViewModel dashboardViewModel;
    RecyclerView rvRepos;
    TextView tvNoItem;
    ProgressBar progressBar;
    boolean isFragmentReplaced = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rvRepos = root.findViewById(R.id.rv_repos);
        tvNoItem = root.findViewById(R.id.tv_placeholder);
        progressBar = root.findViewById(R.id.progressBar);
        rvRepos.setLayoutManager(new LinearLayoutManager(getActivity()));

        dashboardViewModel.getRepositories().observe(getViewLifecycleOwner(), new Observer<List<Repository>>() {
            @Override
            public void onChanged(@Nullable List<Repository> repositories) {
                if (null != repositories && repositories.size() > 0) {
                    rvRepos.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    tvNoItem.setVisibility(View.GONE);
                    rvRepos.setAdapter(new RepositoriesRVAdapter(repositories.subList(0, 10), getContext(), DashboardFragment.this));
                }
            }
        });
        dashboardViewModel.getErrorText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onClickItem(Repository repository) {

        replaceFragment(DetailFragment.newInstance(repository));
    }

    @Override
    public void onFinalItemReached(int position) {
        RepositoriesRVAdapter adapter = ((RepositoriesRVAdapter) rvRepos.getAdapter());
        if (null != adapter) {
            adapter.addItems(dashboardViewModel.getRepositories().getValue().subList(adapter.getItemCount(), adapter.getItemCount() + 10));
        }
    }

    private void replaceFragment(Fragment newFragment) {
        isFragmentReplaced = true;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        if (!newFragment.isAdded()) {
            try {
                transaction.add(R.id.detailPlaceholder, newFragment, "detailFragment");
                transaction.commit();
            } catch (Exception e) {
                // TODO: handle exception
                // AppConstants.printLog(e.getMessage());
            }
        }
    }

    public void onBackPressed() {
        if (isFragmentReplaced) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            Fragment detailFragment = manager.findFragmentByTag("detailFragment");
            if (detailFragment instanceof DetailFragment) {
                ((DetailFragment) detailFragment).onBackPressed();
            }
            isFragmentReplaced = false;
        } else {
            getActivity().onBackPressed();
        }
    }

    public boolean canAllowBack() {
        return isFragmentReplaced;
    }
}