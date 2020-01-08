package com.example.user.ebooks;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.ebooks.utils.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements BookItemClickListener {

    private List<Slide> firstSlides ;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView BooksRV,BooksRvLastWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iniViews();
        iniSlider();
        iniPopularBooks();
        iniLastWeekBooks();


    }

    private void iniLastWeekBooks() {

        BookAdapter LastWeekBookAdapter = new BookAdapter(this,DataSource.getLastWeekBook(),this);
        BooksRvLastWeek.setAdapter(LastWeekBookAdapter);
        BooksRvLastWeek.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void iniPopularBooks() {
        // Recyclerview Setup
        // ini data

        BookAdapter movieAdapter = new BookAdapter(this, DataSource.getPopularBooks(),this);
        BooksRV.setAdapter(movieAdapter);
        BooksRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void iniSlider() {
        // prepare a list of slides ..
        firstSlides = new ArrayList<>() ;
        firstSlides.add(new Slide(R.drawable.dracula,"Dracula"));
        firstSlides.add(new Slide(R.drawable.tree,"The Tree"));
        firstSlides.add(new Slide(R.drawable.peterpan,"Peter Pan"));
        firstSlides.add(new Slide(R.drawable.poetryofart,"Poetry Of Art"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this,firstSlides);
        sliderpager.setAdapter(adapter);

        sliderpager.setAdapter(adapter);
        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);
    }

    private void iniViews() {
        sliderpager = findViewById(R.id.slider_pager) ;
        indicator = findViewById(R.id.indicator);
        BooksRV = findViewById(R.id.Rv_books);
        BooksRvLastWeek = findViewById(R.id.Rv_books_last_week);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBookClick(Book book, ImageView bookImageView) {


        Intent intent = new Intent(this,BookDetailActivity.class);

        intent.putExtra("title",book.getTitle());
        intent.putExtra("description",book.getDescription());
        intent.putExtra("imgURL",book.getThumbnail());
        intent.putExtra("imgCover",book.getCoverPhoto());
        // lets crezte the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,
                bookImageView,"sharedName");

        startActivity(intent,options.toBundle());



        // i l make a simple test to see if the click works

        Toast.makeText(this,"" + book.getTitle(),Toast.LENGTH_LONG).show();
        // it works great


    }

    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<firstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }





}




