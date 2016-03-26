package com.l24o.myserials.retrofit.loaders;

import android.content.Context;

import com.l24o.myserials.models.Serial;
import com.l24o.myserials.retrofit.ApiFactory;
import com.l24o.myserials.retrofit.RetrofitService;
import com.l24o.myserials.retrofit.response.SerialResponse;
import com.l24o.myserials.retrofit.response.RequestResult;
import com.l24o.myserials.retrofit.response.Response;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * @author Alexander Popov created on 01.03.2016.
 */
public class RetorfitLoader extends BaseLoader {

    private final int from;
    private final String keyword;

    public RetorfitLoader(Context context, int from, String keyword) {
        super(context);
        this.from = from;
        this.keyword = keyword;
    }

    @Override
    protected Response apiCall() throws IOException {
        RetrofitService service = ApiFactory.getService();
        Call<List<Serial>> call = service.getSerials(keyword, from/20, 20);
        List<Serial> serials = call.execute().body();
        return new SerialResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(serials);
    }
}