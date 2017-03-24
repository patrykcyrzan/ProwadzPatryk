package pl.cyrzan.prowadzpatryk.ui.common.dialogs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Product;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;
import pl.cyrzan.prowadzpatryk.ui.mapwithform.ProductsAdapter;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Patryk on 24.03.2017.
 */

public class ProductsPreferencesAdapter extends RecyclerView.Adapter<ProductsPreferencesAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductsPreferencesAdapter(Context context){
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductsPreferencesAdapter.ProductViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product_selectable, parent, false));
    }

    public void setItems(List<Product> productList) {
        this.productList = productList;
        this.notifyDataSetChanged();
    }

    public List<Product> getItems(){
        return this.productList;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        TraverseMode mode = product.getTraverseMode();

        holder.title.setText(MainUtil.getNameForProduct(context, product.getTraverseMode()));

        Drawable drawable = ContextCompat.getDrawable(context, MainUtil.getDrawableForProduct(mode));
        if(product.isAvailable()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.image.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        if(productList != null) {
            return productList.size();
        } else {
            return 0;
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_title)
        TextView title;
        @BindView(R.id.product_image)
        ImageView image;
        @BindView(R.id.product_checkbox)
        AppCompatCheckBox checkBox;
        @BindView(R.id.item_layout)
        LinearLayout linearLayout;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                Product product = productList.get(getAdapterPosition());
                product.setAvailable(isChecked);
                productList.set(getAdapterPosition(), product);
            });
        }

        @OnClick(R.id.item_layout)
        public void onLayoutClick(){
            checkBox.setChecked(!checkBox.isChecked());
        }

    }
}
