package com.henrydyc.housefinder;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    ArrayList<String> texts = new ArrayList<String>();
    ArrayList<Drawable> images = new ArrayList<Drawable>();
    String fullAddress;
    ImageSwitcher imageSwitcher;
    TextSwitcher textSwitcherTitle;
    TextSwitcher textSwitcherAddress;

    int index = 0; //current page: 0 is one-year, 1 is five-year, 2 is ten-year
    final int NUM_IMAGES = 3;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ResultActivity activity = (ResultActivity) getActivity();
        final HashMap<String, String> data = activity.getData();
        final ArrayList<Bitmap> bitmap_images = activity.getImages();
        fullAddress = activity.getFullAddress();
        Log.d("DEBUG", "In HistoryFragment: After getting images");

        for (int i = 0; i < bitmap_images.size(); i++) {
            images.add(new BitmapDrawable(getResources(), bitmap_images.get(i)));
        }
        Log.d("DEBUG", "In HistoryFragment: After getting data");


        //Prepare texts
        texts.add("Historical Zestimate for the past 1 year");
        texts.add("Historical Zestimate for the past 5 years");
        texts.add("Historical Zestimate for the past 10 years");


        Log.d("DEBUG", "After processing images");

        textSwitcherTitle = (TextSwitcher) (view.findViewById(R.id.textSwitcherTitle));
        textSwitcherTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getActivity());
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                return textView;
            }
        });
        textSwitcherAddress = (TextSwitcher) (view.findViewById(R.id.textSwitcherAddress));
        textSwitcherAddress.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                return textView;
            }
        });
        textSwitcherTitle.setInAnimation(getActivity(), android.R.anim.fade_in);
        textSwitcherTitle.setOutAnimation(getActivity(), android.R.anim.fade_out);
        textSwitcherAddress.setInAnimation(getActivity(), android.R.anim.fade_in);
        textSwitcherAddress.setOutAnimation(getActivity(), android.R.anim.fade_out);

        imageSwitcher = (ImageSwitcher) (view.findViewById(R.id.imageSwitcher));
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        imageSwitcher.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        imageSwitcher.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        textSwitcherTitle.setText(texts.get(0));
        textSwitcherAddress.setText(fullAddress);
        imageSwitcher.setBackground(images.get(0));


        //Set button click listeners
        Button button_prev = (Button) (view.findViewById(R.id.button_prev));
        Button button_next = (Button) (view.findViewById(R.id.button_next));
        button_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTextViewPrev();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTextViewNext();
            }
        });


        // Zillow links
        TextView termsOfUse = (TextView) (view.findViewById(R.id.termsOfUse));
        Linkify.addLinks(termsOfUse, Linkify.ALL);
        termsOfUse.setMovementMethod(LinkMovementMethod.getInstance());
        TextView whatIsZestimate = (TextView) (view.findViewById(R.id.whatIsZestimate));
        Linkify.addLinks(whatIsZestimate, Linkify.ALL);
        whatIsZestimate.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    private void switchTextViewPrev() {
        if (index == 0) index = NUM_IMAGES;
        index--;
        textSwitcherTitle.setText(texts.get(index));
        textSwitcherAddress.setText(fullAddress);
        imageSwitcher.setBackground(images.get(index));
    }

    private void switchTextViewNext() {
        if (index == 2) index = -1;
        index++;
        textSwitcherTitle.setText(texts.get(index));
        textSwitcherAddress.setText(fullAddress);
        imageSwitcher.setBackground(images.get(index));
    }


}