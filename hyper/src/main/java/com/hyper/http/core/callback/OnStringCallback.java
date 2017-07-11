package com.hyper.http.core.callback;

public interface OnStringCallback extends OnFailureCallback {
    void onStringSuccess(final String result);
}
