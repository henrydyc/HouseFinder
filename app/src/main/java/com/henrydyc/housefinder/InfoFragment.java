package com.henrydyc.housefinder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Must add this line in to get the correct inflated view,
        //otherwise findViewById will fail
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ResultActivity activity = (ResultActivity) getActivity();
        final HashMap<String, String> data = activity.getData();

        //homedetails url link
        TextView link = (TextView) (view.findViewById(R.id.link));
        String homedetails = data.get("homedetails");
        String fullAddress = data.get("street") + ", " + data.get("city") + ", " + data.get("state")
                + "-" + data.get("zipcode");
        link.setText(Html.fromHtml(
                "<a href=\"" + homedetails + "\">" + fullAddress + "</a>"));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        //Property Type
        TextView propertyType = (TextView) (view.findViewById(R.id.propertyType));
        propertyType.setText(data.get("useCode"));

        //Year Built
        TextView yearBuilt = (TextView) (view.findViewById(R.id.yearBuilt));
        yearBuilt.setText(data.get("yearBuilt"));

        //Lot Size
        TextView lotSize = (TextView) (view.findViewById(R.id.lotSize));
        lotSize.setText(data.get("lotSizeSqFt"));

        //Finished Area
        TextView finishedArea = (TextView) (view.findViewById(R.id.finishedArea));
        finishedArea.setText(data.get("finishedSqFt"));

        //Bathrooms
        TextView bathrooms = (TextView) (view.findViewById(R.id.bathrooms));
        bathrooms.setText(data.get("bathrooms"));

        //Bedrooms
        TextView bedrooms = (TextView) (view.findViewById(R.id.bedrooms));
        bedrooms.setText(data.get("bedrooms"));

        //Tax Assessment Year
        TextView taxAssessmentYear = (TextView) (view.findViewById(R.id.taxAssessmentYear));
        taxAssessmentYear.setText(data.get("taxAssessmentYear"));

        //Tax Assessment
        TextView taxAssessment = (TextView) (view.findViewById(R.id.taxAssessment));
        taxAssessment.setText(data.get("taxAssessment"));

        //Last Sold Price
        TextView lastSoldPrice = (TextView) (view.findViewById(R.id.lastSoldPrice));
        lastSoldPrice.setText(data.get("lastSoldPrice"));

        //Last Sold Date
        TextView lastSoldDate = (TextView) (view.findViewById(R.id.lastSoldDate));
        lastSoldDate.setText(data.get("lastSoldDate"));

        //Property Estimate
        TextView propertyEstimateDate = (TextView) (view.findViewById(R.id.propertyEstimateDate));
        propertyEstimateDate.setText("Zestimate® Property Estimate as of " + data.get("estimateLastUpdate"));
        TextView propertyEstimateAmount = (TextView) (view.findViewById(R.id.propertyEstimateAmount));
        propertyEstimateAmount.setText(data.get("estimateAmount"));

        //30 Days Overall Change
        ImageView thirtyDaysOverallChangeArrow = (ImageView) (view.findViewById(R.id.thirtyDaysOverallChangeArrow));
        String overallChangeSign = data.get("estimateValueChangeSign");
        if (overallChangeSign.equals("+"))
            thirtyDaysOverallChangeArrow.setImageResource(R.drawable.up_arrow);
        else
            thirtyDaysOverallChangeArrow.setImageResource(R.drawable.down_arrow);
        TextView thirtyDaysOverallChange = (TextView) (view.findViewById(R.id.thirtyDaysOverallChange));
        thirtyDaysOverallChange.setText(data.get("estimateValueChange"));

        //All Time property range
        TextView allTimePropertyRange = (TextView) (view.findViewById(R.id.allTimePropertyRange));
        allTimePropertyRange.setText(data.get("estimateValuationRangeLow") + " - " + data.get("estimateValuationRangeHigh"));


        //Rent Estimate
        TextView rentEstimateDate = (TextView) (view.findViewById(R.id.rentEstimateDate));
        rentEstimateDate.setText("Rent Zestimate® Valuation as of " + data.get("restimateLastUpdate"));
        TextView rentEstimateAmount = (TextView) (view.findViewById(R.id.rentEstimateAmount));
        rentEstimateAmount.setText(data.get("restimateAmount"));

        //30 Days Rent Change
        ImageView thirtyDaysRentChangeArrow = (ImageView) (view.findViewById(R.id.thirtyDaysRentChangeArrow));
        String rentChangeSign = data.get("restimateValueChangeSign");
        if (rentChangeSign.equals("+"))
            thirtyDaysRentChangeArrow.setBackgroundResource(R.drawable.up_arrow);
        else
            thirtyDaysRentChangeArrow.setBackgroundResource(R.drawable.down_arrow);
        TextView thirtyDaysRentChange = (TextView) (view.findViewById(R.id.thirtyDaysRentChange));
        thirtyDaysRentChange.setText(data.get("restimateValueChange"));

        //All Time rent range
        TextView allTimeRentRange = (TextView) (view.findViewById(R.id.allTimeRentRange));
        allTimeRentRange.setText(data.get("restimateValuationRangeLow") + " - " + data.get("restimateValuationRangeHigh"));


        //Zillow links
        TextView termsOfUse = (TextView) (view.findViewById(R.id.termsOfUse));
        Linkify.addLinks(termsOfUse, Linkify.ALL);
        termsOfUse.setMovementMethod(LinkMovementMethod.getInstance());
        TextView whatIsZestimate = (TextView) (view.findViewById(R.id.whatIsZestimate));
        Linkify.addLinks(whatIsZestimate, Linkify.ALL);
        whatIsZestimate.setMovementMethod(LinkMovementMethod.getInstance());

        // Inflate the layout for this fragment
        return view;
    }


}
