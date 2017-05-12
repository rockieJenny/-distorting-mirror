package com.givewaygames.ads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.givewaygames.ads.HouseAds.HouseAd;
import java.util.List;

public class HouseAdAdapter extends BaseAdapter {
    final List<HouseAd> ads;
    final HouseAds houseAds;
    final LayoutInflater inflater;

    public HouseAdAdapter(Context context, HouseAds houseAds, List<HouseAd> ads) {
        this.inflater = LayoutInflater.from(context);
        this.ads = ads;
        this.houseAds = houseAds;
    }

    public int getCount() {
        return this.ads.size();
    }

    public HouseAd getItem(int position) {
        return (HouseAd) this.ads.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View root = convertView;
        if (convertView == null) {
            root = this.inflater.inflate(R.layout.view_single_ad, parent, false);
        }
        this.houseAds.loadHouseAd(root, getItem(position), false);
        return root;
    }
}
