package com.example.simbirplanner.repository;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import javax.inject.Inject;

public class FireStoreService {

    @Inject
    FirebaseStorage storage;

    private StorageReference storageRef;
    private StorageReference imageRef;

    public FireStoreService(FirebaseStorage firebaseStorage) {
        this.storage = firebaseStorage;
        this.storageRef = this.storage.getReference();
    }

    public void uploadImageEvent(Uri uri,OnCompletedCallback callback){
        imageRef = storageRef.child("images/").child(UUID.randomUUID().toString());
        UploadTask uploadTask = imageRef.putFile(uri);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    String url = task.getResult().toString();
                    callback.onUploadImageSuccess(url);
                }
            }
        });
    }

    public interface OnCompletedCallback{
        void onUploadImageSuccess(String url);
        void onUploadImageError();
    }
}
