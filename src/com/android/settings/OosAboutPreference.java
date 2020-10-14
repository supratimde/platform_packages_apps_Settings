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
        if (TextUtils.isEmpty(getSystemProperty(prop))) {
            textview.setText("Unknown");
        } if (!TextUtils.isEmpty(getSystemProperty(prop2)) && !TextUtils.isEmpty(getSystemProperty(prop))) {
            if (prop2.equals("ro.processor.model")) {
                String model = getSystemProperty(prop2);
                if (model.contains("_"))
                model = model.replace("_", " ");
                textview.setText(getSystemProperty(prop)+" coupled with "+model);
	    } else textview.setText("Unknown");
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

        setInfo("ro.product.model", "ro.processor.model", deviceName);
        setInfo("ro.octavi.status", "ro.octavi.branding.version", octStage);
        setInfo("ro.octavi.maintainer", octMaintainer);

        switch (getSystemProperty("ro.product.system.name")) {
            case "mido":
                deviceIcon.setImageResource(R.drawable.ic_device_mido);
                break;
            case "sanders":
                deviceIcon.setImageResource(R.drawable.ic_device_sanders);
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
            default:
		deviceIcon.setImageResource(R.drawable.ic_default_device);
                break;
        }

        holder.setDividerAllowedAbove(false);
        holder.setDividerAllowedBelow(false);
    }
}

