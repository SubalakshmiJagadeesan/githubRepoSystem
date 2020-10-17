package com.example.githubreposystem.ui.dashboard;

import com.example.githubreposystem.model.Repository;

public interface RVAdapterInterface {

    public void onClickItem(Repository repository);
    public void onFinalItemReached(int position);
}
