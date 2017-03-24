package pl.cyrzan.prowadzpatryk.ui.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Product;
import pl.cyrzan.prowadzpatryk.service.preferences.model.UserPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Patryk on 21.03.2017.
 */

public class ProductsPreferencesDialog extends DialogFragment {

    private static final String TAG = "ProductsPreferencesDia";
    private static final String ARG_USER_PREF = "userPreferences";

    @BindView(R.id.products_recyclerView)
    RecyclerView recyclerView;

    private ProductsPreferencesAdapter adapter;
    private OnProductsChangedListener listener;

    private UserPreferences userPreferences;

    public static ProductsPreferencesDialog newInstance(UserPreferences userPreferences){
        ProductsPreferencesDialog dialog = new ProductsPreferencesDialog();
        Bundle args = new Bundle();
        args.putParcelable("userPreferences", userPreferences);
        dialog.setArguments(args);
        return dialog;
    }

    public void setListener(OnProductsChangedListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.userPreferences = getArguments().getParcelable(ARG_USER_PREF);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.fragment_products_dialog, container, false);
        ButterKnife.bind(this, v);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductsPreferencesAdapter(getContext());
        recyclerView.setAdapter(adapter);

        adapter.setItems(userPreferences.getProductList());

        return v;
    }

    public interface OnProductsChangedListener {
        void onProductsChanged(UserPreferences userPreferences);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick(){
        List<Product> productList = adapter.getItems();
        userPreferences.setProductList(productList);
        listener.onProductsChanged(userPreferences);
        getDialog().cancel();
    }

    @OnClick(R.id.cancelButton)
    public void onCancelButtonClick(){
        getDialog().cancel();
    }
}
