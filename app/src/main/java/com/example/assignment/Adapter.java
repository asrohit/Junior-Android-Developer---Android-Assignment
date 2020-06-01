package com.example.assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    Context context;
    ArrayList<Model> arrayList;
    DataBaseHelper dataBaseHelper;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        Model mModel = arrayList.get(position);

        Log.e("MODLE", "model " + mModel.id);
        final String id = mModel.getId();
        final String image = mModel.getImage();
        final String name = mModel.getName();
        final String model = mModel.getModel();
        final String varent = mModel.getVarent();
        final String fueltype = mModel.getType();
        final String addTimeStamp = mModel.getAddTimeStamp();
        final String updateTimeStamp = mModel.getUpdateTimeStamp();

        holder.img_getImageView.setImageURI(Uri.parse(image));
        holder.txt_getName.setText(name);
        holder.txt_getModel.setText(model);
        holder.txt_getVarent.setText(varent);
        holder.txt_getType.setText(fueltype);


        holder.img_edtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(
                        "" + holder.getAdapterPosition(),
                        "" + id,
                        "" + name,
                        "" + model,
                        "" + varent,
                        "" + image,
                        "" + fueltype,
                        "" + addTimeStamp,
                        "" + updateTimeStamp
                );
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteDialog(
                        holder.getAdapterPosition(),
                        "" + id
                );
                return true;
            }
        });

    }

    private void deleteDialog(final int position, final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Are You Want To Delete");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_delete_black_24dp);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dataBaseHelper.deleteInformation(id);
                dialogInterface.dismiss();
                arrayList.remove(position);
                notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void editDialog(String position,
                            final String id,
                            final String name,
                            final String model,
                            final String varent,
                            final String image,
                            final String fueltype,
                            final String addTimeStamp,
                            final String updateTimeStamp) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update");
        builder.setMessage("Are You Want To Update");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_edit_black_24dp);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("MODEL", model);
                intent.putExtra("VARENT", varent);
                intent.putExtra("FUELTYPE", fueltype);
                intent.putExtra("IMAGE", image);
                intent.putExtra("ADD_TIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATE_TIMESTAMP", updateTimeStamp);
                intent.putExtra("edit Mode", true);
                context.startActivity(intent);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_getImageView, img_edtImage;
        TextView txt_getName, txt_getModel, txt_getVarent, txt_getType;

        public MyViewHolder(@NonNull View item) {
            super(item);
            img_getImageView = item.findViewById(R.id.img_GetImage);
            img_edtImage = item.findViewById(R.id.imgEdit);
            txt_getName = item.findViewById(R.id.txt_GetvehicalName);
            txt_getModel = item.findViewById(R.id.txt_Getvehicalmodel);
            txt_getVarent = item.findViewById(R.id.txt_Getvarent);
            txt_getType = item.findViewById(R.id.txt_Getfueltype);
        }
    }
}
