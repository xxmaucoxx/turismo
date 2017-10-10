package com.example.astrid.turismo.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.R;
import com.example.astrid.turismo.models.Post;
import com.example.astrid.turismo.models.Product;

import java.util.List;
import java.util.Objects;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {

    List<Product> products;
    private Context context;

    private static final String TAG = "MyActivity";

    public AdapterProduct(List<Product> products, Context context){
        this.products = products;
        this.context = context;
    }

    @Override
    public AdapterProduct.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        AdapterProduct.ProductViewHolder holder = new AdapterProduct.ProductViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);

        Log.i(TAG,"--------------->>mirenn" +product.getNameArticle() );
        holder.txt_name_product.setText(product.getNameArticle());
        holder.txt_name_description.setText(product.getDescription());
        holder.txt_name_precio.setText(product.getPrecioArticle());
        String url = product.getUrlImg();

        Glide.with(context)
                .load(url)
                .crossFade()
                .centerCrop()
                .into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void adicionarListaProduct(Product value) {
        products.add(value);
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView img_product;
        TextView txt_name_product;
        TextView txt_name_description;
        TextView txt_name_precio;

        public ProductViewHolder(View itemView) {
            super(itemView);
            txt_name_product = (TextView) itemView.findViewById(R.id.txt_name_product);
            txt_name_description = (TextView) itemView.findViewById(R.id.txt_name_description);
            txt_name_precio = (TextView) itemView.findViewById(R.id.txt_name_precio);
            img_product = (ImageView) itemView.findViewById(R.id.img_product);
        }
    }

}
