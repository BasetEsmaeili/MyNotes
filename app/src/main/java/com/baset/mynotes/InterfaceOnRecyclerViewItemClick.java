package com.baset.mynotes;

public interface InterfaceOnRecyclerViewItemClick {
    void onRemoveBtnClick(int position);

    void onEditBtnClick(String note, int position);

    void onSetAlarmBtnClick(Note note, int position);
}
