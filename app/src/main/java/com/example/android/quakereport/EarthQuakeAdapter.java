package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    List<EarthQuake> earthQuakes;

    public EarthQuakeAdapter(Context context, List<EarthQuake> objects) {
        super(context, 0, objects);
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthQuakes = earthQuakes;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        EarthQuake currentEarthquake = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_item,parent,false);
        TextView txtMagnitud = convertView.findViewById(R.id.txtMagnitud);
        TextView txtLocation = convertView.findViewById(R.id.txtLocation);
        TextView txtOffSet = convertView.findViewById(R.id.txtOffSet);
        TextView txtFecha = convertView.findViewById(R.id.txtFecha);
        TextView txtHora = convertView.findViewById(R.id.txtHora);

        txtMagnitud.setText(formatMagnitude(currentEarthquake.getMagnitude()));
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) txtMagnitud.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        String[] parts = formatPlace(currentEarthquake.getLocation());
        if (parts.length==2){
            txtOffSet.setText(parts[0].toUpperCase() + " OF");
            txtLocation.setText(parts[1]);
        }else{
            txtOffSet.setText(parts[0]);
            txtLocation.setText(parts[0]);
        }

        txtFecha.setText(formatDate(new Date(currentEarthquake.getTimeInMilliseconds())));
        txtHora.setText(formatTime(new Date(currentEarthquake.getTimeInMilliseconds())));
        return convertView;
    }

    private int getMagnitudeColor(double magnitude) {
        int color = R.color.magnitude1;
        int mag = (int) magnitude;
        switch (mag){
            case 2: color = R.color.magnitude2;break;
            case 3: color = R.color.magnitude3;break;
            case 4: color = R.color.magnitude4;break;
            case 5: color = R.color.magnitude5;break;
            case 6: color = R.color.magnitude6;break;
            case 7: color = R.color.magnitude7;break;
            case 8: color = R.color.magnitude8;break;
            case 9: color = R.color.magnitude9;break;
            case 10: color = R.color.magnitude10plus;break;
        }

        int magnitude1Color = ContextCompat.getColor(getContext(), color);
        return magnitude1Color;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(dateObject);
    }


    private String[] formatPlace(String place){
        String[] partes = {};
        partes = place.split(" of ");
        return partes;
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
