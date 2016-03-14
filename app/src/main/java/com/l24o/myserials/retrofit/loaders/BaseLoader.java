package com.l24o.myserials.retrofit.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.l24o.myserials.retrofit.response.RequestResult;
import com.l24o.myserials.retrofit.response.Response;

import java.io.IOException;


/**
 * @author Alexander Popov created on 01.03.2016.
 */
public abstract class BaseLoader extends AsyncTaskLoader<Response> {

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Response loadInBackground() {
        try {
            Response response = apiCall();
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getContext());
                onSuccess();
            } else {
                onError();
            }
            return response;
        } catch (IOException e) {
            onError();
            return new Response();
        }
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected abstract Response apiCall() throws IOException;
}