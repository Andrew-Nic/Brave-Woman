package com.example.bravewoman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ProductosAdapter extends FirestoreRecyclerAdapter<ListProductos, ProductosAdapter.productosViewHolder> {

    public ProductosAdapter(@NonNull FirestoreRecyclerOptions<ListProductos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull productosViewHolder holder, int position, @NonNull ListProductos model) {
        holder.vNomProducto.setText(model.getmNombreProducto());
        holder.vNomNegocio.setText(model.getmNombreNegocio());
    }

    @NonNull
    @Override
    public productosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_publicaciones,parent,false);
        return new productosViewHolder(view);
    }

    class productosViewHolder extends RecyclerView.ViewHolder {
        private TextView vNomProducto, vNomNegocio;


        public productosViewHolder(@NonNull View itemView) {
            super(itemView);

            vNomProducto = itemView.findViewById(R.id.nombreProducto);
            vNomNegocio = itemView.findViewById(R.id.nombreNegocio);
        }
    }
}
