package com.example.myapplication;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;

public class FileListFragment extends Fragment implements FileAdapter.FileItemEventListener {
    private String path;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path=getArguments().getString("path");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        recyclerView = view.findViewById(R.id.rv_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        File currentFolder=new File(path);
        File[] files=currentFolder.listFiles();
       fileAdapter = new FileAdapter(Arrays.asList(files),this);
        recyclerView.setAdapter(fileAdapter);
        TextView pathTv=view.findViewById(R.id.tv_files_path);
        pathTv.setText(currentFolder.getName().equalsIgnoreCase("files") ? "External Storage:":currentFolder.getName());


        view.findViewById(R.id.iv_files_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    @Override
    public void onFileItemClick(File file) {
        if(file.isDirectory()){
            ((MainActivity) getActivity()).listFile(path + File.separator + file.getName());
        }
    }

    public void createNewFolder(String folderName){
            File newFoldere=new File(path + File.separator + folderName);
            if (!newFoldere.exists()){
                if (newFoldere.mkdir()){
                    fileAdapter.addFile(newFoldere);
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        }
    }
