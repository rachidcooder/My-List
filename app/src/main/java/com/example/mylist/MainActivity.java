package com.example.mylist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
  Adapter_C adapter_obj;
 private TextView showdaylist,showweeklist,showmonthlist;
 ListView day_list,week_list,month_list;
 DB_methods db;
private FloatingActionButton fab;
int listnum=0;
int list_sh_n=0;
 AdView mAdView;
 boolean isAppRuning=false;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar_ID);
        day_list=findViewById(R.id.list_days);
        week_list=findViewById(R.id.list_weekly);
        month_list=findViewById(R.id.list_montly);
        showdaylist=findViewById(R.id.showdaylist);
        showweeklist=findViewById(R.id.showeeklist);
        showmonthlist=findViewById(R.id.showmonthlist);
        fab=findViewById(R.id.fab);
        showdaylist=findViewById(R.id.showdaylist);

        //Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);


        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My List");
        // make lists non visible :
        //
        db=DB_methods.getInstanse(this);
        //  objects :

        showdaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(day_list.getVisibility()==View.GONE){

                    list_sh_n=1;
                    gonweeklist();
                    gonmonthlist();
                    if(db!=null){
                    db.open();
                    adapter_obj=new Adapter_C( db.getTasks(list_sh_n));
                    db.close();
                    day_list.setAdapter(adapter_obj);
                       }
                    Showdaylist();   }
                else{
                    day_list.setVisibility(View.GONE);

                }
            }
        });
        showweeklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(week_list.getVisibility()==View.GONE){
                    list_sh_n=2;
                    gonmonthlist();
                    gondaylist();
                    if(db!=null){
                    db.open();
                        adapter_obj=new Adapter_C(db.getTasks(list_sh_n));
                    db.close();
                    week_list.setAdapter(adapter_obj);
                        Showweeklist();
                    }
                }

                else{
                    week_list.setVisibility(View.GONE);
                  //  Toast.makeText(getBaseContext(),"week list  was visible"+adapter_objW.isEmpty(),Toast.LENGTH_LONG).show();
                }
            }
        });
        showmonthlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(month_list.getVisibility()==View.GONE){

                    gondaylist();
                    gonweeklist();
                    list_sh_n=3;
                    if(db!=null){
                    db.open();
                    adapter_obj=new Adapter_C(db.getTasks(list_sh_n));
                    db.close();
                    month_list.setAdapter(adapter_obj);}
                    Showmonthlist();  }
                else{
                    month_list.setVisibility(View.GONE);
                }
            }
        });
       fab.setOnClickListener(new View.OnClickListener() {
          // @SuppressLint("ResourceAsColor")
           @Override
           public void onClick(View view) {
               View popup = getLayoutInflater().inflate(R.layout.add_item, null);
               AlertDialog dialog;
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setView(popup);
               dialog = builder.create();
               dialog.show();
               Button cancel, done;
               EditText task_name,task_date;
               RadioGroup rg = popup.findViewById(R.id.listype_id);
               cancel = popup.findViewById(R.id.canecel);
               done = popup.findViewById(R.id.done);
               task_name = popup.findViewById(R.id.task_name);
               task_date = popup.findViewById(R.id.task_date);
               ImageView img_calender=popup.findViewById(R.id.calender);
               // get Date from my  device :
               task_date.setEnabled(false);
               // listenrs :
               rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(RadioGroup radioGroup, int i) {
                       listnum=0;
                       switch (radioGroup.getCheckedRadioButtonId()){
                           case  R.id.radday:
                               listnum=1;
                               break;
                           case  R.id.radweek:
                               listnum=2;
                               break;
                           case  R.id.rad_month:
                               listnum=3;
                               break;
                       }
                   }
               });
               img_calender.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                     CalendarView calendarView;
                       View popup_calender = getLayoutInflater().inflate(R.layout.calender_layout, null);
                       AlertDialog dialog_c;
                       AlertDialog.Builder builder_c = new AlertDialog.Builder(MainActivity.this);
                       builder_c.setView(popup_calender);
                       dialog_c = builder_c.create();
                       dialog_c.show();
                       calendarView=popup_calender.findViewById(R.id.calendarView);
                       TextView finish=popup_calender.findViewById(R.id.finish);
                       TextView cansel_calender=popup_calender.findViewById(R.id.cancel_calender);
                       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                           @Override
                           public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                              task_date.setText(String.valueOf(i2)+"/"+String.valueOf(i1+1)+"/"+String.valueOf(i));
                           }
                       });
                       finish.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                              // task_date.setText(String.valueOf(i2)+"/"+String.valueOf(i1+1)+"/"+String.valueOf(i));
                               SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                             String date_get=sdf.format(calendarView.getDate()) ;
                             if(task_date !=null) {
                                 task_date.setText(date_get.toString());
                             }else{
                                 task_date.setText(date_get);
                             }
                               dialog_c.dismiss();
                           }
                       });
                       cansel_calender.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               dialog_c.dismiss();
                           }
                       });
                   }
               });
               cancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog.dismiss();
                   }
               });
               done.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String task_n=task_name.getText().toString();
                       String task_d=task_date.getText().toString();
                     if(listnum!=0 &&task_n!=null&&task_d!=null){

                         db.open();
                      db.insert_M(new listItem(task_n,task_d),listnum);
                         db.close();
                         switch (listnum){
                             case 1:
                                 if(day_list.getVisibility()==View.GONE){

                                     list_sh_n=1;
                                     gonweeklist();
                                     gonmonthlist();
                                     if(db!=null){
                                         db.open();
                                         adapter_obj=new Adapter_C( db.getTasks(list_sh_n));
                                         db.close();
                                         day_list.setAdapter(adapter_obj);
                                     }
                                     Showdaylist();   }
                                 break;
                             case 2 :
                                 if(week_list.getVisibility()==View.GONE){
                                     list_sh_n=2;
                                     gonmonthlist();
                                     gondaylist();
                                     if(db!=null){
                                         db.open();
                                         adapter_obj=new Adapter_C(db.getTasks(list_sh_n));
                                         db.close();
                                         week_list.setAdapter(adapter_obj);
                                         Showweeklist();
                                     }
                                 }
                                 break;
                             case 3:
                                 if(month_list.getVisibility()==View.GONE){

                                     gondaylist();
                                     gonweeklist();
                                     list_sh_n=3;
                                     if(db!=null){
                                         db.open();
                                         adapter_obj=new Adapter_C(db.getTasks(list_sh_n));
                                         db.close();
                                         month_list.setAdapter(adapter_obj);}
                                     Showmonthlist();  }
                                 break;
                         }
                         listnum=0;
                         dialog.dismiss();

                     }else{
                         Toast.makeText(getBaseContext(),"please fill all the fields",Toast.LENGTH_LONG).show();
                     }

                   }
               });
               LoadAdsInlies();

           }});
    }

           //some Methods to help :
           public void Gonlists() {
        day_list.setVisibility(View.GONE);
        week_list.setVisibility(View.GONE);
        month_list.setVisibility(View.GONE);
           }

           public void Showdaylist() {
               day_list.setVisibility(View.VISIBLE);
           }
           public void Showweeklist() {
                  week_list.setVisibility(View.VISIBLE);
           }
           public void Showmonthlist() {
               month_list.setVisibility(View.VISIBLE);
           }
    public void gondaylist() {
        day_list.setVisibility(View.GONE);
        LoadAdsInlies();
    }
    public void gonweeklist() {
        week_list.setVisibility(View.GONE);
        LoadAdsInlies();
    }
    public void gonmonthlist() {
        month_list.setVisibility(View.GONE);
        LoadAdsInlies();
    }
    //Ads Method
    private void LoadAdsInlies(){

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if(mInterstitialAd!=null)
                            mInterstitialAd.show(MainActivity.this);
                      //  Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                     //   Log.d("TAG", loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
         isAppRuning = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRuning=false;
    }

           // Adapter class :
    public class Adapter_C extends BaseAdapter {

        ArrayList<listItem> arrylist=new ArrayList<>();

               public Adapter_C(ArrayList<listItem> arrylist) {
                   this.arrylist = arrylist;
               }

               @Override
               public int getCount() {
                   return arrylist.size();
               }

               @Override
               public Object getItem(int i) {
                   return null;
               }

               @Override
               public long getItemId(int i) {
                   return 0;
               }

               @Override
               public View getView(int i, View view, ViewGroup viewGroup) {
                   final int i1=i;
                   View v=getLayoutInflater().inflate(R.layout.item_style,null);
                   TextView task_name=v.findViewById(R.id.item_text_id);
                   TextView task_date=v.findViewById(R.id.item_date_id);
                   ImageView delet =v.findViewById(R.id.delet_id);

                   task_name.setText(arrylist.get(i).task_name);
                   task_date.setText(arrylist.get(i).task_date);

                   delet.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           db.open();
                           String task_text=arrylist.get(i1).getTask_name();
                           listItem ls=db.getTask(list_sh_n,task_text);
                           db.delet_M(ls,list_sh_n);
                           adapter_obj=new Adapter_C( db.getTasks(list_sh_n));
                           day_list.setAdapter(adapter_obj);
                           db.close();

                       }
                   });

                   return v;
               }
           }




}