    package main.resources.gateway.domain;

    /***
     * @Author 徐庶   QQ:1092002729
     * @Slogan 致敬大师，致敬未来的你
     */
    public class Result<T> {
        private Integer code;
        private String msg;
        private T data;

        public Result(Integer code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public Result(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
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

        public static Result error(Integer code, String msg){
            return new Result(code,msg);
        }
    }
