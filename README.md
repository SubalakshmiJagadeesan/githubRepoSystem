# githubRepoSystem
Githup repository system
In this Demo application we used the following frameworks to list out the https://api.github.com/repositories items

1) Retrofit is to get all the items from the  above api. In retrofit folder two files are there
    1) RetrofitClient.java
        This file is for providing instance of Retrofit
    2) ApiInterface.java
        This is the interface file . The Annotations on the interface methods and its parameters indicate how a request will be handled.
    3) DashboardViewModel.java
        This file is the  extends of ViewModel Jetpack library and it will provide the  list of all repositories to fragments

2) RecyclerView  is used to list out all  the repositories with the help of RepositoriesRVAdapter and RVAdapterInterface

3) Fragments  are used to segregate  the mainactivity UI and navigated to  the respected screen use of the TabsPagerAdapter