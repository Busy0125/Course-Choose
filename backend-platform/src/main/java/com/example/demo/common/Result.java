package com.example.demo.common;

public class Result<T> {
    private String code;
    // 告诉前台这次返回的数据是成功还是失败，如果失败code就是非0，0则成功
    // 不同的非0可以使用不同的code来与错误类型一一对应，这样后台就可以知道是什么错误
    private String msg;
    // 错误信息，会返回给前台，前台将错误信息弹出，告诉用户（用户密码错误......）
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
