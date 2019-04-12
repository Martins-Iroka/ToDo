package com.martdev.android.todo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martdev.android.todo.R;
import com.martdev.android.todo.repository.model.ToDo;
import com.martdev.android.todo.utils.ToDoDateUtils;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoHolder> {
    private final Context mContext;

    private final ToDoOnItemClickHandler mClickHandler;
    private List<ToDo> mToDoList;

    public interface ToDoOnItemClickHandler {
        void clickItem(ToDo toDo);
    }

    class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mWhatToDo;
        private TextView mToDoDate;
        private TextView mToDoCompleted;
        ToDoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.todo_list_item, parent, false));
            itemView.setOnClickListener(this);

            mWhatToDo = itemView.findViewById(R.id.todo_text);
            mToDoDate = itemView.findViewById(R.id.todo_date);
            mToDoCompleted = itemView.findViewById(R.id.todo_completed_text);
        }

        void bind(ToDo toDo, Context context) {
            mWhatToDo.setText(toDo.getWhatToDo());
            long timeStamp = toDo.getDate().getTime();
            String date = ToDoDateUtils.getReadableDate(timeStamp);
            mToDoDate.setText(date);
            if (toDo.isCompleted()) {
                int color = context.getResources().getColor(R.color.colorPrimary);
                mToDoCompleted.setText(R.string.todo_done);
                mToDoCompleted.setTextColor(color);
            } else {
                int color = context.getResources().getColor(android.R.color.holo_red_light);
                mToDoCompleted.setText(R.string.todo_not_done);
                mToDoCompleted.setTextColor(color);
            }
        }

        @Override
        public void onClick(View v) {
            int id = getAdapterPosition();
            ToDo toDo = mToDoList.get(id);
            mClickHandler.clickItem(toDo);
        }
    }


    public ToDoAdapter(Context context, ToDoOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ToDoHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        ToDo toDo = mToDoList.get(position);
        holder.bind(toDo, mContext);
    }

    public void setToDoList(List<ToDo> toDoList) {
        mToDoList = toDoList;
        notifyDataSetChanged();
    }

    public List<ToDo> getToDoList() {
        return mToDoList;
    }

    @Override
    public int getItemCount() {
        if (mToDoList != null)
            return mToDoList.size();
        return 0;
    }
}
