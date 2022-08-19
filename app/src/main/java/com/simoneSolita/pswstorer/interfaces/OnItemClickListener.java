package com.simonesolita.pswstorer.interfaces;

public interface OnItemClickListener {
    void OnItemClick(String id);
    void OnItemDelete(String id);
    void OnItemEdit(String id);
    void OnLongItemClick(String id);
    void OnCopyPassword(String id);
    void OnCopyUtenza(String id);
}
