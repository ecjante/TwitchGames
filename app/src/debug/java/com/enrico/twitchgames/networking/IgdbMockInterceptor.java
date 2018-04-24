package com.enrico.twitchgames.networking;

import com.enrico.twitchgames.settings.DebugPreferences;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by enrico on 3/14/18.
 */

public class IgdbMockInterceptor implements Interceptor {

    private final IgdbMockResponseFactory igdbMockResponseFactory;
    private final DebugPreferences debugPreferences;

    @Inject
    IgdbMockInterceptor(IgdbMockResponseFactory igdbMockResponseFactory, DebugPreferences debugPreferences) {
        this.igdbMockResponseFactory = igdbMockResponseFactory;
        this.debugPreferences = debugPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (debugPreferences.useMockResponsesEnabled()) {
            String mockResponse = igdbMockResponseFactory.getMockResponse(chain.request());
            if (mockResponse != null) {

                return new Response.Builder()
                        .message("")
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/json"), mockResponse))
                        .build();
            }
        }
        return chain.proceed(chain.request());
    }
}
