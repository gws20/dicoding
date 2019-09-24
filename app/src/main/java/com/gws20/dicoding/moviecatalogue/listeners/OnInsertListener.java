package com.gws20.dicoding.moviecatalogue.listeners;

import java.util.List;

public interface OnInsertListener {
    void OnSuccess(Long idList);
    void OnFailed(String message);
}