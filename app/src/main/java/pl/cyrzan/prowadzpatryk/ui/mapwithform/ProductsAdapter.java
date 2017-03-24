package pl.cyrzan.prowadzpatryk.ui.mapwithform;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Product;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Patryk on 20.03.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList){
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductsAdapter.ProductViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        TraverseMode mode = product.getTraverseMode();

        Drawable drawable = ContextCompat.getDrawable(context, MainUtil.getDrawableForProduct(mode));;
        if(product.isAvailable()) {
            drawable = ContextCompat.getDrawable(context, MainUtil.getDrawableForProduct(mode));
        } else {
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.comet), PorterDuff.Mode.SRC_IN);
        }
        holder.imageView.setImageDrawable(drawable);
    }

    public void setItems(List<Product> productList) {
        this.productList = productList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productView)
        ImageView imageView;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /*@OnClick(R.id.text)
        public void onItemClick(){
            WrapLocation location = getSuggestLocationAtPosition(getAdapterPosition());
            onLocationInputAdapterActionListener.onItemClickListener(location, view);
        }*/

    }
}
