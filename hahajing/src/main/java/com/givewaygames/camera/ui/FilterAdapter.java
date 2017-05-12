package com.givewaygames.camera.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.shader.ProgramSet;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.picasso.Picasso;
import it.sephiroth.android.library.widget.HListView;

public class FilterAdapter extends BaseAdapter {
    public static final SparseArray<Integer> shaderNameLookup = new SparseArray();
    final LayoutInflater inflater;
    boolean isInverted = false;
    final Picasso picasso;
    final ProgramSet programSet;
    final AppState state;

    static {
        populateShaderNames();
    }

    public FilterAdapter(Context context, ProgramSet programSet) {
        this.inflater = LayoutInflater.from(context);
        this.programSet = programSet;
        this.picasso = Picasso.with(context);
        this.state = AppState.getInstance();
    }

    public void setIsInverted(boolean isInverted) {
        this.isInverted = isInverted;
    }

    public boolean isInverted() {
        return this.isInverted;
    }

    public int getCount() {
        return this.programSet.size();
    }

    public GLProgram getItem(int position) {
        return (GLProgram) this.programSet.get((int) getItemId(position));
    }

    public long getItemId(int position) {
        return this.isInverted ? (long) ((this.programSet.size() - position) - 1) : (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isSelected;
        int i = 0;
        View root = convertView;
        if (root == null) {
            root = this.inflater.inflate(R.layout.view_single_filter, parent, false);
        }
        int shaderId = getItem(position).getTag();
        ((TextView) root.findViewById(R.id.filter_name)).setText(((Integer) shaderNameLookup.get(shaderId, Integer.valueOf(0))).intValue());
        ImageView imageView = (ImageView) root.findViewById(R.id.filter);
        this.picasso.load(getResourceForShader(shaderId)).fit().centerCrop().into(imageView);
        if (this.state.getCurrentFilter() == ((int) getItemId(position))) {
            isSelected = true;
        } else {
            isSelected = false;
        }
        View reload = root.findViewById(R.id.reload);
        if (!isSelected) {
            i = 8;
        }
        reload.setVisibility(i);
        return root;
    }

    public void animate(int pos, ListView listView) {
        if (VERSION.SDK_INT >= 14) {
            innerAnimation(getViewByPosition(pos, listView));
        }
    }

    public void animate(int pos, HListView listView) {
        if (VERSION.SDK_INT >= 14) {
            innerAnimation(getViewByPosition(pos, listView));
        }
    }

    @TargetApi(14)
    private void innerAnimation(View root) {
        if (root != null && root.getVisibility() == 0) {
            View rotate = root.findViewById(R.id.reload);
            rotate.setVisibility(0);
            rotate.animate().rotationBy(BitmapDescriptorFactory.HUE_CYAN).setDuration(250).start();
        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = (listView.getChildCount() + firstListItemPosition) - 1;
        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        }
        return listView.getChildAt(pos - firstListItemPosition);
    }

    public View getViewByPosition(int pos, HListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = (listView.getChildCount() + firstListItemPosition) - 1;
        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        }
        return listView.getChildAt(pos - firstListItemPosition);
    }

    public int getResourceForShader(int shader) {
        switch (shader) {
            case 1:
                return R.drawable.filter_1_circular_ripple;
            case 2:
                return R.drawable.filter_2_emboss;
            case 3:
                return R.drawable.filter_3_inverted;
            case 4:
            case 20:
                return R.drawable.filter_4_cell_shaded;
            case 6:
                return R.drawable.filter_6_old_photo;
            case 7:
                return R.drawable.filter_7_sobel;
            case 8:
                return R.drawable.filter_8_swirl;
            case 9:
                return R.drawable.filter_9_glass_sphere;
            case 10:
                return R.drawable.filter_10_small_circles;
            case 11:
                return R.drawable.filter_11_neon;
            case 12:
                return R.drawable.filter_12_motion;
            case 13:
                return R.drawable.filter_13_dilate;
            case 14:
                return R.drawable.filter_14_predator;
            case 15:
                return R.drawable.filter_15_ripple;
            case 16:
                return R.drawable.filter_16_stain_glass;
            case 18:
                return R.drawable.filter_20_nightvision;
            case 19:
                return R.drawable.filter_19_skinny_fat;
            case 21:
                return R.drawable.filter_21_frozen_glass;
            case 22:
                return R.drawable.filter_22_oilify;
            case 23:
                return R.drawable.filter_23_sketch;
            case 24:
                return R.drawable.filter_24_leopard;
            case 25:
                return R.drawable.filter_25_shapes;
            case 27:
                return R.drawable.filter_27_extract_color;
            case 28:
                return R.drawable.filter_28_colorblind;
            case 29:
                return R.drawable.filter_29_color_matrix;
            case 30:
                return R.drawable.filter_30_mirror;
            case 31:
                return R.drawable.filter_31_hue_shift;
            default:
                return R.drawable.filter_5_bloom;
        }
    }

