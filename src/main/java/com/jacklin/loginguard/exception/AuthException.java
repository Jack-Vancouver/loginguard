package com.jacklin.loginguard.exception;

// 继承 RuntimeException，让它成为一个可以在程序中随时抛出的“炸弹”
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message); // 把错误信息传给父类
    }
}