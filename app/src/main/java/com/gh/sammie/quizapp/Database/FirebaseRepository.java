package com.gh.sammie.quizapp.Database;

import androidx.annotation.NonNull;

import com.gh.sammie.quizapp.Modal.QuizListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class FirebaseRepository {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = firebaseFirestore.collection("QuizList");

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;

    }

    public void getQuizData() {
        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                        onFirestoreTaskComplete.quizListDataAdded(Objects.requireNonNull(task.getResult()).toObjects(QuizListModel.class));
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void quizListDataAdded(List<QuizListModel> quizListModelsList);
        void onError(Exception e);
    }

}
