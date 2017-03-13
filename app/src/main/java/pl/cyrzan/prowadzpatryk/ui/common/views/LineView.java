package pl.cyrzan.prowadzpatryk.ui.common.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.opentripplanner.routing.core.TraverseMode;
import org.opentripplanner.v092snapshot.api.model.Leg;

import pl.cyrzan.prowadzpatryk.R;

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

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode()) {
            setOrientation(LinearLayout.HORIZONTAL);
            init(context, attrs);
        }
    }

    public void setLeg(Leg leg){
        if (leg.getMode().equals(TraverseMode.TRANSIT.toString()) || leg.getMode().equals(TraverseMode.WALK.toString())) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), MainUtils.getDrawableForProduct(leg.getMode()));
            product.setImageDrawable(drawable);
            label.setText(leg.getRoute());
        } else {
            product.setVisibility(GONE);
            label.setVisibility(GONE);
        }
    }

    private void init(Context context, AttributeSet attrs){
        View.inflate(context, R.layout.line_view, this);
        ButterKnife.bind(this);

        setBackground(ContextCompat.getDrawable(context, R.drawable.line_box));

        int height = context.getResources().getDimensionPixelSize(R.dimen.line_box_height);
        setMinimumHeight(height);
    }
}
