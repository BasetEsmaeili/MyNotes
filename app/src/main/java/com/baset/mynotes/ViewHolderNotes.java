package com.baset.mynotes;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class ViewHolderNotes extends RecyclerView.ViewHolder {
    private TextView note;
    private TextView dot;
    private TextView date;
    private ImageView editBtn;
    private ImageView deletBtn;
    private ImageView setAlaram;

    public ViewHolderNotes(View itemView) {
        super(itemView);
        note = itemView.findViewById(R.id.note);
        dot = itemView.findViewById(R.id.dot);
        date = itemView.findViewById(R.id.timestamp);
        editBtn = itemView.findViewById(R.id.edit_btn);
        deletBtn = itemView.findViewById(R.id.delet_btn);
        setAlaram = itemView.findViewById(R.id.setAlaram);
    }

    public void bindData(final Note mnote, final InterfaceOnRecyclerViewItemClick onRecyclerViewItemClick) {
        note.setText(mnote.getNote());
        dot.setText(Html.fromHtml("&#8226;"));
        PersianDate pdate = new PersianDate();
        PersianDateFormat persianDateFormat = new PersianDateFormat("l j F");
        String perdate = persianDateFormat.format(pdate);
        date.setText(perdate + " " + "تاریخ:");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClick.onEditBtnClick(mnote.getNote(), getAdapterPosition());
            }
        });
        deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClick.onRemoveBtnClick(getAdapterPosition());
            }
        });
        setAlaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClick.onSetAlarmBtnClick(mnote, getAdapterPosition());
            }
        });

    }
}
