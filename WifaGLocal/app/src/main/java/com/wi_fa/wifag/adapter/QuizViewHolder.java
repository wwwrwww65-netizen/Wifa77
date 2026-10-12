package com.wi_fa.wifag.adapter;

/**
 * Created by ahmedalalimi on 14/09/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class QuizViewHolder extends RecyclerView.ViewHolder{

    public ImageView quizImage;
    public TextView quizName;
    public View view;

    public QuizViewHolder(View itemView) {
        super(itemView);
        //quizImage = (ImageView)itemView.findViewById(R.id.selected_quiz_image);
        //quizName = (TextView)itemView.findViewById(R.id.selected_quiz_name);
        view = itemView;
    }
}
