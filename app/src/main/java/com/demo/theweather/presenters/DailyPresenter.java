//package com.demo.theweather.presenters;
//
//import com.demo.theweather.contracts.DailyContract;
//import com.demo.theweather.contracts.MainContract;
//import com.demo.theweather.model.DailyRepository;
//import com.demo.theweather.model.MainRepository;
//
//public class DailyPresenter implements DailyContract.Presenter, DailyRepository.Callback {
//    private DailyContract.View view;
//    private DailyContract.Repository repository;
//    private String location;
//
//
//    public DailyPresenter(DailyContract.View view) {
//        this.view = view;
//        this.repository = new DailyRepository(this);
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public void onDestroy() {
//
//    }
//
//    @Override
//    public void setLocationKey(String locationKey) {
//
//    }
//}
