package com.water.context;

import com.water.entity.TokenBind;

public class WaterContext{
    private static final String TOKEN_BIND_KEY = "tokenBind";
    public static void setTokenBind(TokenBind tokenBind){
        BaseContext.set(TOKEN_BIND_KEY,tokenBind);
    }
    public static TokenBind getTokenBind(){
        return (TokenBind) BaseContext.get(TOKEN_BIND_KEY);
    }

    public static void remove() {
        BaseContext.remove();
    }
}