    private static void populateShaderNames() {
        shaderNameLookup.put(0, Integer.valueOf(R.string.none));
        shaderNameLookup.put(1, Integer.valueOf(R.string.circular_ripple));
        shaderNameLookup.put(2, Integer.valueOf(R.string.emboss));
        shaderNameLookup.put(3, Integer.valueOf(R.string.inverted));
        shaderNameLookup.put(4, Integer.valueOf(R.string.comic_book));
        shaderNameLookup.put(5, Integer.valueOf(R.string.bloom));
        shaderNameLookup.put(6, Integer.valueOf(R.string.old_photo));
        shaderNameLookup.put(7, Integer.valueOf(R.string.sobel));
        shaderNameLookup.put(8, Integer.valueOf(R.string.swirl));
        shaderNameLookup.put(9, Integer.valueOf(R.string.glass_sphere));
        shaderNameLookup.put(10, Integer.valueOf(R.string.small_circles));
        shaderNameLookup.put(11, Integer.valueOf(R.string.neon));
        shaderNameLookup.put(12, Integer.valueOf(R.string.motion_blur));
        shaderNameLookup.put(13, Integer.valueOf(R.string.dilate));
        shaderNameLookup.put(14, Integer.valueOf(R.string.predator));
        shaderNameLookup.put(15, Integer.valueOf(R.string.ripple));
        shaderNameLookup.put(16, Integer.valueOf(R.string.stain_glass));
        shaderNameLookup.put(18, Integer.valueOf(R.string.night_vision));
        shaderNameLookup.put(19, Integer.valueOf(R.string.skinny_fat_mirror));
        shaderNameLookup.put(20, Integer.valueOf(R.string.comic_book));
        shaderNameLookup.put(21, Integer.valueOf(R.string.frozen_glass));
        shaderNameLookup.put(22, Integer.valueOf(R.string.oilify));
        shaderNameLookup.put(23, Integer.valueOf(R.string.sketch));
        shaderNameLookup.put(24, Integer.valueOf(R.string.leopard));
        shaderNameLookup.put(25, Integer.valueOf(R.string.shapes));
        shaderNameLookup.put(27, Integer.valueOf(R.string.extract_color));
        shaderNameLookup.put(28, Integer.valueOf(R.string.colorblind));
        shaderNameLookup.put(29, Integer.valueOf(R.string.color_matrix));
        shaderNameLookup.put(30, Integer.valueOf(R.string.mirror));
        shaderNameLookup.put(31, Integer.valueOf(R.string.color_shift));
        shaderNameLookup.put(32, Integer.valueOf(R.string.conehead));
        shaderNameLookup.put(33, Integer.valueOf(R.string.big_ears));
        shaderNameLookup.put(34, Integer.valueOf(R.string.big_forehead));
        shaderNameLookup.put(35, Integer.valueOf(R.string.pointy_chin));
        shaderNameLookup.put(36, Integer.valueOf(R.string.bug_eyes));
        shaderNameLookup.put(37, Integer.valueOf(R.string.squinty_eyes));
        shaderNameLookup.put(38, Integer.valueOf(R.string.tiny_mouth));
        shaderNameLookup.put(39, Integer.valueOf(R.string.fat_chin));
        shaderNameLookup.put(40, Integer.valueOf(R.string.square_nose));
        shaderNameLookup.put(41, Integer.valueOf(R.string.monkey_mouth));
        shaderNameLookup.put(42, Integer.valueOf(R.string.chubby_cheeks));
        shaderNameLookup.put(43, Integer.valueOf(R.string.spiky_hair));
        shaderNameLookup.put(44, Integer.valueOf(R.string.double_bump_forehead));
        shaderNameLookup.put(45, Integer.valueOf(R.string.gorilla_nose));
    }
}
