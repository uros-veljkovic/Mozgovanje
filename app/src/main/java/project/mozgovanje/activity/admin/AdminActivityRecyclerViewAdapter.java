package project.mozgovanje.activity.admin;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.databinding.QuestionItemAdminBinding;
import project.mozgovanje.model.question.Question;

public class AdminActivityRecyclerViewAdapter extends RecyclerView.Adapter<AdminActivityRecyclerViewAdapter.ViewHolder> {

    private List<Question> pendingQuestions;
    private Context context;
    private OnItemManipulateListener listener;

    public AdminActivityRecyclerViewAdapter(Context context, List<Question> pendingQuestions) {
        this.pendingQuestions = pendingQuestions;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminActivityRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionItemAdminBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.question_item_admin,
                parent,
                false);

        return new AdminActivityRecyclerViewAdapter.ViewHolder(itemBinding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminActivityRecyclerViewAdapter.ViewHolder holder, int position) {
        Question question = pendingQuestions.get(position);

        holder.itemBinding.setQuestion(question);
        holder.itemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return pendingQuestions.size();
    }

    public void setQuestions(ArrayList<Question> pendingQuestions) {
        this.pendingQuestions = pendingQuestions;
    }

    public Question getQuestion(int position) {
        return pendingQuestions.get(position);
    }

    public void removeQuestion(int position) {
        pendingQuestions.remove(pendingQuestions.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private QuestionItemAdminBinding itemBinding;
        private OnItemManipulateListener mListener;
        private boolean editable;

        public ViewHolder(@NonNull QuestionItemAdminBinding itemView, final OnItemManipulateListener listener) {
            super(itemView.getRoot());
            itemBinding = itemView;
            this.mListener = listener;

            configBtnEdit(itemView);
            configBtnSave(itemView);
            configBtnDelete(itemView);
            editable = false;

        }

        private void configBtnDelete(@NonNull project.mozgovanje.databinding.QuestionItemAdminBinding itemView) {
            itemView.questionItemBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.deleteQuestion(position);
                        }
                    }
                }
            });
        }

        private void configBtnSave(@NonNull project.mozgovanje.databinding.QuestionItemAdminBinding itemView) {
            itemView.questionItemBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.createQuestion(position);
                        }
                    }
                }
            });
        }

        private void configBtnEdit(@NonNull final project.mozgovanje.databinding.QuestionItemAdminBinding itemView) {
            itemView.questionItemBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        changeMode();
                        if(editable){
                            itemBinding.questionItemBtnEdit.setBackgroundResource(R.drawable.ic_save);
                            enableOrDisableFields(itemView);
                        }else{
                            //TODO: binding.notify() i update firestore

                            itemBinding.questionItemBtnEdit.setBackgroundResource(R.drawable.ic_pen);
                            enableOrDisableFields(itemView);
                            mListener.updateQuestion(position);
                        };
                    }
                }
            });
        }

        private void changeMode() {
            editable = !editable;
        }

        private void enableOrDisableFields(@NonNull project.mozgovanje.databinding.QuestionItemAdminBinding itemView) {
/*            itemView.questionItemTvQuestion.setFocusable(editable);
            itemView.questionItemTvAnswer1.setFocusable(editable);
            itemView.questionItemTvAnswer2.setFocusable(editable);
            itemView.questionItemTvAnswer3.setFocusable(editable);
            itemView.questionItemTvAnswer4.setFocusable(editable);
            itemView.questionItemTvQuestion.setEnabled(editable);
            itemView.questionItemTvAnswer1.setEnabled(editable);
            itemView.questionItemTvAnswer2.setEnabled(editable);
            itemView.questionItemTvAnswer3.setEnabled(editable);
            itemView.questionItemTvAnswer4.setEnabled(editable);*/
            int inputType;
            if(editable){
                inputType = InputType.TYPE_CLASS_TEXT;
            }else{
                inputType = InputType.TYPE_NULL;
            }
            itemView.questionItemTvQuestion.setInputType(inputType);
            itemView.questionItemTvAnswer1.setInputType(inputType);
            itemView.questionItemTvAnswer2.setInputType(inputType);
            itemView.questionItemTvAnswer3.setInputType(inputType);
            itemView.questionItemTvAnswer4.setInputType(inputType);
            itemView.notifyChange();
        }

    }

    public interface OnItemManipulateListener {
        void deleteQuestion(int position);

        void updateQuestion(int position);

        void createQuestion(int position);
    }

    public void setOnItemManipulateListener(OnItemManipulateListener listener) {
        this.listener = listener;
    }

}
