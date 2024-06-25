package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    List<File> files;
    private  FileItemEventListener fileItemEventListener;

    public FileAdapter(List<File> files, FileItemEventListener fileItemEventListener){
        this.files=new ArrayList<>(files);
        this.fileItemEventListener = fileItemEventListener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new FileViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_file, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.bindFile(files.get(position));

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public  class FileViewHolder extends RecyclerView.ViewHolder {
        private TextView fileNameTv;
        private ImageView fileIconIv;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTv=itemView.findViewById(R.id.tv_file_name);
            fileIconIv=itemView.findViewById(R.id.iv_file);
        }
        private void bindFile(File file){
            if (file.isDirectory()){
                fileIconIv.setImageResource(R.drawable.ic_add_folder_white_24dp);
            }
            else
                fileIconIv.setImageResource(R.drawable.ic_file_black_32dp);
            fileNameTv.setText(file.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileItemEventListener.onFileItemClick(file);
                }
            });
        }
    }
    public interface FileItemEventListener{
        void onFileItemClick(File file);
    }
    public void  addFile(File file){
        files.add(0,file);
        notifyItemInserted(0);
    }
}