package com.kmk.motatawera.doctor.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kmk.motatawera.doctor.R;
import com.kmk.motatawera.doctor.databinding.QuizlistLayoutBinding;
import com.kmk.motatawera.doctor.model.QuizModel;
import com.kmk.motatawera.doctor.model.SubjectModel;
import com.kmk.motatawera.doctor.ui.QuestionsListActivity;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private List<QuizModel> quizModelList;
    private Context context;
    private String branch;
    private OnQuizListener onQuizListener;
    private SubjectModel subjectModel;

    public QuizAdapter(List<QuizModel> quizModelList, Context context, OnQuizListener onQuizListener) {
        this.quizModelList = quizModelList;
        this.context = context;
        this.onQuizListener = onQuizListener;
    }

    @NonNull
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuizlistLayoutBinding quizlistLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.quizlist_layout, parent, false);
        return new QuizAdapter.ViewHolder(quizlistLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, int position) {

        QuizModel model = quizModelList.get(position);
        onQuizListener.OnLoadingListener(false);


        FirebaseFirestore.getInstance()
                .collection("subject")
                .document(model.getSubject_id())
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    subjectModel = documentSnapshot.toObject(SubjectModel.class);

                    if (subjectModel.getBranch() == 1) {
                        branch = "Haram";
                    } else {
                        branch = "Qtamia";
                    }

                    holder.quizlistLayoutBinding.txtBranch.setText(branch);
                    holder.quizlistLayoutBinding.txtSubject.setText(subjectModel.getName());

                    onQuizListener.OnLoadingListener(true);
                });

        holder.quizlistLayoutBinding.btnEditQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionsListActivity.class)
                    .putExtra("QuizID", model.getId())
                    .putExtra("subject_name", subjectModel.getName())
                    .putExtra("title", model.getTitle())
                    .putExtra("subject_branch", subjectModel.getBranch());
            context.startActivity(intent);
            notifyDataSetChanged();
        });
        if (model.getQuizActive()) {
            holder.quizlistLayoutBinding.btnStartquiz.setText(R.string.close);
            holder.quizlistLayoutBinding.isactive.setImageResource(R.drawable.btn_active);
        } else {
            holder.quizlistLayoutBinding.btnStartquiz.setText(R.string.start);
            holder.quizlistLayoutBinding.isactive.setImageResource(R.drawable.btn_notactive);
        }

        holder.quizlistLayoutBinding.txtQuiztitle.setText(model.getTitle());
        holder.quizlistLayoutBinding.btnStartquiz.setOnClickListener(v -> {
            onQuizListener.OnStartQuizListener(position, quizModelList);
            notifyDataSetChanged();
        });

        holder.quizlistLayoutBinding.btnDeleteQuiz.setOnClickListener(v -> {
            onQuizListener.OnDeleteQuizListener(position, quizModelList, context);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return quizModelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        QuizlistLayoutBinding quizlistLayoutBinding;

        public ViewHolder(@NonNull QuizlistLayoutBinding quizlistLayoutBinding) {
            super(quizlistLayoutBinding.getRoot());
            this.quizlistLayoutBinding = quizlistLayoutBinding;
        }

    }

    public void setList(List<QuizModel> quizModels) {
        this.quizModelList = quizModels;
        notifyDataSetChanged();
    }

    public interface OnQuizListener {

        void OnStartQuizListener(int position, List<QuizModel> quizList);

        void OnDeleteQuizListener(int position, List<QuizModel> quizList, Context context);

        void OnLoadingListener(boolean isLoaded);
    }
}
