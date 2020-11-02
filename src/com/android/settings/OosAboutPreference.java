package com.android.settings;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

public class OosAboutPreference extends Preference {

    Context context;

    public OosAboutPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayoutResource(R.layout.oos_about);
    }

    public static String getSystemProperty(String key) {
        String value = null;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    private static void setInfo(String prop, TextView textview) {
        if (TextUtils.isEmpty(getSystemProperty(prop))) {
            textview.setText("Unknown");
	} else {
            if (prop.equals("ro.octavi.maintainer")) {
	        String str = getSystemProperty(prop);
		if (str.contains("_"))
                    str = str.replace("_", " ");
		textview.setText(str);
	    }
            textview.setText(getSystemProperty(prop));
        }
    }

    private static void setInfo(String prop, String prop2, TextView textview) {
        if (TextUtils.isEmpty(getSystemProperty(prop)) && TextUtils.isEmpty(getSystemProperty(prop2))) {
            textview.setText("Unknown");
	} else {
            textview.setText(String.format("v%s %s", getSystemProperty(prop), getSystemProperty(prop2)));
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        final boolean selectable = false;

        holder.itemView.setFocusable(selectable);
        holder.itemView.setClickable(selectable);

        ImageView deviceIcon = holder.itemView.findViewById(R.id.device_icon);
        TextView deviceName = holder.itemView.findViewById(R.id.device_model_name);
        TextView octStage = holder.itemView.findViewById(R.id.octavi_stage);
        TextView octMaintainer = holder.itemView.findViewById(R.id.octavi_maintainer);

        setInfo("ro.product.model", deviceName);
        setInfo("ro.octavi.status", "ro.octavi.branding.version", octStage);
        setInfo("ro.octavi.maintainer", octMaintainer);

        switch (getSystemProperty("ro.product.device").toLowerCase()) {
            case "x00td":
                deviceIcon.setImageResource(R.drawable.ic_device_x00td);
                break;
            case "violet":
                deviceIcon.setImageResource(R.drawable.ic_device_violet);
                break;
            case "tulip":
                deviceIcon.setImageResource(R.drawable.ic_device_tulip);
                break;
            case "rmx1921":
                deviceIcon.setImageResource(R.drawable.ic_device_rmx1921);
                break;
            case "rmx1901":
                deviceIcon.setImageResource(R.drawable.ic_device_rmx1901);
                break;
            case "rmx1801":
                deviceIcon.setImageResource(R.drawable.ic_device_rmx1801);
                break;
            case "mido":
                deviceIcon.setImageResource(R.drawable.ic_device_mido);
                break;
            case "sanders":
                deviceIcon.setImageResource(R.drawable.ic_device_sanders);
                break;
            case "tissot":
                deviceIcon.setImageResource(R.drawable.ic_device_tissot);
                break;
	    case "ginkgo":
		deviceIcon.setImageResource(R.drawable.ic_device_ginkgo);
		break;
            case "whyred":
                deviceIcon.setImageResource(R.drawable.ic_device_whyred);
                break;
            case "vince":
                deviceIcon.setImageResource(R.drawable.ic_device_vince);
                break;
            case "raphael":
                deviceIcon.setImageResource(R.drawable.ic_device_raphael);
                break;
           case "raphaelin":
                deviceIcon.setImageResource(R.drawable.ic_device_raphael);
                break;
            case "beryllium":
                deviceIcon.setImageResource(R.drawable.ic_device_beryllium);
                break;
            default:
		deviceIcon.setImageResource(R.drawable.ic_default_device);
                break;
        }

        holder.setDividerAllowedAbove(false);
        holder.setDividerAllowedBelow(false);
    }
}

