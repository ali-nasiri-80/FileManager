package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewFolderDialog.AddNewFolderCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(StorageHelper.isExternalStorageReadable()){
            File externalFilesDir = getExternalFilesDir(null);
            FileListFragment fileListFragment=new FileListFragment();
            listFile(externalFilesDir.getPath(),false);
        }


        findViewById(R.id.iv_main_addNewFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewFolderDialog().show(getSupportFragmentManager(),null);
            }
        });

    }
    public void listFile(String path,boolean addToBackStack){
        FileListFragment fileListFragment=new FileListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("path",path);
        fileListFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_fragmentContainer,fileListFragment );
        if(addToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    public void listFile(String path){
        this.listFile(path,true);
    }

    @Override
    public void onCreateFolderButtonClick(String folderName) {
        Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
        if(fragment instanceof FileListFragment){
            ((FileListFragment) fragment).createNewFolder(folderName);
        }


    }
}