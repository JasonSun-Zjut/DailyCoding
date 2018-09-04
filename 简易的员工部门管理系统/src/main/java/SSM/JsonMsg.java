package SSM;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date /08/30
 */
public class JsonMsg {
    private int Code;
    private String Msg;
    private Map<String ,Object> map=new HashMap<>();
    public JsonMsg add(String key,Object object)
    {
        this.map.put(key,object);
        return this;

    }

    public static JsonMsg success()
    {
        JsonMsg jsonMsg=new JsonMsg();
        jsonMsg.setCode(100);
        jsonMsg.setMsg("请求成功");
        return jsonMsg;
    }
    public static JsonMsg failure()
    {
        JsonMsg jsonMsg=new JsonMsg();
        jsonMsg.setCode(200);
        jsonMsg.setMsg("请求失败");
        return jsonMsg;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getCode() {

        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

}
