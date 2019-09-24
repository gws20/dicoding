package com.gws20.dicoding.moviecatalogue.listeners;

import java.util.List;

public interface OnDeleteListener {
    void OnSuccess(int id); //the number of delete row
    void OnFailed(String message); //ids inserted
}
