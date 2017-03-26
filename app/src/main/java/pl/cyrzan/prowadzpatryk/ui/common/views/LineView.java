package pl.cyrzan.prowadzpatryk.ui.common.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Leg;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Patryk on 13.03.2017.
 */

public class LineView extends LinearLayout {

    private final static String TAG = "LineView";

    @BindView(R.id.productView)
    ImageView product;
    @BindView(R.id.labelView)
    TextView label;

    private ViewGroup view;

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode()) {
            setOrientation(LinearLayout.HORIZONTAL);
            init(context, attrs);
        }
    }

    public LineView(Context context) {
        super(context, null);
    }

    public void setLeg(Leg leg){
        GradientDrawable box = (GradientDrawable) view.getBackground();
        if (leg.getMode().isTransit()) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), MainUtil.getDrawableForProduct(leg.getMode()));
            product.setImageDrawable(drawable);
            label.setText(leg.getRoute());
            box.mutate();
            view.setBackgroundColor(MainUtil.getColorForProduct(getContext(), leg.getMode()));
        } else if(leg.getMode().isWalk()) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), MainUtil.getDrawableForProduct(leg.getMode()));
            product.setImageDrawable(drawable);
            label.setText(leg.getRoute());
            view.setBackgroundColor(Color.TRANSPARENT);
        } else {
            product.setVisibility(GONE);
            label.setVisibility(GONE);
        }
    }

    private void init(Context context, AttributeSet attrs){
        View.inflate(context, R.layout.line_view, this);
        ButterKnife.bind(this);

        view = this;

        view.setBackground(ContextCompat.getDrawable(context, R.drawable.line_box));

        int height = context.getResources().getDimensionPixelSize(R.dimen.line_box_height);
        setMinimumHeight(height);
    }
}
