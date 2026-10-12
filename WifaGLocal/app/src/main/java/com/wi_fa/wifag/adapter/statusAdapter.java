package com.wi_fa.wifag.adapter;

/**
 * Created by ahmedalalimi on 14/09/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class statusAdapter extends RecyclerView.Adapter<QuizViewHolder> {
    private Context context;
    private int layoutResource;

    public statusAdapter(Context context, int layout) {
        this.context = context;
        this.layoutResource = layout;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        //holder.quizName.setText(quizObject.getQuizName());
        //holder.quizImage.setImageResource(R.drawable.profile);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent profileIntent = new Intent(context, MainActivity.class);
                //context.startActivity(profileIntent);
                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
