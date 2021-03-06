package com.stopgroup.stopcar.client.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;


/**
 * Created by pc on 27/02/2017.
 */

public class Adapter_Viewpager extends PagerAdapter {

// this adapter  is for images slider at image details
    Context context;
    ArrayList<String> list;
//    int[] imageId = {R.drawable.img, R.drawable.imgg, R.drawable.imggg, R.drawable.imgggg, R.drawable.imggggg};
    public Adapter_Viewpager(Context context, ArrayList<String> list){
        this.context = context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Auto-generated method stub
        container.removeView((View) object);
    }
    ImageView imageView ;
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.custom_viewpager, container, false);
        imageView = viewItem.findViewById(R.id.custom_img_viewpager);

        Picasso.get().load(list.get(position).trim()).into(imageView);

        container.addView(viewItem);
        return viewItem;
    }

}
